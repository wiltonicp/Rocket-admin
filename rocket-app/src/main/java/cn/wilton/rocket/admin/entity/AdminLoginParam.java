package cn.wilton.rocket.admin.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 登录参数
 * @Author: Ranger
 * @Date: 2021/1/25 17:30
 * @Email: wilton.icp@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AdminLoginParam {
    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
