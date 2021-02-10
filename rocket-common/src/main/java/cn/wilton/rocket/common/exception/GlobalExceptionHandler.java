package cn.wilton.rocket.common.exception;

import cn.wilton.rocket.common.handler.BaseExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/28 14:40
 * @Email: wilton.icp@gmail.com
 */
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends BaseExceptionHandler {
}
