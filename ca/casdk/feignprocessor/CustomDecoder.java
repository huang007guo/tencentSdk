package com.wjj.application.facade.ca.casdk.feignprocessor;

import com.alibaba.fastjson.JSON;
import com.wjj.application.facade.ca.casdk.common.code.ResCode;
import com.wjj.application.facade.ca.casdk.config.CaConfig;
import com.wjj.application.facade.ca.casdk.exception.CaResStatusException;
import com.wjj.application.facade.ca.casdk.exception.CaResponseException;
import com.wjj.application.facade.ca.casdk.util.CaUtils;
import com.wjj.application.facade.ca.casdk.vo.out.BaseOut;
import feign.Response;
import feign.codec.Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

public class CustomDecoder implements Decoder {

    private CaConfig caConfig;

    private final static Logger logger = LoggerFactory.getLogger(CustomDecoder.class);

    public CustomDecoder(CaConfig caConfig) {
        this.caConfig = caConfig;
    }

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
            String resToStr = resText.toString();
            // stamp 太长日志你不打印
            logger.info("ca response body: {}", resToStr.replaceFirst("\"stamp\":\"[^\"]*\"", "\"stamp\":\"?\""));
            Object resObject = JSON.parseObject(resToStr, type);
            if (resObject instanceof BaseOut) {
                BaseOut baseOut = (BaseOut) resObject;
                // 响应码为不成功
                if (!ResCode.SUCCESS.getStatus().equals(baseOut.getStatus())) {
                    if(ResCode.ACCESS_TOKEN_ERROR.getStatus().equals(baseOut.getStatus())){
                        // token错误,清理token
                        CaUtils.getAccessToken(caConfig, true);
                    }
                    throw new CaResStatusException(baseOut);
                }
            }
            return resObject;
        }
    }
}