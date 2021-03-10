package cn.wilton.rocket.common.entity;

import cn.wilton.rocket.common.entity.system.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ranger
 * @since 2021/3/10
 * @email wilton.icp@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends Tree<Dept> {

    private Integer orderNum;
}
