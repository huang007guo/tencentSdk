/**
  * Copyright 2020 bejson.com 
  */
package com.wjj.application.facade.ca.casdk.vo.in.recipesyn;

import com.wjj.application.facade.ca.casdk.vo.in.BaseIn;

/**
 *
 * 签名信息同步服务
 * 医师开具处方等类型签数据后，厂商将数据同步医网信
 * 1 请求参数中body由公共参数+模板参数组成，医网信将对整个body串固化签名。
 * 2 已同步数据有效期7天，如自厂商同步数据起7天内，医师未签名，数据状态会变为已过期，不能再签名。
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RecipeSynIn<T> {

    /**
     * 签名方式
     * 0：推送签名（仅限推送到医网信APP）
     * 1：SDK集成签名
     * 2：PC二维码签名
     */
    private String signType;
    private BaseIn<T> msg;

    public RecipeSynIn() {
        this.msg = new BaseIn<T>();
    }

    public void setSignType(String signType) {
         this.signType = signType;
     }
     public String getSignType() {
         return signType;
     }

    public void setMsg(BaseIn<T> msg) {
         this.msg = msg;
     }
     public BaseIn<T> getMsg() {
         return msg;
     }

}