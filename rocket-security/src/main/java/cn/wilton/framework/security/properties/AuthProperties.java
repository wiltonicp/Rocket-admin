package cn.wilton.framework.security.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/2/2 16:55
 * @Email: wilton.icp@gmail.com
 */
@Data
@SpringBootConfiguration
@ConfigurationProperties(prefix = "rocket.auth")
public class AuthProperties {
    /**
     * JWT加签密钥
     */
    private String jwtAccessKey;

    /**
     * 社交登录所使用的 Client
     */
    private String socialLoginClientId;
}
