package com.wjj.application.config;

import com.wjj.application.controller.saas.ca.CaNonProdController;
import com.wjj.application.util.SpringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 非正式环境的bean中心
 * @author hank
 * @since 2021/4/1 0001 下午 13:52
 */
@ConditionalOnExpression(value = "!'${spring.profiles.active:${env:" + SpringUtils.PROD_ACTIVE +  "}}'.equals('prod') && !${isProd:false}")
@Configuration
public class NonProductionConfig {

    @Bean
    public CaNonProdController getCaNonProdController(){
        return new CaNonProdController();
    }
}
