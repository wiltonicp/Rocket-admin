package cn.wilton.framework.security.controller;

import cn.wilton.framework.security.service.ValidateCodeService;
import cn.wilton.rocket.common.api.RocketResult;
import cn.wilton.rocket.common.exception.RocketAuthException;
import cn.wilton.rocket.common.exception.ValidateCodeException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/25 16:02
 * @Email: wilton.icp@gmail.com
 */
@RestController
@RequiredArgsConstructor
public class SecurityController {

    private final ValidateCodeService validateCodeService;
    private final ConsumerTokenServices consumerTokenServices;

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
     * @param token
     * @return
     * @throws RocketAuthException
     */
    @DeleteMapping("signout")
    public RocketResult signout(@RequestHeader("Authorization") String token) throws RocketAuthException {
        token = StringUtils.replace(token, "bearer ", "");
        consumerTokenServices.revokeToken(token);
        return RocketResult.success("signout");
    }
}
