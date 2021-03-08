package cn.wilton.rocket.config;

import cn.wilton.rocket.common.config.SwaggerConfig;
import cn.wilton.rocket.common.entity.SwaggerProperties;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @author Ranger
 * @date: 2021/1/20 14:12
 * @email: wilton.icp@gmail.com
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
@ConditionalOnProperty(value = {"knife4j.enable"}, matchIfMissing = true)
public class RocketSwaggerConfig extends SwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .title("Rcoket Admin 后台服务API接口文档")
                .description("Rcoket-Admin服务相关接口(Knife4j)")
                .groupName("1.0版本")
                .contactName("Ranger")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
