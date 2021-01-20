package cn.wilton.rocket.common.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用api 返回对象
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/15 10:24
 * @Email: wilton.icp@gmail.com
 */
@Data
@ApiModel(value = "RocketResult")
public class RocketResult<T> {
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private long code;
    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息")
    private String message;
    /**
     * 数据封装
     */
    @ApiModelProperty(value = "数据封装")
    private T data;

    protected RocketResult() {
    }

    protected RocketResult(long code, String message) {
        this.code = code;
        this.message = message;
    }

    protected RocketResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> RocketResult<T> success(T data) {
        return new RocketResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> RocketResult<T> success(T data, String message) {
        return new RocketResult<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> RocketResult<T> failed(IErrorCode errorCode) {
        return new RocketResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> RocketResult<T> failed(IErrorCode errorCode,String message) {
        return new RocketResult<T>(errorCode.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> RocketResult<T> failed(String message) {
        return new RocketResult<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     */
    public static <T> RocketResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> RocketResult<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> RocketResult<T> validateFailed(String message) {
        return new RocketResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> RocketResult<T> unauthorized(T data) {
        return new RocketResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> RocketResult<T> forbidden(T data) {
        return new RocketResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }
}
