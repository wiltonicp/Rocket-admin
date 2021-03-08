package cn.wilton.rocket.service;

import cn.wilton.rocket.common.entity.system.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author Ranger
 * @date: 2021/3/8 16:32
 * @email: wilton.icp@gmail.com
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 获取用户权限
     * @param username 用户名
     * @return 用户权限
     */
    String findUserPermissions(String username);

    /**
     * 获取用户菜单
     * @param username 用户名
     * @return 用户菜单
     */
    List<Menu> findUserMenus(String username);

    /**
     * 获取用户菜单
     * @param menu menu
     * @return 用户菜单
     */
    Map<String, Object> findMenus(Menu menu);

    /**
     * 获取菜单列表
     * @param menu menu
     * @return 菜单列表
     */
    List<Menu> findMenuList(Menu menu);

    /**
     * 创建菜单
     * @param menu menu
     */
    void createMenu(Menu menu);

    /**
     * 更新菜单
     * @param menu menu
     */
    void updateMenu(Menu menu);

    /**
     * 递归删除菜单/按钮
     * @param menuIds menuIds
     */
    void deleteMeuns(String[] menuIds);

}
