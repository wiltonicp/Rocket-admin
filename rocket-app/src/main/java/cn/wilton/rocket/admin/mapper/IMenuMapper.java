package cn.wilton.rocket.admin.mapper;

import cn.wilton.framework.core.mapper.GenericMapper;
import cn.wilton.rocket.common.entity.system.Menu;

import java.util.List;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/25 16:32
 * @Email: wilton.icp@gmail.com
 */
public interface IMenuMapper extends GenericMapper<Menu> {
    /**
     * 通过用户名查找用户权限集合
     * @param username
     * @return
     */
    List<Menu> findUserPermissions(String username);
}
