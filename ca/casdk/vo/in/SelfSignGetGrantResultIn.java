/**
  * Copyright 2020 bejson.com 
  */
package com.wjj.application.facade.ca.casdk.vo.in;

/**
 *  自动签名授权-获取授权结果接口 入参
 *
 openId	String	是	用户标识openId
 sysTag	String	否	系统类型
 */
public class SelfSignGetGrantResultIn{
    private String openId;
    private String sysTag;

    public SelfSignGetGrantResultIn(String openId, String sysTag) {
        this.openId = openId;
        this.sysTag = sysTag;
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
}