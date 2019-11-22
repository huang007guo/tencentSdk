package com.xiaochong.meet.lib.tencentimsdk.feignprocessor;

import com.xiaochong.meet.lib.tencentimsdk.config.TencentImConfig;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class TencentInterceptor implements RequestInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(TencentInterceptor.class);

    private final TencentImConfig tencentImConfig;

    public TencentInterceptor(TencentImConfig tencentImConfig) {
        this.tencentImConfig = tencentImConfig;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.query("random", String.valueOf((new Random()).nextInt()));
        template.query("identifier", tencentImConfig.getAdminIdentifier());
        template.query("usersig", tencentImConfig.getAdminUserSig());
        template.query("sdkappid", tencentImConfig.getSdkAppId());
        logger.info("imRest request uri: {}; imRest request body: {}", template.url(), new String(template.body()));
    }
}
