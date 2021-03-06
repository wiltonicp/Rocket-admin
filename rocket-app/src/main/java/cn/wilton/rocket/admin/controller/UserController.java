package cn.wilton.rocket.admin.controller;

import cn.wilton.rocket.admin.service.ILoginLogService;
import cn.wilton.rocket.admin.service.IUserDataPermissionService;
import cn.wilton.rocket.admin.service.IUserService;
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
 * @date: 2021/3/6 16:01
 * @email: wilton.icp@gmail.com
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Api(tags = "用户管理")
public class UserController {

    private final IUserService userService;
    private final IUserDataPermissionService userDataPermissionService;
    private final ILoginLogService loginLogService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("success")
    @ApiOperation(value = "登录成功保存用户登录日志")
    public void loginSuccess(HttpServletRequest request) {
        String currentUsername = RocketUtil.getCurrentUsername();
        // update last login time
        this.userService.updateLoginTime(currentUsername);
        // save login log
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(currentUsername);
        loginLog.setSystemBrowserInfo(request.getHeader("user-agent"));
        this.loginLogService.saveLoginLog(loginLog);
    }


    @GetMapping
    @ApiOperation(value = "查询用户")
    @PreAuthorize("hasAuthority('user:view')")
    public RocketResult userList(QueryRequest queryRequest, SystemUser user) {
        Map<String, Object> dataTable = RocketUtil.getDataTable(userService.findUserDetailList(user, queryRequest));
        return RocketResult.success(dataTable);
    }

    @GetMapping("check/{username}")
    @ApiOperation(value = "校验用户")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userService.findByName(username) == null;
    }


    @PostMapping
    @PreAuthorize("hasAuthority('user:add')")
    @ApiOperation(value = "新增用户")
    public void addUser(@Valid SystemUser user) {
        this.userService.createUser(user);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('user:update')")
    @ApiOperation(value = "修改用户")
    public void updateUser(@Valid SystemUser user) {
        this.userService.updateUser(user);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('user:update')")
    @ApiOperation(value = "通过用户ID查找关联关系")
    public RocketResult findUserDataPermissions(@NotBlank(message = "{required}") @PathVariable String userId) {
        String dataPermissions = this.userDataPermissionService.findByUserId(userId);
        return RocketResult.success(dataPermissions);
    }

    @DeleteMapping("/{userIds}")
    @PreAuthorize("hasAuthority('user:delete')")
    @ApiOperation(value = "删除用户")
    public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) {
        String[] ids = userIds.split(",");
        this.userService.deleteUsers(ids);
    }

    @PutMapping("profile")
    @ApiOperation(value = "修改个人信息")
    public void updateProfile(@Valid SystemUser user) throws RocketException {
        this.userService.updateProfile(user);
    }

    @PutMapping("avatar")
    @ApiOperation(value = "修改头像")
    public void updateAvatar(@NotBlank(message = "{required}") String avatar) {
        this.userService.updateAvatar(avatar);
    }

    @GetMapping("password/check")
    @ApiOperation(value = "校验密码")
    public boolean checkPassword(@NotBlank(message = "{required}") String password) {
        String currentUsername = RocketUtil.getCurrentUsername();
        SystemUser user = userService.findByName(currentUsername);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    @PutMapping("password")
    @ApiOperation(value = "修改密码")
    public void updatePassword(@NotBlank(message = "{required}") String password) {
        userService.updatePassword(password);
    }

    @PutMapping("password/reset")
    @PreAuthorize("hasAuthority('user:reset')")
    @ApiOperation(value = "重置用户密码")
    public void resetPassword(@NotBlank(message = "{required}") String usernames) {
        String[] usernameArr = usernames.split(",");
        this.userService.resetPassword(usernameArr);
    }

    @PostMapping("excel")
    @PreAuthorize("hasAuthority('user:export')")
    @ApiOperation(value = "导出用户数据")
    public void export(QueryRequest queryRequest, SystemUser user, HttpServletResponse response) {
        List<SystemUser> users = this.userService.findUserDetailList(user, queryRequest).getRecords();
        ExcelKit.$Export(SystemUser.class, response).downXlsx(users, false);
    }
}