package cn.wilton.rocket.admin.service.impl;

import cn.wilton.framework.core.service.GenericServiceImpl;
import cn.wilton.framework.security.entity.AdminAuthUser;
import cn.wilton.framework.security.util.JwtTokenUtil;
import cn.wilton.rocket.admin.mapper.IUserMapeer;
import cn.wilton.rocket.admin.service.IAdminService;
import cn.wilton.rocket.admin.service.IMenuService;
import cn.wilton.rocket.common.entity.system.SystemUser;
import cn.wilton.rocket.common.exception.RocketException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/19 10:48
 * @Email: wilton.icp@gmail.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends GenericServiceImpl<SystemUser,SystemUser> implements IAdminService {

    private final IUserMapeer userMapeer;
    private final IMenuService menuService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        SystemUser systemUser = userMapeer.findByName(username);
        if(systemUser != null){
            String permissions = menuService.findUserPermissions(username);
            boolean notLocked = false;
            if (StringUtils.equals(SystemUser.STATUS_VALID, systemUser.getStatus())){
                notLocked = true;
            }
            AdminAuthUser authUser = new AdminAuthUser(systemUser.getUsername(), systemUser.getPassword(), true, true, true, notLocked,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));

            BeanUtils.copyProperties(systemUser,authUser);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                new RocketException("密码不正确");
            }
            if(!userDetails.isEnabled()){
                new RocketException("帐号已被禁用");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
//            updateLoginTimeByUsername(username);
//            insertLoginLog(username);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }
}
