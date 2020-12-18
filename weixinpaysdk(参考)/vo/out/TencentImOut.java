package com.wjj.application.util.weixinpaysdk.vo.out;

public class TencentImOut {
    //请求处理的结果，OK表示处理成功，FAIL表示失败
    private String ActionStatus;

    //错误码
    private Integer ErrorCode;

    //错误信息
    private String ErrorInfo;


    public String getActionStatus() {
        return ActionStatus;
    }

    public void setActionStatus(String actionStatus) {
        ActionStatus = actionStatus;
    }

    public Integer getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(Integer errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorInfo() {
        return ErrorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        ErrorInfo = errorInfo;
    }
}
