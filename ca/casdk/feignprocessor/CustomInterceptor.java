package com.wjj.application.facade.ca.casdk.feignprocessor;

import com.wjj.application.facade.ca.casdk.config.CaConfig;
import com.wjj.application.facade.ca.casdk.exception.CaTokenException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomInterceptor implements RequestInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(CustomInterceptor.class);

    private CaConfig caConfig;

    public CustomInterceptor(CaConfig caConfig) {
        this.caConfig = caConfig;
    }

    @Override
    public void apply(RequestTemplate template) throws CaTokenException{
//        HashMap<String, String> headParam = new HashMap<>(2);
//        String s = new String(template.body(), StandardCharsets.UTF_8);
//        System.out.println(s);
//        headParam.put("clientId", caConfig.getClientId());
//        headParam.put("accessToken", getAccessToken());
//        template.query("head", headParam);
        logger.info("ca request uri: {}, body: {}, headers:{}", template.url(), new String(template.body()), template.headers());
    }
}
