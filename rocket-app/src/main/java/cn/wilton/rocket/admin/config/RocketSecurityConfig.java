package cn.wilton.rocket.admin.config;

import cn.wilton.framework.security.config.SecurityConfig;
import cn.wilton.rocket.admin.service.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/25 15:48
 * @Email: wilton.icp@gmail.com
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class RocketSecurityConfig extends SecurityConfig {

    private final IAdminService adminService;

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> adminService.loadUserByUsername(username);
    }
}
