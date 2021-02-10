package cn.wilton.rocket.admin.controller;

import cn.wilton.framework.core.controller.GenericController;
import cn.wilton.rocket.common.entity.system.SystemUser;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/19 10:35
 * @Email: wilton.icp@gmail.com
 */
@RestController
@Api(tags = "用户测试接口")
@RequestMapping("system")
@RequiredArgsConstructor
public class AdminController extends GenericController<SystemUser,SystemUser> {

}
