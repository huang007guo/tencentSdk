package com.wjj.application.facade.ca.casdk.exception;

import feign.Response;

/**
 * CA http响应不为200,抛出这个
 * @author hank
 * @since 2020/7/21 0021 下午 13:56
 */
public class CaResponseException extends CaException {
    private static final long serialVersionUID = -495614963808428662L;

    public CaResponseException(Response response) {
        this("", null, response);
    }
    public CaResponseException(String message, Object request, Response response) {
        super("ca请求响应异常 " + message, request, response);
    }

}
