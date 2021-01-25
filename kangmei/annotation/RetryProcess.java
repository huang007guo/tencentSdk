package com.wjj.application.facade.kangmei.annotation;

import com.wjj.application.facade.kangmei.exception.KangmeiBaseException;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 康美接口重试注解
 * 1.这里要注意入参会重复使用,set的值会保留
 * 2.KangmeiBaseException,不会进行重试
 * @author hank
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RetryProcess {
    //重试的次数,默认2次,总共3次
    int value() default 2;

    /**
     * 接口名
     */
    String apiName();

    /**
     * 重试失败是否发邮件
     * @return
     */
    boolean isRetryFailSendEmail() default true;
    /**
     * 不重试的异常
     * @return
     */
    Class<? extends Throwable>[] notRetryThrowable() default {KangmeiBaseException.class};
}