package com.xiaochong.meet.lib.tencent.tencentimagesdk.vo.out;

import java.util.List;

/**
 * 天御图片鉴黄 参考：
 * https://cloud.tencent.com/document/product/669/14415
 */
public class ImagePornContentSecurityOut {
    //公共错误码 0：表示成功 其他值：表示失败
    private int code;
    //业务侧错误码 成功时返回Success
    private String codeDesc;
    //识别内容详情
    private List<Data> data;
    //模块错误信息描述
    private String message;

    public static class Data {
        //识别类型
        private String category;
        //识别为label的概率
        private int confidence;
        /**
         * 	porn	色情
            hot	性感
            breast	凸显胸部
            ass	凸显臀部或大腿
            bareBody	裸露身体
            unrealHotPeople	非真实人物
         */
        private String label;
        //具体服务错误码 https://cloud.tencent.com/document/product/669/14415#b
        private int subCode;
        //建议 pass：通过 review：人工审核 block：阻止传播
        private String suggestion;
        public void setCategory(String category) {
            this.category = category;
        }
        public String getCategory() {
            return category;
        }

        public void setConfidence(int confidence) {
            this.confidence = confidence;
        }
        public int getConfidence() {
            return confidence;
        }

        public void setLabel(String label) {
            this.label = label;
        }
        public String getLabel() {
            return label;
        }

        public void setSubCode(int subCode) {
            this.subCode = subCode;
        }
        public int getSubCode() {
            return subCode;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }
        public String getSuggestion() {
            return suggestion;
        }
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }
    public String getCodeDesc() {
        return codeDesc;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
