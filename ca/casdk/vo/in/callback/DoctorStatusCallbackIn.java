package com.wjj.application.facade.ca.casdk.vo.in.callback;

import com.wjj.application.facade.ca.casdk.vo.in.BaseIn;

/**
 * 签名状态通知接口
 *
 * urId	String	是	处方ID	2015120417541766
 * uniqueId	String	是	医网信处方唯一标识
 * status	int	是	签名订单状态
 * 2已签名
 * 6拒绝签名
 * 7签名订单已过期删除
 * 9已签名订单作废
 * signedData	String	否	处方哈希的 P7签名（仅在签名成功时回调）
 * signedPdfBase64	String	否	pdf文件流（base64编码，（仅在pdf签名成功状态时回调））
 * reason	String	否	拒绝签名原因（仅在拒绝签名状态时回调）
 * deleteTime	String	否	作废时间（仅在作废状态时回调），yyyy-MM-dd HH:mm:ss
 * openId	String	是	签名医师openId
 * @author hank
 * @since 2020/12/25 0025 下午 14:23
 */
public class DoctorStatusCallbackIn extends BaseIn<DoctorStatusCallbackIn.Body> {
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
    }
}
