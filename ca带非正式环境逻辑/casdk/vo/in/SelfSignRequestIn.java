/**
  * Copyright 2020 bejson.com 
  */
package com.wjj.application.facade.ca.casdk.vo.in;

/**
 *  自动签名授权-请求接口 入参
 *
 openId	String	是	用户标识openId
 sessionTime	int	是	授权会话时间，单位小时，默认4小时，最长不超过16小时	12
 notifyUrl	String	是	授权通知地址，用户确认授权会回调通知该接口。注意：请求会话60s有效，60秒以内未收到回调确认视为授权超时	http://domain/call
 sysTag	String	否	系统类型，同步签名数据带着次标记才被自动签名，不传递标记，默认不做授权的系统隔离限制，全部自动签名。
 */
public class SelfSignRequestIn {
    private String openId;
    private String sysTag;
    private String notifyUrl;
    private Integer sessionTime;

    public SelfSignRequestIn(String openId, String notifyUrl, Integer sessionTime) {
        this.openId = openId;
        this.notifyUrl = notifyUrl;
        this.sessionTime = sessionTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSysTag() {
        return sysTag;
    }

    public void setSysTag(String sysTag) {
        this.sysTag = sysTag;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Integer getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Integer sessionTime) {
        this.sessionTime = sessionTime;
    }

    @Override
    public String toString() {
        return "SelfSignRequestIn{" +
                "openId='" + openId + '\'' +
                ", sysTag='" + sysTag + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", sessionTime=" + sessionTime +
                '}';
    }
}