/**
  * Copyright 2020 bejson.com 
  */
package com.wjj.application.facade.ca.casdk.vo.out;

/**
 * 医师状态查询接口 出参
 * openId	String	是	医网信医师唯一标识
 * userStatus	String	是	用户同步状态
 * -1：未审核
 * 0：身份审核通过
 * 1：证书签发
 * 2：设置签章
 * 3：用户注销
 * 4：申请拒绝
 * 5：用户停用
 * note	String	否	审核拒绝原因/证书信息	当拒绝时或证书签发时
 * stamp	String	否	签章图片base64
 */
public class DoctorGetUserStatusOut extends BaseOut<DoctorGetUserStatusOut.Data>{
    public static class Data {
        private String openId;
        private String userStatus;
        private String note;
        private String stamp;

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getStamp() {
            return stamp;
        }

        public void setStamp(String stamp) {
            this.stamp = stamp;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "openId='" + openId + '\'' +
                    ", userStatus='" + userStatus + '\'' +
                    ", note='" + note + '\'' +
                    ", stamp='" + stamp + '\'' +
                    '}';
        }
    }

}