package cn.wilton.framework.security.properties;

import lombok.Data;

/**
 * Client配置类
 * @Author: Ranger
 * @Date: 2021/1/25 14:51
 * @Email: wilton.icp@gmail.com
 */
@Data
public class ClientsProperties {

    private String client;
    private String secret;
    private String grantType = "password,authorization_code,refresh_token";
    private String scope = "all";
}
