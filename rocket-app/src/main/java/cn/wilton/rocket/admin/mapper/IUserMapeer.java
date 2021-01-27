package cn.wilton.rocket.admin.mapper;

import cn.wilton.framework.core.mapper.GenericMapper;
import cn.wilton.rocket.common.entity.system.SystemUser;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/19 10:36
 * @Email: wilton.icp@gmail.com
 */
@Repository
public interface IUserMapeer extends GenericMapper<SystemUser> {
    /**
     * 通过用户名查找用户信息
     * @param username
     * @return
     */
    SystemUser findByName(String username);
}
