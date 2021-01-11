package com.wjj.application.facade.ca.casdk.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *配置bean
 */
@Component
@ConfigurationProperties(prefix = "ca")
@PropertySource(value = {
        "classpath:config/ca.yml",
        "classpath:config/ca-${spring.profiles.active:${env:prod}}.yml",
        "file:config/ca.yml",
        "file:config/ca-${spring.profiles.active:${env:prod}}.yml"
}, ignoreResourceNotFound = true)
@Data
public class CaConfig {

    // 请求的基础地址
    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${clientId}")
    private String clientId;

    @Value("${appSecret}")
    private String appSecret;

    // 缓存前缀
    @Value("${accessTokenCacheKey}")
    private String accessTokenCacheKey;

    // 二维码签名的跳转地址
    @Value("${signQRCodeUrlRedirectUrl}")
    private String signQRCodeUrlRedirectUrl;

    // 二维码签名的跳转地址
    @Value("${selfSignRequestNotifyUrl}")
    private String selfSignRequestNotifyUrl;
}
