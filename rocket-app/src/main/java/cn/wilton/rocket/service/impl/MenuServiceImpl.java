package cn.wilton.rocket.service.impl;

import cn.wilton.rocket.common.entity.MenuTree;
import cn.wilton.rocket.common.entity.Tree;
import cn.wilton.rocket.common.entity.system.Menu;
import cn.wilton.rocket.common.util.TreeUtil;
import cn.wilton.rocket.mapper.MenuMapper;
import cn.wilton.rocket.service.IMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ranger
 * @date: 2021/3/8 16:31
 * @email: wilton.icp@gmail.com
 */
@Slf4j
@Service("menuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public String findUserPermissions(String username) {
        List<Menu> userPermissions = this.baseMapper.findUserPermissions(username);
        return userPermissions.stream().map(Menu::getPerms).collect(Collectors.joining(","));
    }

    @Override
    public List<Menu> findUserMenus(String username) {
        return this.baseMapper.findUserMenus(username);
    }

    @Override
    public Map<String, Object> findMenus(Menu menu) {
        Map<String, Object> result = new HashMap<>(2);
        try {
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByAsc(Menu::getOrderNum);
            List<Menu> menus = baseMapper.selectList(queryWrapper);

            List<MenuTree> trees = new ArrayList<>();
            buildTrees(trees, menus);

            if (StringUtils.equals(menu.getType(), Menu.TYPE_BUTTON)) {
                result.put("rows", trees);
            } else {
                List<? extends Tree<?>> menuTree = TreeUtil.build(trees);
                result.put("rows", menuTree);
            }

            result.put("total", menus.size());
        } catch (NumberFormatException e) {
            log.error("查询菜单失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }
        return result;
    }


    @Override
    public List<Menu> findMenuList(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.like(Menu::getMenuName, menu.getMenuName());
        }
        queryWrapper.orderByAsc(Menu::getMenuId);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createMenu(Menu menu) {
        menu.setCreatedTime(LocalDateTime.now());
        setMenu(menu);
        this.save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(Menu menu) {
        menu.setModifyTime(LocalDateTime.now());
        setMenu(menu);
        baseMapper.updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMeuns(String[] menuIds) {
        this.delete(Arrays.asList(menuIds));
    }

    private void buildTrees(List<MenuTree> trees, List<Menu> menus) {
        menus.forEach(menu -> {
            MenuTree tree = new MenuTree();
            tree.setId(menu.getMenuId());
            tree.setParentId(menu.getParentId());
            tree.setLabel(menu.getMenuName());
            tree.setComponent(menu.getComponent());
            tree.setIcon(menu.getIcon());
            tree.setOrderNum(menu.getOrderNum());
            tree.setPath(menu.getPath());
            tree.setType(menu.getType());
            tree.setPerms(menu.getPerms());
            trees.add(tree);
        });
    }

    private void setMenu(Menu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(Menu.TOP_MENU_ID);
        }
        if (Menu.TYPE_BUTTON.equals(menu.getType())) {
            menu.setPath(null);
            menu.setIcon(null);
            menu.setComponent(null);
            menu.setOrderNum(null);
        }
    }

    private void delete(List<String> menuIds) {
        removeByIds(menuIds);

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Menu::getParentId, menuIds);
        List<Menu> menus = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(menus)) {
            List<String> menuIdList = new ArrayList<>();
            menus.forEach(m -> menuIdList.add(String.valueOf(m.getMenuId())));
            this.delete(menuIdList);
        }
    }

}
