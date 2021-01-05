/**
  * Copyright 2020 bejson.com 
  */
package com.wjj.application.facade.ca.casdk.vo.in;

/**
 * 入参基础类
 * @author hank
 * @website http://www.bejson.com/java2pojo/
 */
public class BaseIn<B> {
    private Head head;
    private B body;

    public BaseIn() {
        head = new Head();
    }

    public BaseIn(B body) {
        this();
        this.body = body;
    }

    public BaseIn(Head head, B body) {
        this.head = head;
        this.body = body;
    }

    /**
     * 通过body获取请求单例
     * @param body body
     * @param tClass 请求类
     * @param <T> 请求类类型
     * @param <B> body类型
     * @return 请求单例
     */
    public static <T extends BaseIn<B>, B> T build(B body, Class<T> tClass){
        try {
            T newInstance = tClass.newInstance();
            newInstance.setBody(body);
            return newInstance;
        }catch (Exception e){
            // 这里异常直接返回null,因为这里是个替换构建的工具类
            return null;
        }
    }

    public void setHead(Head head) {
         this.head = head;
     }
    public Head getHead() {
         return head;
     }

    public void setBody(B body) {
         this.body = body;
     }
    public B getBody() {
         return body;
     }

    @Override
    public String toString() {
        return "BaseIn{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }

    public static class Head {
        /**
         * 自动注入
         */
        private String clientId;
        /**
         * 自动注入
         */
        private String accessToken;
        /**
         * 明文模板:
         * recipe：处方
         * viewport：影像
         * diagnosis：诊断报告
         * medicalRecord：病历
         * consultation：会诊意见书
         * Laboratory：检验模板参数
         * personPdf：个人pdf签名
         */
        private String templateId;

        /**
         * 订阅服务Id
         */
        private String serviceId;

        /**
         * Body签名值
         */
        private String sign;


        public void setClientId(String clientId) {
            this.clientId = clientId;
        }
        public String getClientId() {
            return clientId;
        }
        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
        public String getAccessToken() {
            return accessToken;
        }

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}