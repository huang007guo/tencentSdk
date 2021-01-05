/**
  * Copyright 2020 bejson.com 
  */
package com.wjj.application.facade.ca.casdk.vo.out;

/**
 * 医师信息同步接口 出参
 * openId	String	是	医网信医师唯一标识
 */
public class DoctorSynOut extends BaseOut<DoctorSynOut.Data>{
    public static class Data {
        private String openId;
        public void setOpenId(String openId) {
            this.openId = openId;
        }
        public String getOpenId() {
            return openId;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "openId='" + openId + '\'' +
                    '}';
        }
    }

}