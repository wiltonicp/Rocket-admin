package cn.wilton.rocket.mapper;

import cn.wilton.rocket.common.entity.system.SystemUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ranger
 * @date: 2021/3/6 15:35
 * @email: wilton.icp@gmail.com
 */
@Repository
public interface UserMapper extends BaseMapper<SystemUser> {

    /**
     * 查找用户详细信息
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @param <T>  type
     * @return Ipage
     */
    <T> IPage<SystemUser> findUserDetailPage(Page<T> page, @Param("user") SystemUser user);

    /**
     * 查找用户详细信息
     * @param user 用户对象，用于传递查询条件
     * @return List<User>
     */
    List<SystemUser> findUserDetail(@Param("user") SystemUser user);

}
