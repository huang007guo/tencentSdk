package com.xiaochong.meet.lib.tencentimsdk.config;

/**
 * 腾讯im配置提供者
 */
public interface TencentImConfig {
        String getBaseUrl();
        String getAdminUserSig();
        String getAdminIdentifier();
        String getSdkAppId();
}
