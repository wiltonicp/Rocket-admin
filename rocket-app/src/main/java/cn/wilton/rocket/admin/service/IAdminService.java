package cn.wilton.rocket.admin.service;

import cn.wilton.framework.core.service.IGenericService;
import cn.wilton.rocket.common.entity.system.SystemUser;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/25 16:18
 * @Email: wilton.icp@gmail.com
 */
public interface IAdminService extends IGenericService<SystemUser,SystemUser> {

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);
}
