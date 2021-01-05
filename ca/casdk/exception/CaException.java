package com.wjj.application.facade.ca.casdk.exception;

/**
 * CA基础异常
 * @author hank
 * @since 2020/7/21 0021 下午 13:56
 */
public class CaException extends RuntimeException {
    private static final long serialVersionUID = 5207538472062548189L;
    private Object result;
    private Object request;

    public CaException(String message, Object request, Object result) {
        super(message);
        this.result = result;
        this.request = request;
    }

    public CaException(String message, Object request) {
        this(message, request, null);
    }

    public CaException(String message) {
        super(message);
    }

    public CaException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "CA异常,"+super.getMessage() +
                "\n request=" + request +
                "\n result=" + result;
    }

    public Object getResult() {
        return result;
    }

    public Object getRequest() {
        return request;
    }

    @Override
    public String toString() {
        return "CaBaseException{" +
                "request=" + request +
                ", result=" + result +
                ", message=" + super.getMessage() +
                '}';
    }
}
