package cn.wilton.framework.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 用于配置白名单资源路径
 * @Author: Ranger
 * @Date: 2021/1/25 15:20
 * @Email: wilton.icp@gmail.com
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();
}
