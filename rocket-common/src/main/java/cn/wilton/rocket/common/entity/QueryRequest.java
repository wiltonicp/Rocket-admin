package cn.wilton.rocket.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/15 11:05
 * @Email: wilton.icp@gmail.com
 */
@Data
@ToString
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = -4869594085374385813L;

    /**
     * 当前页面数据量
     */
    @ApiModelProperty(value = "每页条数", required = true, example = "10")
    @Min(value = 1, message = "每页条数不得小于1")
    @Max(value = 100, message = "每页条数不得大于100")
    private int pageSize = 10;
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "起始页，从1开始", required = true, example = "1")
    @Min(value = 1, message = "页数不得小于1")
    private int pageNum = 1;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段", required = false, example = "created_time")
    private String field = "created_time";
    /**
     * 排序规则，asc升序，desc降序
     */
    @ApiModelProperty(value = "排序规则", required = false, example = "DESC")
    private String order = "DESC";
}
