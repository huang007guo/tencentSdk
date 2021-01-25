package com.wjj.application.facade.kangmei.exception;

/**
 * 康美参数错误异常
 * 这个异常会发邮件,不激发重试机制
 * @author hank
 * @since 2020/7/21 0021 下午 13:56
 */
public class KangmeiParamErrorException extends KangmeiBaseException {
    public KangmeiParamErrorException(String message, Object request) {
        super(message, request);
    }

    public KangmeiParamErrorException(Object request) {
        this("参数错误", request);
    }
}
