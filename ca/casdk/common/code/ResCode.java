package com.wjj.application.facade.ca.casdk.common.code;

/**
 * ca响应结果状态码
 *
 * 错误码	消息	备注
 * 000x000	系统异常	参见常见问题 1
 * 001x001	请求对象为空
 * 000x003	参数错误
 * 005x010	对不起，厂商状态停用	请联系运营人员
 * 005x011	对不起，厂商订阅服务已停用	请联系运营人员
 * 005x012	对不起，找不到当前有效订阅服务	请联系运营人员
 * 005x013	对不起，当前免费订阅服务已到期	请联系运营人员
 * 005x016	accessToken不存在或者已过期，请重新进行授权	accessToken已过期，请重新获取
 * 005x017	厂商密钥不匹配	clientId和secret不对应，请联系运营人员确认。
 * 005x019	accessToken不匹配，请重新进行授权	有效期内，再次获取了新token，但未使用新获取的token
 */
public enum ResCode {
    SUCCESS("0","success"),
    fail("500","fail"),
    ;

    // ca返回的状态码,错误码
    private String status;
    // ca返回的状态码,错误码,对应的消息
    private String message;

    ResCode(String status, String message){
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
