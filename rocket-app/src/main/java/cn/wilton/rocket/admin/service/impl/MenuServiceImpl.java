package cn.wilton.rocket.admin.service.impl;

import cn.wilton.framework.core.service.GenericServiceImpl;
import cn.wilton.rocket.admin.mapper.IMenuMapper;
import cn.wilton.rocket.admin.service.IMenuService;
import cn.wilton.rocket.common.entity.system.Menu;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/25 16:57
 * @Email: wilton.icp@gmail.com
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends GenericServiceImpl<Menu,Menu> implements IMenuService {
    private final IMenuMapper menuMapper;

    @Override
    public String findUserPermissions(String username) {
        List<Menu> userPermissions = menuMapper.findUserPermissions(username);

        List<String> perms = new ArrayList<>();
        for (Menu m: userPermissions){
            perms.add(m.getPerms());
        }
        return StringUtils.join(perms, ",");
    }
}
