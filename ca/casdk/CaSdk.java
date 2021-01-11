package com.wjj.application.facade.ca.casdk;

import com.wjj.application.facade.ca.casdk.config.CaConfig;
import com.wjj.application.facade.ca.casdk.feignprocessor.CustomDecoder;
import com.wjj.application.facade.ca.casdk.feignprocessor.CustomEncoder;
import com.wjj.application.facade.ca.casdk.feignprocessor.CustomInterceptor;
import feign.Feign;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;


@Configuration
public class CaSdk {
    private final static Logger logger = LoggerFactory.getLogger(CaSdk.class);

    @Bean
    public static CaFeignClient builderClient(CaConfig caConfig){
        String url = caConfig.getBaseUrl();
//        System.out.println(url);
//        return HystrixFeign.builder()
        return Feign.builder()
                .encoder(new CustomEncoder(caConfig))
                .decoder(new CustomDecoder(caConfig))
                .requestInterceptor(new CustomInterceptor(caConfig))
//                .client(new OkHttpClient((new okhttp3.OkHttpClient.Builder()).proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8889))).build()))
                .client(new OkHttpClient())
                .logger(new Slf4jLogger())
                .target(CaFeignClient.class, url);
    }
}
