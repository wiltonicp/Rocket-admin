package cn.wilton.rocket.common.entity;

import lombok.Data;
import lombok.ToString;

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
    private int pageSize = 10;
    /**
     * 当前页码
     */
    private int pageNum = 1;
    /**
     * 排序字段
     */
    private String field;
    /**
     * 排序规则，asc升序，desc降序
     */
    private String order;
}
