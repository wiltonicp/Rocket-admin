package cn.wilton.framework.security.controller;

import cn.wilton.framework.security.manager.service.UserManager;
import cn.wilton.framework.security.service.ValidateCodeService;
import cn.wilton.rocket.common.api.ResultCode;
import cn.wilton.rocket.common.api.RocketResult;
import cn.wilton.rocket.common.entity.system.SystemUser;
import cn.wilton.rocket.common.exception.RocketAuthException;
import cn.wilton.rocket.common.exception.RocketException;
import cn.wilton.rocket.common.exception.ValidateCodeException;
import com.alibaba.fastjson.JSONObject;
import com.sun.javafx.binding.StringConstant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
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
@RequestMapping("/auth")
public class SecurityController {

    private final UserManager userManager;
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
    public RocketResult currentUser(Principal principal) {
        Map<String, Object> result = new HashMap<>(2);
        if(principal==null){
            new RocketException(ResultCode.UNAUTHORIZED.getMessage());
        }
        String username = principal.getName();
        SystemUser user = userManager.findByName(username);
        String userPermissions = userManager.findUserPermission(username);
        String[] permissionArray = new String[0];
        if (StringUtils.isNoneBlank(userPermissions)) {
            permissionArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(userPermissions, ",");
        }
        result.put("username",username);
        result.put("avatar",user.getAvatar());
        result.put("permissions",permissionArray);
        return RocketResult.success(result);
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
