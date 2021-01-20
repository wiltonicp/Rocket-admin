package cn.wilton.framework.core.entity;

import cn.wilton.framework.core.util.CoreUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/15 15:13
 * @Email: wilton.icp@gmail.com
 */
@Data
@ApiModel(value = "PageInfo")
public class PageInfo<T> {

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    private int page;
    /**
     * 页面大小
     */
    @ApiModelProperty(value = "页面大小")
    private int pageSize;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String field;
    /**
     * 排序方式
     */
    @ApiModelProperty(value = "排序方式")
    private String order;
    /**
     * 分页结果
     */
    @ApiModelProperty(value = "分页结果")
    private List<T> rows;
    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private int records;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private int totalPage;

    public static <T> PageInfo<T> of(IPage page, Class<T> entityVoClass) {
        int records = (int) page.getTotal();
        int pageSize = (int) page.getSize();
        int total = records % pageSize == 0 ? records / pageSize : records / pageSize + 1;

        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setPage((int) page.getCurrent());
        pageInfo.setPageSize(pageSize);
        pageInfo.setRows(CoreUtil.copyList(page.getRecords(), entityVoClass));
        pageInfo.setRecords(records);
        pageInfo.setTotalPage(total);
        return pageInfo;
    }
}
