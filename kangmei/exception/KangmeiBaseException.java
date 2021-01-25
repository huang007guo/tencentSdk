package com.wjj.application.facade.kangmei.exception;

import com.wjj.application.facade.kangmei.model.save.Result;

/**
 * 康美基础异常
 * 这个异常会发邮件,不激发重试机制
 * @author hank
 * @since 2020/7/21 0021 下午 13:56
 */
public class KangmeiBaseException extends Exception {
    private Result result;
    private Object request;

    public KangmeiBaseException(String message, Object request, Result result) {
        super(message);
        this.result = result;
        this.request = request;
    }

    public KangmeiBaseException(String message, Object request) {
        this(message, request, null);
    }

    public KangmeiBaseException(String message) {
        super(message);
    }

    public KangmeiBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "康美异常,"+super.getMessage() +
                "\n request=" + request +
                "\n result=" + result;
    }

    public Result getResult() {
        return result;
    }

    public Object getRequest() {
        return request;
    }

    @Override
    public String toString() {
        return "KangmeiBaseException{" +
                "request=" + request +
                ", result=" + result +
                ", message=" + super.getMessage() +
                '}';
    }
}
