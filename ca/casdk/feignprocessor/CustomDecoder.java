package com.wjj.application.facade.ca.casdk.feignprocessor;

import com.alibaba.fastjson.JSON;
import com.wjj.application.facade.ca.casdk.common.code.ResCode;
import com.wjj.application.facade.ca.casdk.exception.CaResStatusException;
import com.wjj.application.facade.ca.casdk.exception.CaResponseException;
import com.wjj.application.facade.ca.casdk.vo.out.BaseOut;
import feign.Response;
import feign.codec.Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

public class CustomDecoder implements Decoder {

    private final static Logger logger = LoggerFactory.getLogger(CustomDecoder.class);

    @Override
    public Object decode(Response response, Type type) throws IOException {
        logger.info("ca response status: {}", response.status());
        // http响应错误
        if (response.status() != 200)
            throw new CaResponseException(response);
        if (response.body() == null)
            return null;
        // 读取body
        try(Reader reader = response.body().asReader()) {
            StringBuilder resText = new StringBuilder();
            int nowByte;
            while ((nowByte = reader.read()) != -1) {
                resText.append((char) nowByte);
            }
            logger.debug("ca response body: {}", resText);
            Object resObject = JSON.parseObject(resText.toString(), type);
            if (resObject instanceof BaseOut) {
                BaseOut baseOut = (BaseOut) resObject;
                // 响应码为不成功
                if (!ResCode.SUCCESS.getStatus().equals(baseOut.getStatus())) {
                    throw new CaResStatusException(baseOut);
                }
            }
            return resObject;
        }
    }
}