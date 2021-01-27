package cn.wilton.rocket.admin.controller;

import cn.wilton.framework.security.service.ValidateCodeService;
import cn.wilton.rocket.admin.entity.AdminLoginParam;
import cn.wilton.rocket.admin.service.IAdminService;
import cn.wilton.rocket.common.api.RocketResult;
import cn.wilton.rocket.common.exception.RocketAuthException;
import cn.wilton.rocket.common.exception.ValidateCodeException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/25 16:02
 * @Email: wilton.icp@gmail.com
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class SecurityController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    private final ValidateCodeService validateCodeService;
    private final IAdminService adminService;

    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws IOException
     * @throws ValidateCodeException
     */
    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        validateCodeService.create(request, response);
    }

    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public RocketResult login(@Validated @RequestBody AdminLoginParam adminLoginParam) {
        String token = adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword());
        if (token == null) {
            return RocketResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RocketResult.success(tokenMap);
    }



    /**
     * 获取当前登录用户
     * @param principal
     * @return
     */
    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        System.out.println(principal);
        return principal;
    }

    /**
     * 注销当前Token
     * @param request
     * @return
     * @throws RocketAuthException
     */
    @DeleteMapping("logout")
    public RocketResult signout(HttpServletRequest request) throws RocketAuthException {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.replace(authorization, "bearer ", "");
        return RocketResult.success("退出登录成功");
    }
}
