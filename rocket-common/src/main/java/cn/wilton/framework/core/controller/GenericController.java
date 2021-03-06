package cn.wilton.framework.core.controller;

import cn.wilton.framework.core.entity.PageInfo;
import cn.wilton.framework.core.service.IGenericService;
import cn.wilton.rocket.common.api.RocketResult;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 通用controller
 * @param <V> vo类
 * @param <T> entity实体
 * @Author: Ranger
 * @Date: 2021/1/15 15:19
 * @Email: wilton.icp@gmail.com
 */
public abstract class GenericController<V,T> {

    @Autowired(required = false)
    private IGenericService<V,T> genericService;

    @GetMapping()
    @ApiOperation(value = "查询分页接口")
    @ApiOperationSupport(author = "Ranger")
    public RocketResult<PageInfo<V>> page(V entityVo) {
        return genericService.page(entityVo);
    }

    @GetMapping("list")
    @ApiOperation(value = "查询 List 接口")
    @ApiOperationSupport(author = "Ranger")
    public RocketResult<List<V>> list(V entityVo) {
        return genericService.list(entityVo);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据 id 查询接口")
    @ApiOperationSupport(author = "Ranger")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true)
    })
    public RocketResult<V> get(@PathVariable("id") String id) {
        return genericService.get(id);
    }

    @PutMapping()
    @ApiOperation(value = "保存接口")
    @ApiOperationSupport(author = "Ranger")
    @PreAuthorize("hasAnyAuthority('user:add')")
    public RocketResult<V> save(V entityVo) {
        return genericService.save(entityVo);
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "根据 id 删除接口")
    @ApiOperationSupport(author = "Ranger")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true)
    })
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public RocketResult<String> delete( @PathVariable("id") String id) {
        return genericService.delete(id);
    }
}
