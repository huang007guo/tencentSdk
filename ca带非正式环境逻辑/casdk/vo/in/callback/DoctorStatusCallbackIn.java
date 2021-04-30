package com.wjj.application.facade.ca.casdk.vo.in.callback;

import com.wjj.application.facade.ca.casdk.vo.in.BaseIn;

/**
 * 签名状态通知接口
 *
 openId	String	是	医网信医师唯一标识
 phoneNum	String	是	用户手机号
 process	String	是	用户同步状态
 0：身份审核通过
 1：证书签发
 2：设置签章
 3：用户注销
 4：申请拒绝
 5：用户停用
 6：修改手机号
 7：用户启用
 time	String	是	操作时间，yyyy-MM-dd HH:mm:ss
 note	String	否	审核拒绝原因/证书信息	当拒绝时或证书签发时
 stamp	String	否	仅当process为2时，会回调签章图片base64
 stampStatus	String	否	仅当process为2时，会回调签章状态
 10：待审核
 11：签章审核通过
 * @author hank
 * @since 2020/12/25 0025 下午 14:23
 */
public class DoctorStatusCallbackIn extends BaseIn<DoctorStatusCallbackIn.Body> {
    /**
     * 是否是第二次回调(在非正式环境,会转发一次回调,用这个标识)
     */
    private Boolean isTwice = false;

    public Boolean getIsTwice() {
        return isTwice;
    }

    public void setIsTwice(Boolean isTwice) {
        this.isTwice = isTwice;
    }
    public static class Body {

        private String process;
        private String phoneNum;
        private String time;
        private String openId;
        private String note;
        private String stamp;
        private String stampStatus;

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
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

        public String getStampStatus() {
            return stampStatus;
        }

        public void setStampStatus(String stampStatus) {
            this.stampStatus = stampStatus;
        }

        @Override
        public String toString() {
            return "Body{" +
                    "process='" + process + '\'' +
                    ", phoneNum='" + phoneNum + '\'' +
                    ", time='" + time + '\'' +
                    ", openId='" + openId + '\'' +
                    ", note='" + note + '\'' +
                    ", stamp='" + stamp + '\'' +
                    ", stampStatus='" + stampStatus + '\'' +
                    '}';
        }
    }
}
