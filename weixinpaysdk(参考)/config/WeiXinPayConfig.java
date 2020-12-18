package com.wjj.application.util.weixinpaysdk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *腾讯云配置bean
 */
//@Component
//@ConfigurationProperties(prefix = "weiXinPay")
//@PropertySource(value = {"classpath:config/weiXinPay.yml", "classpath:weiXinPay-${spring.profiles.active:${env:prod}}.yml"}, ignoreResourceNotFound = true)
@Data
public class WeiXinPayConfig {

    private String baseUrl;

    //im appid
    private Long sdkAppId;

    //im 私钥
    private String privateKey;

    //im 预设管理员id
    private String adminIdentifier;

    //腾讯云主密钥
    private Long mainAppId;

    private String mainSecretId;


    private String mainSecretKey;

    private Long cosAppId;

    private String cosSecretId;

    private String cosSecretKey;

    private String cosBucketName;

    private String cosCdnHost;

    //cos区域
    private String cosRegion;

    //vod区域
    private String vodRegion;

    //vod防盗链签名key
    private String vodSignKey;

    //vod上传后的任务流
    private String vodProcedure;

    private String liveSecretId;

    private String liveSecretKey;

    private String liveBizid;

    //天御内容安全 区域
    private String contentSecurityRegion;
}
