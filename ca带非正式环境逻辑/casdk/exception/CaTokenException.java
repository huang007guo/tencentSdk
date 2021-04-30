package com.wjj.application.facade.ca.casdk.exception;

/**
 * CA accessToken异常
 * @author hank
 * @since 2020/7/21 0021 下午 13:56
 */
public class CaTokenException extends CaException {
    private static final long serialVersionUID = -2895030430953857527L;

    public CaTokenException(String message, Object request, Object result) {
        super("ca Token异常,"+message, request, result);
    }

    public CaTokenException(String message) {
        super("ca Token异常,"+message);
    }

    public CaTokenException(String message, Throwable cause) {
        super("ca Token异常,"+message, cause);
    }
}
