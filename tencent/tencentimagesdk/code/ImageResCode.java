package com.xiaochong.meet.lib.tencent.tencentimagesdk.code;

/**
 * 腾讯智能鉴黄服务响应code （ErrorCode） 参考地址： https://cloud.tencent.com/document/product/864/17713
 */
public enum ImageResCode {
    SUCCESS(0,"成功"),

    SIG_ERROR(5,"签名串错误"),
    SIG_OVERDUE(9, "签名过期"),
    SIG_CHECK_FAIL(14, "签名校验失败"),
    FREQUENCY_TOO_FAST(15, "rest接口调用频率超过限制"),
    IMAGE_URL_ERROR(-1308, "url图片下载失败"),
    ;
    private Integer code;
    private String msg;

    ImageResCode(Integer code, String msg){
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
