package com.xiaochong.meet.lib.tencentimsdk;

import com.xiaochong.meet.lib.tencentimsdk.config.TencentImConfig;
import com.xiaochong.meet.lib.tencentimsdk.feignprocessor.TencentDecoder;
import com.xiaochong.meet.lib.tencentimsdk.feignprocessor.TencentEncoder;
import com.xiaochong.meet.lib.tencentimsdk.feignprocessor.TencentInterceptor;
import feign.Feign;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TencentImSdk {
    private final static Logger logger = LoggerFactory.getLogger(TencentImSdk.class);

    public static TencentFeignClient builderClient(TencentImConfig tencentImConfig){
        String url = tencentImConfig.getBaseUrl();
//        System.out.println(url);
//        return HystrixFeign.builder()
        return Feign.builder()
                .encoder(new TencentEncoder())
                .decoder(new TencentDecoder())
                .requestInterceptor(new TencentInterceptor(tencentImConfig))
                .client(new OkHttpClient())
                .logger(new Slf4jLogger())
                .target(TencentFeignClient.class, url);
    }
}
