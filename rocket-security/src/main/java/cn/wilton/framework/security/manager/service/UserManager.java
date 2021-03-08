package cn.wilton.framework.security.manager.service;

import cn.wilton.framework.security.manager.mapper.IAdminMenuMapper;
import cn.wilton.framework.security.manager.mapper.IAdminUserMapper;
import cn.wilton.rocket.common.entity.system.Menu;
import cn.wilton.rocket.common.entity.system.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/2/10 13:16
 * @Email: wilton.icp@gmail.com
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserManager {

    private final IAdminUserMapper adminUserMapper;
    private final IAdminMenuMapper adminMenuMapper;

    /**
     * 通过用户名查询用户信息
     * @param username 用户名
     * @return 用户
     */
    public SystemUser findByName(String username) {
        SystemUser user = adminUserMapper.findByName(username);
        if (user != null) {
        }
        return user;
    }

    /**
     * 通过用户名查询用户权限串
     *
     * @param username 用户名
     * @return 权限
     */
    public String findUserPermission(String username) {
        List<Menu> userPermissions = adminMenuMapper.findUserPermission(username);
        return userPermissions.stream().map(Menu::getPerms).collect(Collectors.joining(","));
    }
}
