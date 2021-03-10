package cn.wilton.rocket.config;

import cn.wilton.rocket.common.config.MybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis 配置类
 * @author Ranger
 * @since 2021/2/11
 * @email: wilton.icp@gmail.com
 */
@Configuration
@MapperScan({"cn.wilton.rocket.mapper","cn.wilton.framework.security.manager.mapper"})
public class RocketMyBatisPlusConfig extends MybatisPlusConfig {
}
