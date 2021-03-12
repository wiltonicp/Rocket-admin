package cn.wilton.rocket.common.entity.system;

import cn.wilton.rocket.common.annotation.IsMobile;
import cn.wilton.rocket.common.entity.RocketEntity;
import cn.wilton.rocket.common.entity.enums.SexEnum;
import cn.wilton.rocket.common.entity.enums.StatusEnum;
import cn.wilton.rocket.common.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Ranger
 * @date: 2021/3/6 15:58
 * @email: wilton.icp@gmail.com
 */
@Data
@TableName("t_user")
public class SystemUser extends RocketEntity implements Serializable {

    private static final long serialVersionUID = -4352868070794165001L;

    /**
     * 用户 ID
     */
    @TableId(value = "USER_ID", type = IdType.AUTO)
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 用户名
     */
    @TableField("USERNAME")
    @Size(min = 4, max = 10, message = "用户名长度在 4-10 之间")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 部门 ID
     */
    @TableField("DEPT_ID")
    @ApiModelProperty(value = "部门 ID")
    private Long deptIds;

    /**
     * 邮箱
     */
    @TableField("EMAIL")
    @Size(max = 50, message = "长度不能超过50个字符")
    @Email(message = "邮箱地址有误")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 联系电话
     */
    @TableField("MOBILE")
    @IsMobile(message = "手机号输入有误")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 状态 0锁定 1有效
     */
    @TableField("STATUS")
    @NotBlank(message = "状态不能为空")
    @ApiModelProperty(value = "状态：0锁定 1有效")
    private Long status;

    @ApiModelProperty(value = "状态，用于展示")
    private StatusEnum statusVal;

    /**
     * 最近访问时间
     */
    @TableField("LAST_LOGIN_TIME")
    @ApiModelProperty(value = "最近访问时间")
    private LocalDateTime lastLoginTime;

    /**
     * 性别 0男 1女 2 保密
     */
    @TableField("SSEX")
    @NotBlank(message = "性别不能为空")
    @ApiModelProperty(value = "性别 0男 1女 2 保密")
    private Long sex;

    @ApiModelProperty(value = "性别,用于展示")
    private SexEnum sexVal;

    /**
     * 头像
     */
    @TableField("AVATAR")
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 描述
     */
    @TableField("DESCRIPTION")
    @Size(max = 100, message = "长度不能超过100个字符")
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @TableField(exist = false)
    private String createdTimeFrom;
    @TableField(exist = false)
    private String createdTimeTo;
    /**
     * 角色 ID
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    public void created(SystemUser user){
        this.setSexVal(EnumUtil.getEnumByCode(SexEnum.class,user.getSex()));
        this.setStatusVal(EnumUtil.getEnumByCode(StatusEnum.class,user.getStatus()));
    }

}
