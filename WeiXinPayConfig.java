package com.wjj.application.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *微信支付配置bean
 */
@Component
@ConfigurationProperties(prefix = "weiXinPay")
@PropertySource(value = {
        "classpath:config/weiXinPay.yml",
        "classpath:config/weiXinPay-${spring.profiles.active:${env:prod}}.yml",
        "file:config/weiXinPay.yml",
        "file:config/weiXinPay-${spring.profiles.active:${env:prod}}.yml"
}, ignoreResourceNotFound = true)
@Data
public class WeiXinPayConfig {

    //appid
    @Value("${appId}")
    private String appId;

    //MchID
    @Value("${mchId}")
    private String mchId;

    @Value("${key}")
    private String key;

    //证书路径
    @Value("${apiClientCertPath}")
    private String apiClientCertPath;

    //是否使用沙箱环境
    @Value("${useSandbox}")
    private Boolean useSandbox = true;

    //是否生产数据
    @Value("${isProd:false}")
    private Boolean isProd = false;



}
