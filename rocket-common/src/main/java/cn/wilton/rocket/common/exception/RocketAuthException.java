package cn.wilton.rocket.common.exception;

/**
* 自定义异常
* @Description
* @Author: Ranger
* @Date: 2021/1/15 14:02
* @Email: wilton.icp@gmail.com
*/
public class RocketAuthException extends Exception{

    private static final long serialVersionUID = -6916154462432027437L;

    public RocketAuthException(String message){
        super(message);
    }
}
