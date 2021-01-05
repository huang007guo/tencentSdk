package com.wjj.application.facade.ca.casdk.feignprocessor;

import com.alibaba.fastjson.JSON;
import com.wjj.application.facade.ca.casdk.config.CaConfig;
import com.wjj.application.facade.ca.casdk.util.CaUtils;
import com.wjj.application.facade.ca.casdk.vo.in.BaseIn;
import com.wjj.application.facade.ca.casdk.vo.in.recipesyn.RecipeSynIn;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class CustomEncoder implements Encoder {
    private CaConfig caConfig;

    private final static Logger logger = LoggerFactory.getLogger(CustomEncoder.class);

    public CustomEncoder(CaConfig caConfig) {
        this.caConfig = caConfig;
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
//        System.out.println(JSON.toJSONString(object));
//        logger.info("request uri: {}; request param: {}", template.url(), object);
        if (object instanceof BaseIn) {
            BaseIn<?> baseIn = (BaseIn<?>) object;
            setHeadCommonInfo(baseIn);
        }
        // 信息同步接口
        if (object instanceof RecipeSynIn) {
            RecipeSynIn<?> recipeSynIn = (RecipeSynIn<?>) object;
            setHeadCommonInfo(recipeSynIn.getMsg());
        }
            template.body(JSON.toJSONString(object));
    }

    /**
     * 设置Head 公共数据 token和ClientId
     * @param baseIn
     */
    private void setHeadCommonInfo(BaseIn<?> baseIn){
        if(baseIn.getHead() == null){
            baseIn.setHead(new BaseIn.Head());
        }
        baseIn.getHead().setAccessToken(CaUtils.getAccessToken(caConfig));
        baseIn.getHead().setClientId(caConfig.getClientId());
    }


}
