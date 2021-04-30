/**
  * Copyright 2020 bejson.com 
  */
package com.wjj.application.facade.ca.casdk.vo.out;

/**
 * Auto-generated: 2020-12-18 14:41:23
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TokenData {

    private Data data;
    private String message;
    private String status;
    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

    public void setMessage(String message) {
         this.message = message;
     }
     public String getMessage() {
         return message;
     }

    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public static class Data {

        private String accessToken;
        private String expiresIn;
        private String scope;
        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
        public String getAccessToken() {
            return accessToken;
        }

        public void setExpiresIn(String expiresIn) {
            this.expiresIn = expiresIn;
        }
        public String getExpiresIn() {
            return expiresIn;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getScope() {
            return scope;
        }

    }
}