package cn.wilton.rocket.controller;

import cn.wilton.rocket.common.annotation.ControllerLogAspect;
import cn.wilton.rocket.common.api.RocketResult;
import cn.wilton.rocket.common.entity.QueryRequest;
import cn.wilton.rocket.common.entity.system.Role;
import cn.wilton.rocket.common.util.RocketUtil;
import cn.wilton.rocket.service.IRoleService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author Ranger
 * @since 2021/3/10
 * @email wilton.icp@gmail.com
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("role")
@Api(tags = "角色管理模块")
public class RoleController {

    private final IRoleService roleService;

    @GetMapping
    @ApiOperation(value = "列表")
    @ControllerLogAspect
    public RocketResult roleList(QueryRequest queryRequest, Role role) {
        Map<String, Object> dataTable = RocketUtil.getDataTable(roleService.findRoles(role, queryRequest));
        return RocketResult.data(dataTable);
    }

    @GetMapping("options")
    @ApiOperation(value = "获取所有角色")
    @ControllerLogAspect
    public RocketResult roles() {
        List<Role> allRoles = roleService.findAllRoles();
        return RocketResult.data(allRoles);
    }

//    @GetMapping("check/{roleName}")
//    @ApiOperation(value = "通过名称获取角色")
//    @ControllerLogAspect
//    public boolean checkRoleName(@NotBlank(message = "角色名称不能为空") @PathVariable String roleName) {
//        Role result = this.roleService.findByName(roleName);
//        return result == null;
//    }
//
//    @PostMapping
//    @PreAuthorize("hasAuthority('role:add')")
//    @ApiOperation(value = "新增角色")
//    @ControllerLogAspect
//    public void addRole(@RequestBody @Valid Role role) {
//        this.roleService.createRole(role);
//    }
//
//    @DeleteMapping("/{roleIds}")
//    @PreAuthorize("hasAuthority('role:delete')")
//    @ApiOperation(value = "删除角色")
//    @ControllerLogAspect
//    public void deleteRoles(@NotBlank(message = "角色id不能为空") @PathVariable String roleIds) {
//        String[] ids = roleIds.split(",");
//        this.roleService.deleteRoles(ids);
//    }
//
//    @PutMapping
//    @PreAuthorize("hasAuthority('role:update')")
//    @ApiOperation(value = "修改角色")
//    @ControllerLogAspect
//    public void updateRole(@RequestBody @Valid Role role) {
//        this.roleService.updateRole(role);
//    }
//
//    @PostMapping("excel")
//    @PreAuthorize("hasAuthority('role:export')")
//    @ApiOperation(value = "导出角色数据")
//    @ControllerLogAspect
//    public void export(@RequestBody QueryRequest queryRequest, @RequestBody Role role, HttpServletResponse response) {
//        List<Role> roles = this.roleService.findRoles(role, queryRequest).getRecords();
//        ExcelKit.$Export(Role.class, response).downXlsx(roles, false);
//    }
}
