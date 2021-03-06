package cn.wilton.rocket.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis Plus 配置类
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/18 14:56
 * @Email: wilton.icp@gmail.com
 */
@Configuration
@MapperScan({"cn.wilton.framework.security.manager.mapper","cn.wilton.rocket.admin.mapper"})
public abstract class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}