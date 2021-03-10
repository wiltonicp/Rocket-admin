package cn.wilton.rocket.controller;

import cn.wilton.rocket.common.annotation.ControllerLogAspect;
import cn.wilton.rocket.common.api.RocketResult;
import cn.wilton.rocket.common.entity.QueryRequest;
import cn.wilton.rocket.common.entity.system.Dept;
import cn.wilton.rocket.service.IDeptService;
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
@RequestMapping("dept")
@RequiredArgsConstructor
@Api(tags = "部门管理模块")
public class DeptController {

    private final IDeptService deptService;

    @GetMapping
    @ApiOperation(value = "分页列表")
    @ControllerLogAspect
    public RocketResult deptList(QueryRequest request, Dept dept) {
        Map<String, Object> depts = this.deptService.findDepts(request, dept);
        return RocketResult.data(depts);
    }

    @GetMapping("options")
    @ApiOperation(value = "获取所有部门")
    @ControllerLogAspect
    public RocketResult roles() {
        Map<String, Object> allDepts = deptService.findAllDepts();
        return RocketResult.data(allDepts);
    }

//    @PostMapping
//    @PreAuthorize("hasAuthority('dept:add')")
//    @ApiOperation(value = "新增部门")
//    @ControllerLogAspect
//    public void addDept(@RequestBody @Valid Dept dept) {
//        this.deptService.createDept(dept);
//    }
//
//    @DeleteMapping("/{deptIds}")
//    @PreAuthorize("hasAuthority('dept:delete')")
//    @ApiOperation(value = "删除部门")
//    @ControllerLogAspect
//    public void deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) {
//        String[] ids = deptIds.split(",");
//        this.deptService.deleteDepts(ids);
//    }
//
//    @PutMapping
//    @PreAuthorize("hasAuthority('dept:update')")
//    @ApiOperation(value = "修改部门")
//    @ControllerLogAspect
//    public void updateDept(@RequestBody  @Valid Dept dept) {
//        this.deptService.updateDept(dept);
//    }
//
//    @PostMapping("excel")
//    @PreAuthorize("hasAuthority('dept:export')")
//    @ApiOperation(value = "导出部门数据")
//    @ControllerLogAspect
//    public void export(@RequestBody  Dept dept, @RequestBody  QueryRequest request, HttpServletResponse response) {
//        List<Dept> depts = this.deptService.findDepts(dept, request);
//        ExcelKit.$Export(Dept.class, response).downXlsx(depts, false);
//    }
}
