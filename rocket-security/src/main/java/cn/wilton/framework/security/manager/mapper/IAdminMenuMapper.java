package cn.wilton.framework.security.manager.mapper;

import cn.wilton.framework.core.mapper.GenericMapper;
import cn.wilton.rocket.common.entity.system.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/25 16:32
 * @Email: wilton.icp@gmail.com
 */
@Repository
public interface IAdminMenuMapper extends GenericMapper<Menu> {
    /**
     * 通过用户名查找用户权限集合
     * @param username
     * @return
     */
    List<Menu> findUserPermission(String username);
}
