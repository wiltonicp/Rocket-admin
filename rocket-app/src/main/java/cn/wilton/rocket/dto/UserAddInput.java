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
 * @author Ranger
 * @date: 2021/3/9 17:20
 * @email: wilton.icp@gmail.com
 */
@Getter
@Setter
@EqualsAndHashCode
@ApiModel("新增用户入参")
public class UserAddInput implements Serializable {

    @Size(min = 4, max = 10, message = "{range}")
    @ApiModelProperty(value = "用户名")
    private String username;

    @Size(max = 50, message = "{noMoreThan}")
    @Email(message = "{email}")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @IsMobile(message = "{mobile}")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @NotBlank(message = "{required}")
    @ApiModelProperty(value = "性别 0男 1女 2 保密")
    private String sex;

    @Size(max = 100, message = "{noMoreThan}")
    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "角色id，多个英文逗号分开")
    private String roleId;

    @ApiModelProperty(value = "部门id，多个英文逗号分开")
    private String deptIds;


}
