package cn.wilton.rocket.dto;

import cn.wilton.rocket.common.annotation.IsMobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 修改用户入参
 * @author Ranger
 * @since 2021/3/10
 * @email: wilton.icp@gmail.com
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ApiModel("修改用户入参")
public class UserUpdateInput implements Serializable {


    @Size(min = 4, max = 10, message = "用户名长度在 4-10 之间")
    @ApiModelProperty(value = "用户名")
    private String username;

    @Size(max = 50, message = "长度不能超过50个字符")
    @Email(message = "邮箱地址有误")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @IsMobile(message = "手机号输入有误")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @NotBlank(message = "性别不能为空")
    @ApiModelProperty(value = "性别 0男 1女 2 保密")
    private Long sex;

    @Size(max = 100, message = "长度不能超过100个字符")
    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "角色id，多个英文逗号分开")
    private Long roleId;

    @ApiModelProperty(value = "部门id")
    private Long deptIds;


}
