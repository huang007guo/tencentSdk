package com.wjj.application.util.weixinpaysdk;

import com.wjj.application.util.weixinpaysdk.config.WeiXinPayConfig;
import com.wjj.application.util.weixinpaysdk.feignprocessor.TencentDecoder;
import com.wjj.application.util.weixinpaysdk.feignprocessor.TencentEncoder;
import com.wjj.application.util.weixinpaysdk.feignprocessor.WeiXinPayInterceptor;
import feign.Feign;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TencentImSdk {
    private final static Logger logger = LoggerFactory.getLogger(TencentImSdk.class);

    public static TencentFeignClient builderClient(WeiXinPayConfig weiXinPayConfig){
        String url = weiXinPayConfig.getBaseUrl();
//        System.out.println(url);
//        return HystrixFeign.builder()
        return Feign.builder()
                .encoder(new TencentEncoder())
                .decoder(new TencentDecoder())
                .requestInterceptor(new WeiXinPayInterceptor(weiXinPayConfig))
                .client(new OkHttpClient())
                .logger(new Slf4jLogger())
                .target(TencentFeignClient.class, url);
    }
}
