package cn.wilton.rocket.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ranger
 * @date: 2021/3/9 15:55
 * @email: wilton.icp@gmail.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerLogAspect {

    String operation() default "";
}
