package com.xiaochong.meet.lib.tencentimsdk.code;

/**
 * 腾讯im服务响应code （ErrorCode） 参考地址： https://cloud.tencent.com/document/product/269/1671
 */
public enum ImResCode {
    SUCCESS(0,"成功"),

    SIG_OVERDUE(70001,"sig 过期"),
    FREQUENCY_TOO_FAST(60007, "rest接口调用频率超过限制"),
    APPID_FREQUENCY_TOO_FAST(60011, "appid请求频率超限")
    ;
    private Integer code;
    private String msg;

    ImResCode(Integer code, String msg){
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
