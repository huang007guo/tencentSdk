package com.wjj.application.facade.kangmei.exception;

import com.wjj.application.facade.kangmei.model.save.Result;

/**
 * 康美返回结果不正确,或者返回结果为失败
 * @author hank
 * @since 2020/7/21 0021 下午 13:56
 */
public class KangmeiResultException extends KangmeiBaseException {
    public KangmeiResultException(String message, Object request, Result result) {
        super(message, request, result);
    }

    public KangmeiResultException(String message) {
        super(message);
    }

    public KangmeiResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
