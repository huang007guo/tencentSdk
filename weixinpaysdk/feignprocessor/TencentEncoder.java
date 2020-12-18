package com.wjj.application.util.weixinpaysdk.feignprocessor;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.gson.GsonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class TencentEncoder extends GsonEncoder {
    private final static Logger logger = LoggerFactory.getLogger(TencentEncoder.class);

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
//        logger.info("request uri: {}; request param: {}", template.url(), object);
        super.encode(object, bodyType, template);
    }
}
