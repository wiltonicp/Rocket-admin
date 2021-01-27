package cn.wilton.rocket.common.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * @Description
 * @Author: Ranger
 * @Date: 2021/1/20 11:23
 * @Email: wilton.icp@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class SwaggerProperties {

    /**
     * 是否要启用登录认证
     */
    private boolean enableSecurity = false;
    /**
     * 文档标题
     */
    private String title = "Rcoket Admin 后台服务API接口文档";
    /**
     * 文档描述
     */
    private String description = "Rcoket-Admin服务相关接口(Knife4j)";
    /**
     * 文档 url
     */
    private String termsOfServiceUrl = "http://localhost:8080/";
    /**
     * 分组版本
     */
    private String groupName = "1.0版本";
    /**
     * 文档版本
     */
    private String version = "1.0";
    /**
     * 文档联系人姓名
     */
    private String contactName = "Ranger";
    /**
     * 文档联系人网址
     */
    private String contactUrl = "https://blog.wiltonic.cn";
    /**
     * 文档联系人邮箱
     */
    private String contactEmail = "wilton.icp@gmail.com";
}
