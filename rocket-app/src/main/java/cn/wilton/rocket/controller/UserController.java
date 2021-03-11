package cn.wilton.rocket.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.wilton.rocket.common.annotation.ControllerLogAspect;
import cn.wilton.rocket.dto.UserAddInput;
import cn.wilton.rocket.dto.UserUpdateInput;
import cn.wilton.rocket.service.ILoginLogService;
import cn.wilton.rocket.service.IUserDataPermissionService;
import cn.wilton.rocket.service.IUserService;
import cn.wilton.rocket.common.api.RocketResult;
import cn.wilton.rocket.common.entity.QueryRequest;
import cn.wilton.rocket.common.entity.system.LoginLog;
import cn.wilton.rocket.common.entity.system.SystemUser;
import cn.wilton.rocket.common.exception.RocketException;
import cn.wilton.rocket.common.util.RocketUtil;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("user")
@Api(tags = "用户管理模块")
public class UserController {

    private final IUserService userService;
    private final IUserDataPermissionService userDataPermissionService;
    private final ILoginLogService loginLogService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("success")
    @ApiOperation(value = "登录成功保存用户登录日志")
    public RocketResult loginSuccess(HttpServletRequest request) {
        String currentUsername = RocketUtil.getCurrentUsername();
        this.userService.updateLoginTime(currentUsername);
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(currentUsername);
        loginLog.setSystemBrowserInfo(request.getHeader("user-agent"));
        this.loginLogService.saveLoginLog(loginLog);
        return RocketResult.success();
    }

    @GetMapping
    @ApiOperation(value = "查询用户")
    @PreAuthorize("hasAuthority('user:view')")
    @ControllerLogAspect
    public RocketResult userList(QueryRequest queryRequest, SystemUser user) {
        Map<String, Object> dataTable = RocketUtil.getDataTable(userService.findUserDetailList(user, queryRequest));
        return RocketResult.data(dataTable);
    }

    @GetMapping("check/{username}")
    @ApiOperation(value = "校验用户")
    public boolean checkUserName(@NotBlank(message = "用户名不能为空") @PathVariable String username) {
        return this.userService.findByName(username) == null;
    }


    @PostMapping
    @PreAuthorize("hasAuthority('user:add')")
    @ApiOperation(value = "新增用户")
    @ControllerLogAspect
    public RocketResult addUser(@RequestBody @Valid UserAddInput input) throws RocketException{
        SystemUser user = new SystemUser();
        BeanUtil.copyProperties(input,user);
        this.userService.createUser(user);
        return RocketResult.success();
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('user:update')")
    @ApiOperation(value = "修改用户")
    @ControllerLogAspect
    public RocketResult updateUser(@RequestBody @Valid UserUpdateInput input) {
        SystemUser user = new SystemUser();
        BeanUtil.copyProperties(input,user);
        this.userService.updateUser(user);
        return RocketResult.success();
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('user:update')")
    @ApiOperation(value = "通过用户ID查找关联关系")
    @ControllerLogAspect
    public RocketResult findUserDataPermissions(@NotBlank(message = "用户id不能为空") @PathVariable String userId) {
        String dataPermissions = this.userDataPermissionService.findByUserId(userId);
        return RocketResult.success(dataPermissions);
    }

    @DeleteMapping("/{userIds}")
    @PreAuthorize("hasAuthority('user:delete')")
    @ApiOperation(value = "删除用户")
    @ControllerLogAspect
    public RocketResult deleteUsers(@NotBlank(message = "用户id不能为空") @PathVariable String userIds) {
        String[] ids = userIds.split(",");
        this.userService.deleteUsers(ids);
        return RocketResult.success();
    }

    @PutMapping("profile")
    @ApiOperation(value = "修改个人信息")
    @ControllerLogAspect
    public RocketResult updateProfile(@RequestBody @Valid UserUpdateInput input) throws RocketException {
        SystemUser user = new SystemUser();
        BeanUtil.copyProperties(input,user);
        this.userService.updateProfile(user);
        return RocketResult.success();
    }

    @PutMapping("avatar")
    @ApiOperation(value = "修改头像")
    @ControllerLogAspect
    public RocketResult updateAvatar(@NotBlank(message = "头像不能为空") String avatar) {
        this.userService.updateAvatar(avatar);
        return RocketResult.success();
    }

    @GetMapping("password/check")
    @ApiOperation(value = "校验密码")
    public boolean checkPassword(@NotBlank(message = "密码不能为空") String password) {
        String currentUsername = RocketUtil.getCurrentUsername();
        SystemUser user = userService.findByName(currentUsername);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    @PutMapping("password")
    @ApiOperation(value = "修改密码")
    @ControllerLogAspect
    public RocketResult updatePassword(@NotBlank(message = "密码不能为空") String password) {
        userService.updatePassword(password);
        return RocketResult.success();
    }

    @PutMapping("password/reset")
    @PreAuthorize("hasAuthority('user:reset')")
    @ApiOperation(value = "重置用户密码")
    @ControllerLogAspect
    public RocketResult resetPassword(@NotBlank(message = "用户名不能为空") @RequestBody String usernames) {
        String[] usernameArr = usernames.split(",");
        this.userService.resetPassword(usernameArr);
        return RocketResult.success();
    }

    @PostMapping("excel")
    @PreAuthorize("hasAuthority('user:export')")
    @ApiOperation(value = "导出用户数据")
    @ControllerLogAspect
    public void export(@RequestBody QueryRequest queryRequest, @RequestBody SystemUser user, HttpServletResponse response) {
        List<SystemUser> users = this.userService.findUserDetailList(user, queryRequest).getRecords();
        ExcelKit.$Export(SystemUser.class, response).downXlsx(users, false);
    }
}
