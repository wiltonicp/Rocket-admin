package cn.wilton.rocket.common.validator;

import cn.wilton.rocket.common.annotation.IsMobile;
import cn.wilton.rocket.common.constant.RegexpConstant;
import cn.wilton.rocket.common.util.RocketUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
* @Description
* @Author: Ranger
* @Date: 2021/1/15 11:12
* @Email: wilton.icp@gmail.com
*/
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE_REG;
                return RocketUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
