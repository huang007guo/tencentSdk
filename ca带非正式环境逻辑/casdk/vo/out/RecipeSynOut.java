/**
  * Copyright 2020 bejson.com 
  */
package com.wjj.application.facade.ca.casdk.vo.out;

/**
 * 签名信息同步服务 返回
 * uniqueId	String	是	同步数据唯一标识
 * timeStampSignData	String	否	同步数据时间戳	仅在开通时间戳服务后返回
 * selfSign	boolean	是	自动签名状态
 */
public class RecipeSynOut extends BaseOut<RecipeSynOut.Data>{
    @lombok.Data
    public static class Data {
        private String uniqueId;
        private String timeStampSignData;
        private Boolean selfSign;
    }
}