package cn.wilton.rocket.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类，所有实体需要继承
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/15 11:17
 * @Email: wilton.icp@gmail.com
 */
@Getter
@Setter
public abstract class RocketEntity implements Serializable {

    /**
     * 创建者
     */
    @TableField(value = "CREATED_BY", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者", required = false, example = "1")
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(value = "CREATED_TIME", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间", required = false)
    private LocalDateTime createdTime;

    /**
     * 修改者
     */
    @TableField(value = "MODIFY_BY", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改者", required = false, example = "1")
    private Long modifyBy;

    /**
     * 修改时间
     */
    @TableField(value = "MODIFY_TIME", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改者", required = false)
    private LocalDateTime modifyTime;

    /**
     * 版本信息
     */
    @TableField(value = "VERSION")
    @Version
    @ApiModelProperty(value = "版本信息", required = false, example = "1.0")
    private Long version;
}
