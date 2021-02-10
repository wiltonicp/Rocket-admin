package cn.wilton.framework.security.config;

import cn.wilton.framework.security.filter.ValidateCodeFilter;
import cn.wilton.framework.security.handler.LoginFailureHandler;
import cn.wilton.framework.security.handler.LoginSuccessHandler;
import cn.wilton.framework.security.service.ValidateCodeService;
import cn.wilton.framework.security.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/25 15:51
 * @Email: wilton.icp@gmail.com
 */
@Order(2)
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ValidateCodeFilter validateCodeFilter;
    private final IgnoreUrlsConfig ignoreUrlsConfig;
    private final LoginFailureHandler loginFailureHandler;
    private final LoginSuccessHandler loginSuccessHandler;
    private final UserDetailsService userDetailService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //不需要保护的资源路径允许访问
        for (String url : ignoreUrlsConfig.getUrls()) {
            http.requestMatchers().antMatchers(url);
        }
        http.addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests() //开启登录配置
                .antMatchers("/oauth/**","/login")
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .permitAll()
                // 自定义权限拦截器JWT过滤器
                .and().csrf().disable()
                .httpBasic().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
