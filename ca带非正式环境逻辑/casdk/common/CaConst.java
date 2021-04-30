package com.wjj.application.facade.ca.casdk.common;

import java.text.SimpleDateFormat;

/**
 * @author hank
 * @since 2020/12/18 0018 下午 14:07
 */
public interface CaConst {
    /**
     * 获取token地址
     */
    String URL_GET_ACCESS_TOKEN = "/device/v2/server/oauth/getAccessToken";
    String URL_RECIPE_GET_SIGN_QR_CODE = "/am/v2/recipe/getSignQRCode";
    /**
     * 时间格式
     */
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 签名回调状态
     *  * 2已签名
     *  * 6拒绝签名
     *  * 7签名订单已过期删除
     *  * 9已签名订单作废
     */
    String SIGN_CALL_BACK_STATUS_SUCCESS = "2";

    /**
     * 医师状态通知接口
     * 用户同步状态
     * 0：身份审核通过
     * 1：证书签发
     * 2：设置签章
     * 3：用户注销
     * 4：申请拒绝
     * 5：用户停用
     * 6：修改手机号
     * 7：用户启用
     */
    String PHYSICIAN_SYNC_STATUS_AUDIT_SUCCESS = "0";
    String PHYSICIAN_SYNC_STATUS_AUDIT_REJECT = "4";
    String PHYSICIAN_SYNC_STATUS_SET_STAMP = "2";

    /**
     * 设置签章状态 仅当process为2时，会回调签章状态
     * 10：待审核
     * 11：签章审核通过
     */
    String STAMP_STATUS_AUDIT_SUCCESS = "11";

}
