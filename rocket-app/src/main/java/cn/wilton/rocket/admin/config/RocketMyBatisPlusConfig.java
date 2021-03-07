package cn.wilton.rocket.admin.config;

import cn.wilton.rocket.common.config.MybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/27 13:43
 * @Email: wilton.icp@gmail.com
 */
@Configuration
@MapperScan({"cn.wilton.rocket.admin.mapper","cn.wilton.framework.security.manager.mapper"})
public class RocketMyBatisPlusConfig extends MybatisPlusConfig {
}
