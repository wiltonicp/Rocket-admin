package cn.wilton.rocket.common.handler;

import cn.wilton.rocket.common.api.ResultCode;
import cn.wilton.rocket.common.api.RocketResult;
import cn.wilton.rocket.common.exception.BizException;
import cn.wilton.rocket.common.exception.RocketAuthException;
import cn.wilton.rocket.common.exception.RocketException;
import cn.wilton.rocket.common.exception.ValidateCodeException;
import com.baomidou.mybatisplus.extension.api.IErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

/**
* 全局统用异常处理器
* 所谓的全局异常处理指的是全局处理Controller层抛出来的异常。因为全局异常处理器在各个服务系统里都能用到
* 对于通用的异常类型捕获可以在BaseExceptionHandler中定义
* @Description
* @Author: Ranger
* @Date: 2021/1/15 13:57
* @Email: wilton.icp@gmail.com
*/
@Slf4j
public class BaseExceptionHandler {


    @ExceptionHandler(value = RocketException.class)
    @ResponseStatus(HttpStatus.OK)
    public RocketResult handleBaseException(RocketException e) {
        log.error("Rocket Admin系统异常", e);
        return RocketResult.failed(e.getMessage());
    }

    /**
     * 处理自定义的业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.OK)
    public RocketResult bizExceptionHandler(BizException e){
        log.error("发生业务异常！原因是：{}",e.getErrorMsg());
        return RocketResult.failed(e.getMessage());
    }

    /**
     * 处理空指针的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseStatus(HttpStatus.OK)
    public RocketResult exceptionHandler(NullPointerException e){
        log.error("发生空指针异常！原因是:",e);
        return RocketResult.failed(ResultCode.BODY_NOT_MATCH);
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return RocketResult
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public RocketResult handleBindException(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return RocketResult.failed(message.toString());
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return RocketResult
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public RocketResult handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return RocketResult.failed(message.toString());
    }

    @ExceptionHandler(value = RocketAuthException.class)
    @ResponseStatus(HttpStatus.OK)
    public RocketResult handleWiltonException(RocketException e) {
        log.error("系统错误", e);
        return RocketResult.failed(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public RocketResult handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return RocketResult.failed("系统内部异常");
    }

    @ExceptionHandler(value = ValidateCodeException.class)
    @ResponseStatus(HttpStatus.OK)
    public RocketResult handleValidateCodeException(ValidateCodeException e) {
        log.error("系统错误", e);
        return RocketResult.failed(e.getMessage());
    }



    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.OK)
    public RocketResult handleAccessDeniedException(){
        return RocketResult.failed(ResultCode.FORBIDDEN);
    }
}
