package cn.wilton.framework.core.controller;

import cn.wilton.framework.core.entity.PageInfo;
import cn.wilton.framework.core.service.IGenericService;
import cn.wilton.rocket.common.api.RocketResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 通用controller
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/15 15:19
 * @Email: wilton.icp@gmail.com
 */
public abstract class GenericController<V,T> {

    @Autowired(required = false)
    private IGenericService<V,T> genericService;

    @GetMapping(value = {"/"})
    public String execute(HttpServletRequest request,
                          @RequestParam String method,
                          @RequestParam(required = false)
                          String id, @RequestParam(required = false) String moduleId) {
        String controllerMapping = ((RequestMapping)this.getClass().getAnnotation(RequestMapping.class)).value()[0];
        //String csrfToken = CSRFTokenManager.getTokenForSession(request.getSession());
        if (StringUtils.isNotBlank(id)) {
            request.setAttribute("id", id);
        }

        if (StringUtils.isNotBlank(moduleId)) {
            request.getSession().setAttribute("moduleId", moduleId);
        }

        //request.setAttribute("CSRFToken", csrfToken);
        return controllerMapping + "/" + method;
    }

    @GetMapping("page")
    public RocketResult<PageInfo<V>> page(V entityVo) {
        return genericService.page(entityVo);
    }

    @GetMapping("list")
    public RocketResult<List<V>> list(V entityVo) {
        return genericService.list(entityVo);
    }

    @GetMapping("get/{id}")
    public RocketResult<V> get(@PathVariable("id") String id) {
        return genericService.get(id);
    }

    @GetMapping("save")
    public RocketResult<V> save(V entityVo) {
        return genericService.save(entityVo);
    }

    @GetMapping("delete/{id}")
    public RocketResult<String> delete( @PathVariable("id") String id) {
        return genericService.delete(id);
    }
}
