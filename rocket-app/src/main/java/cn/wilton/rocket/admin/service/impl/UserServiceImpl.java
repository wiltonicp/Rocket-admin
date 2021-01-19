package cn.wilton.rocket.admin.service.impl;

import cn.wilton.framework.core.service.GenericServiceImpl;
import cn.wilton.rocket.admin.service.IUserService;
import cn.wilton.rocket.common.entity.system.SystemUser;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/19 10:48
 * @Email: wilton.icp@gmail.com
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<SystemUser,SystemUser> implements IUserService {
}
