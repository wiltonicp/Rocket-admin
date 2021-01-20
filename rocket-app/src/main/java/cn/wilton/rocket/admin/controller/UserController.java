package cn.wilton.rocket.admin.controller;

import cn.wilton.framework.core.controller.GenericController;
import cn.wilton.rocket.admin.service.IUserService;
import cn.wilton.rocket.common.api.RocketResult;
import cn.wilton.rocket.common.entity.system.SystemUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/19 10:35
 * @Email: wilton.icp@gmail.com
 */
@RestController
@RequestMapping("system")
@RequiredArgsConstructor
public class UserController extends GenericController<SystemUser,SystemUser> {

    private final IUserService userService;

}
