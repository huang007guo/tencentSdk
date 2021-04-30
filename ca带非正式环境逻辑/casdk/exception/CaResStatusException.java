package com.wjj.application.facade.ca.casdk.exception;


import com.wjj.application.facade.ca.casdk.vo.out.BaseOut;

/**
 * CA 状态码不为成功抛出这个异常
 * @author hank
 * @since 2020/7/21 0021 下午 13:56
 */
public class CaResStatusException extends CaException {
    private static final long serialVersionUID = -1172128179082524698L;

    private BaseOut baseOut;

    public CaResStatusException(BaseOut result) {
        super("ca返回状态码不为成功", null, result);
        this.baseOut = result;
    }
    public BaseOut getBaseOut() {
        return baseOut;
    }
}
