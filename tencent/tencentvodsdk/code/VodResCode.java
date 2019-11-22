package com.xiaochong.meet.lib.tencent.tencentvodsdk.code;

/**
 * 腾讯智能鉴黄服务响应code （ErrorCode） 参考地址： https://cloud.tencent.com/document/product/864/17713
 */
public enum VodResCode {
    SUCCESS(0,"成功"),

    //身份认证失败，一般是由于签名计算错误导致的，请参考文档中签名方法部分。
    SIG_ERROR(4100,"身份认证失败"),
    //请求的次数超过了配额限制，请参考文档请求配额部分。
    FREQUENCY_TOO_FAST(4400, "超过配额"),
    //资源标识对应的实例不存在，或者实例已经被退还，或者访问了其他用户的资源。
    NOT_EXIST_RESOURCE(5000, "资源不存在"),
    BALANCE_NOT_ENOUGH(5300, "余额不足"),
    ;
    private Integer code;
    private String msg;

    VodResCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
