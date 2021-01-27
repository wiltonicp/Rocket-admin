package cn.wilton.rocket.admin.service;

import cn.wilton.framework.core.service.IGenericService;
import cn.wilton.rocket.common.entity.system.Menu;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/25 16:56
 * @Email: wilton.icp@gmail.com
 */
public interface IMenuService extends IGenericService<Menu,Menu> {

    String findUserPermissions(String username);
}
