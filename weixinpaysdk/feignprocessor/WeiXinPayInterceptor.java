package com.wjj.application.util.weixinpaysdk.feignprocessor;

import com.wjj.application.util.weixinpaysdk.config.WeiXinPayConfig;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class WeiXinPayInterceptor implements RequestInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(WeiXinPayInterceptor.class);

    private WeiXinPayConfig weiXinPayConfig;

    public WeiXinPayInterceptor(WeiXinPayConfig weiXinPayConfig) {
        this.weiXinPayConfig = weiXinPayConfig;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.query("random", String.valueOf((new Random()).nextInt()));
        template.query("identifier", weiXinPayConfig.getAdminIdentifier());
        logger.info("imRest request uri: {}; imRest request body: {}", template.url(), new String(template.body()));
    }
}
