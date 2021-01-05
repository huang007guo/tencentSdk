package com.wjj.application.facade.ca.casdk.vo.in.callback;

import com.wjj.application.facade.ca.casdk.vo.in.BaseIn;

/**
 * 签名状态通知接口
 *
 openId	String	是	用户开放标识
 result	Boolean	是	确认结果
     true 同意
     false 拒绝
 * @author hank
 * @since 2020/12/25 0025 下午 14:23
 */
public class AutoSignCallbackIn extends BaseIn<AutoSignCallbackIn.Body> {
    public static class Body {

        private String openId;
        private Boolean result;

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public Boolean getResult() {
            return result;
        }

        public void setResult(Boolean result) {
            this.result = result;
        }
    }
}
