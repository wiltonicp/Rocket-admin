package cn.wilton.rocket.controller;

import cn.wilton.rocket.service.ILoginLogService;
import cn.wilton.rocket.common.api.RocketResult;
import cn.wilton.rocket.common.entity.QueryRequest;
import cn.wilton.rocket.common.entity.system.LoginLog;
import cn.wilton.rocket.common.util.RocketUtil;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author Ranger
 * @date: 2021/3/6 16:55
 * @email: wilton.icp@gmail.com
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("loginLog")
@Api(tags = "用户登录日志")
public class LoginLogController {

    private final ILoginLogService loginLogService;

    @GetMapping
    @ApiOperation(value = "列表")
    public RocketResult loginLogList(LoginLog loginLog, QueryRequest request) {
        Map<String, Object> dataTable = RocketUtil.getDataTable(this.loginLogService.findLoginLogs(loginLog, request));
        return RocketResult.data(dataTable);
    }


    @GetMapping("currentUser")
    @ApiOperation(value = "当前用户登录日志列表")
    public RocketResult getUserLastSevenLoginLogs() {
        String currentUsername = RocketUtil.getCurrentUsername();
        List<LoginLog> userLastSevenLoginLogs = this.loginLogService.findUserLastSevenLoginLogs(currentUsername);
        return RocketResult.data(userLastSevenLoginLogs);
    }

    @DeleteMapping("{ids}")
    @PreAuthorize("hasAuthority('loginlog:delete')")
    @ApiOperation(value = "删除登录日志")
    public void deleteLogs(@NotBlank(message = "{required}") @PathVariable String ids) {
        String[] loginLogIds = ids.split(",");
        this.loginLogService.deleteLoginLogs(loginLogIds);
    }

    @PostMapping("excel")
    @PreAuthorize("hasAuthority('loginlog:export')")
    @ApiOperation(value = "导出登录日志数据")
    public void export(QueryRequest request, LoginLog loginLog, HttpServletResponse response) {
        List<LoginLog> loginLogs = this.loginLogService.findLoginLogs(loginLog, request).getRecords();
        ExcelKit.$Export(LoginLog.class, response).downXlsx(loginLogs, false);
    }
}
