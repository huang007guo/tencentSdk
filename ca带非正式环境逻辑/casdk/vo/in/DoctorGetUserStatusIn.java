/**
  * Copyright 2020 bejson.com 
  */
package com.wjj.application.facade.ca.casdk.vo.in;

/**
 * 医师状态查询接口
 clientId	String	是	第三方厂商标识	参见医网信接入说明
 accessToken	String	是	授权码	参见医网信接入说明
 openId	String	否	医师openId
 uidCardType	String	否	证件类型
 userIdCardNum	String	否	证件号
 phone	String	否	手机号
 */
public class DoctorGetUserStatusIn extends BaseIn.Head{
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return "DoctorGetUserStatusIn{" +
                "openId='" + openId + '\'' +
                '}';
    }
}