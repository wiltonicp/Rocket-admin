package cn.wilton.rocket.common.annotation;

import cn.wilton.rocket.common.configure.RocketLettuceRedisConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启 redis 注解
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/15 10:49
 * @Email: wilton.icp@gmail.com
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RocketLettuceRedisConfigure.class)
public @interface EnableRocketLettuceRedis {
}
