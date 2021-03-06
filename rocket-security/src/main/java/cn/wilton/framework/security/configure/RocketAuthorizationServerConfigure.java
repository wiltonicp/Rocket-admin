package cn.wilton.framework.security.configure;

import cn.wilton.framework.security.exception.AuthWebResponseExceptionTranslator;
import cn.wilton.framework.security.properties.AuthProperties;
import cn.wilton.framework.security.service.RedisAuthenticationCodeService;
import cn.wilton.framework.security.service.RedisClientDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 认证服务器
 * @Author: Ranger
 * @Date: 2021/2/2 14:18
 * @Email: wilton.icp@gmail.com
 */
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class RocketAuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {

    private final AuthProperties properties;
    private final UserDetailsService userDetailService;
    private final AuthenticationManager authenticationManager;
    private final RedisClientDetailsService redisClientDetailsService;
    private final AuthWebResponseExceptionTranslator exceptionTranslator;
    private final RedisAuthenticationCodeService redisAuthenticationCodeService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(redisClientDetailsService);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .userDetailsService(userDetailService)
                .authorizationCodeServices(redisAuthenticationCodeService)
                .authenticationManager(authenticationManager)
                .exceptionTranslator(exceptionTranslator)
                .accessTokenConverter(jwtAccessTokenConverter());
    }

    /**
     * 配置token状态查询
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //开启支持通过表单方式提交client_id和client_secret,否则请求时以basic auth方式,头信息传递Authorization发送请求
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();

        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(redisClientDetailsService);
        return tokenServices;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        DefaultAccessTokenConverter defaultAccessTokenConverter = (DefaultAccessTokenConverter) accessTokenConverter.getAccessTokenConverter();
        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
        userAuthenticationConverter.setUserDetailsService(userDetailService);
        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        accessTokenConverter.setSigningKey(properties.getJwtAccessKey());
        return accessTokenConverter;
    }

    @Bean
    public ResourceOwnerPasswordTokenGranter resourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager, OAuth2RequestFactory oAuth2RequestFactory) {
        DefaultTokenServices defaultTokenServices = defaultTokenServices();
        defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());
        return new ResourceOwnerPasswordTokenGranter(authenticationManager, defaultTokenServices, redisClientDetailsService, oAuth2RequestFactory);
    }

    @Bean
    public DefaultOAuth2RequestFactory oAuth2RequestFactory() {
        return new DefaultOAuth2RequestFactory(redisClientDetailsService);
    }
}
