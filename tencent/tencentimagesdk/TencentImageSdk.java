package com.xiaochong.meet.lib.tencent.tencentimagesdk;

import com.qcloud.image.ImageClient;
import com.xiaochong.meet.config.tencentcloud.TencentCloudConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TencentImageSdk {
    private final static Logger logger = LoggerFactory.getLogger(TencentImageSdk.class);
    public static ImageClient builderClient(TencentCloudConfig tencentCloudConfig){
        return new ImageClient(
                tencentCloudConfig.getMainAppId().toString(),
                tencentCloudConfig.getMainSecretId(),
                tencentCloudConfig.getMainSecretKey(),
                ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com/*根据文档说明选择域名*/);
    }
}
