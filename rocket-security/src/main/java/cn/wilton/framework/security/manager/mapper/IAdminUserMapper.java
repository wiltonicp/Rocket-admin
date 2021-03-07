package cn.wilton.framework.security.manager.mapper;

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
public interface IAdminUserMapper extends GenericMapper<SystemUser> {

    /**
     * 获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    SystemUser findByName(String username);
}
