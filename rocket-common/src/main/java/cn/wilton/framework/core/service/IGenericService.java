package cn.wilton.framework.core.service;

import cn.wilton.framework.core.entity.PageInfo;
import cn.wilton.rocket.common.api.RocketResult;

import java.util.List;

/**
 * 通用service接口
 * @param <V> vo对象
 * @param <T> 实体
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/15 15:05
 * @Email: wilton.icp@gmail.com
 */
public interface IGenericService<V,T> {

    /**
     * page接口 分页、排序
     */
    RocketResult<PageInfo<V>> page(V entityVo);

    /**
     * list接口，条件查询
     */
    RocketResult<List<V>> list(V entityVo);

    /**
     * get接口，根据id查询
     */
    RocketResult<V> get(String id);

    /**
     * save接口，vo无id为新增、vo有id为更新（只更新vo有值的属性）
     */
    RocketResult<V> save(V entityVo);

    /**
     * delete接口，根据id删除
     */
    RocketResult<String> delete(String id);
}
