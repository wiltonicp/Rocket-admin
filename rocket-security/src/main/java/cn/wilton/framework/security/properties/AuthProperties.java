package cn.wilton.framework.security.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * Auth相关的配置类
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/25 14:51
 * @Email: wilton.icp@gmail.com
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:wilton-auth.properties"})
@ConfigurationProperties(prefix = "wilton.auth")
public class AuthProperties {

    private ClientsProperties[] clients = {};
    private int accessTokenValiditySeconds = 60 * 60 * 24;
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;

    // 免认证路径
    private String anonUrl;

    //验证码配置类
    private ValidateCodeProperties code = new ValidateCodeProperties();
}
