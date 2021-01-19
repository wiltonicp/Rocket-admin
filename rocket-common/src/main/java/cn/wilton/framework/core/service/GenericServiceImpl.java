package cn.wilton.framework.core.service;

import cn.wilton.framework.core.entity.PageInfo;
import cn.wilton.framework.core.mapper.GenericMapper;
import cn.wilton.framework.core.util.CoreUtil;
import cn.wilton.rocket.common.api.RocketResult;
import cn.wilton.rocket.common.entity.QueryRequest;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用 service 实现层 每个实现层需要继承
 * @param <V> vo对象
 * @param <T> 实体类
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/15 15:40
 * @Email: wilton.icp@gmail.com
 */
public abstract class GenericServiceImpl<V,T> implements IGenericService<V,T>{

    @Autowired
    private GenericMapper<T> genericMapper;
    /**
     * 实体类Vo
     */
    private Class<V> entityVoClass;
    /**
     * 实体类
     */
    private Class<T> entityClass;

    public GenericServiceImpl() {
        Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        this.entityVoClass = (Class<V>) types[0];
        this.entityClass = (Class<T>) types[1];
    }

    @Override
    public RocketResult<PageInfo<V>> page(V entityVo) {
        //实体类缺失分页信息
        if (!(entityVo instanceof QueryRequest)) {
            throw new RuntimeException("实体类" + entityVoClass.getName() + "未继承QueryRequest");
        }
        QueryRequest QueryRequest = (QueryRequest) entityVo;

        T entity = CoreUtil.copy(entityVo, entityClass);

        //查询条件
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(entity);

        //排序
        if(!StringUtils.isEmpty(QueryRequest.getOrder()) && "desc".equals(QueryRequest.getOrder().toLowerCase())){
            queryWrapper.orderByDesc(QueryRequest.getField());
        }else{
            queryWrapper.orderByAsc(QueryRequest.getField());
        }

        //分页
        IPage<T> page = new Page<>(QueryRequest.getPageNum(), QueryRequest.getPageSize());

        //查询获取数据
        page = genericMapper.selectPage(page, queryWrapper);

        //拼接数据
        PageInfo<V> pageInfo = PageInfo.of(page, entityVoClass);
        pageInfo.setField(QueryRequest.getField());
        pageInfo.setOrder(QueryRequest.getOrder());
        return RocketResult.success(pageInfo);
    }

    @Override
    public RocketResult<List<V>> list(V entityVo) {
        T entity = CoreUtil.copy(entityVo, entityClass);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(entity);
        return RocketResult.success(CoreUtil.copyList(genericMapper.selectList(queryWrapper),entityVoClass));
    }

    @Override
    public RocketResult<V> get(String id) {
        return RocketResult.success(CoreUtil.copy(genericMapper.selectById(id),entityVoClass));
    }

    @Override
    public RocketResult<V> save(V entityVo) {
        //传进来的对象（属性可能残缺）
        T entity = CoreUtil.copy(entityVo, entityClass);

        //最终要保存的对象
        T entityFull = entity;

        Object id = null;

        //为空的属性值，忽略属性，BeanUtils复制的时候用到
        List<String> ignoreProperties = new ArrayList<String>();

        //获取最新数据，解决部分更新时jpa其他字段设置null问题
        try {
            //反射获取Class的属性（Field表示类中的成员变量）
            for (Field field : entity.getClass().getDeclaredFields()) {
                //获取授权
                field.setAccessible(true);
                //属性名称
                String fieldName = field.getName();
                //属性的值
                Object fieldValue = field.get(entity);

                //找出Id主键
                if (field.isAnnotationPresent(TableId.class) && !StringUtils.isEmpty(fieldValue)) {
                    id = fieldValue;
                    entityFull = genericMapper.selectById((Serializable) id);
                }

                //找出值为空的属性，值为空则为忽略属性
                if(null == fieldValue){
                    ignoreProperties.add(fieldName);
                }
            }
            /*
                org.springframework.beans BeanUtils.copyProperties(A,B); 是A中的值付给B
                org.apache.commons.beanutils; BeanUtils.copyProperties(A,B);是B中的值付给A
                把entity的值赋给entityFull，第三个参数是忽略属性，表示不进行赋值
             */
            BeanUtils.copyProperties(entity, entityFull, ignoreProperties.toArray(new String[0]));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //新增或更新
        if(StringUtils.isEmpty(id)){
            //1插入成功、0失败
            int newId = genericMapper.insert(entityFull);
        }else{
            genericMapper.updateById(entityFull);
        }

        return RocketResult.success(CoreUtil.copy(entityFull,entityVoClass));
    }

    @Override
    public RocketResult<String> delete(String id) {
        //1删除成功、0失败
        return RocketResult.success(String.valueOf(genericMapper.deleteById(id)));
    }
}
