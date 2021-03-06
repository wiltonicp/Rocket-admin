package cn.wilton.framework.security.service;

import cn.wilton.framework.security.manager.service.UserManager;
import cn.wilton.rocket.common.entity.system.AdminAuthUser;
import cn.wilton.rocket.common.entity.system.SystemUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/2/3 10:45
 * @Email: wilton.icp@gmail.com
 */
@Service
@RequiredArgsConstructor
public class RocketUserDetailsServiceImpl implements UserDetailsService {

    private final UserManager manager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取用户信息
        SystemUser systemUser = manager.findByName(username);
        if(systemUser != null){
            String permissions = manager.findUserPermission(username);
            boolean notLocked = false;
            if (StringUtils.equals(SystemUser.STATUS_VALID, systemUser.getStatus())){
                notLocked = true;
            }
            AdminAuthUser authUser = new AdminAuthUser(systemUser.getUsername(), systemUser.getPassword(), true, true, true, notLocked,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));

            BeanUtils.copyProperties(systemUser,authUser);
            return authUser;
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
