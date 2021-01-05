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
public class SignCallbackIn extends BaseIn<SignCallbackIn.Body> {
    public static class Body {

        private String signedData;
        private String uniqueId;
        private String status;
        private String signedPdfBase64;
        private String reason;
        private String deleteTime;
        private String openId;
        private String urId;
        public void setSignedData(String signedData) {
            this.signedData = signedData;
        }
        public String getSignedData() {
            return signedData;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }
        public String getUniqueId() {
            return uniqueId;
        }

        public void setStatus(String status) {
            this.status = status;
        }
        public String getStatus() {
            return status;
        }

        public void setUrId(String urId) {
            this.urId = urId;
        }
        public String getUrId() {
            return urId;
        }

        public String getSignedPdfBase64() {
            return signedPdfBase64;
        }

        public void setSignedPdfBase64(String signedPdfBase64) {
            this.signedPdfBase64 = signedPdfBase64;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getDeleteTime() {
            return deleteTime;
        }

        public void setDeleteTime(String deleteTime) {
            this.deleteTime = deleteTime;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        @Override
        public String toString() {
            return "Body{" +
                    "signedData='" + signedData + '\'' +
                    ", uniqueId='" + uniqueId + '\'' +
                    ", status='" + status + '\'' +
                    ", reason='" + reason + '\'' +
                    ", deleteTime='" + deleteTime + '\'' +
                    ", openId='" + openId + '\'' +
                    ", urId='" + urId + '\'' +
                    '}';
        }
    }
}
