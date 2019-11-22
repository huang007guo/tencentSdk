package com.xiaochong.meet.lib.tencent.consts;

public interface TencentConst {
    //任务流状态变更 事件
    String EVENT_TYPE_PROCEDURE_STATE_CHANGED = "ProcedureStateChanged";

    /**
     * 点播 -------------------------------
     */
    //点播鉴黄回调
    String VOD_PORN_CALL_EVENT_TYPE_KEY = "eventType";
    String VOD_PORN_CALL_DATA_KEY = "data";

    /**
     * 点播 end ----------------------------
     */

    /**
     * 直播 -------------------------------
     */
    //直播涉黄回调
    String LIVE_PORN_CALL_SIGNATURE_HEADER = "TPD-CallBack-Auth";
    String LIVE_PORN_CALL_SECRET_ID_HEADER = "TPD-SecretID";

    String LIVE_PORN_CALL_RES_JSON = "{\"code\":0}";
    /**
     * 直播 end ---------------------------
     */

    /**
     * im ---------------------------------
     */
    //im回调
    String IM_CALL_REQ_KEY_SDK_APP_ID = "SdkAppid";
    String IM_CALL_REQ_KEY_CALLBACK_COMMAND = "CallbackCommand";
    String IM_CALL_RES_JSON = "{\"ActionStatus\":\"OK\",\"ErrorInfo\":\"\",\"ErrorCode\":0}";

    //群主角色
    String GROUP_OWNER_ROLE = "Owner";
    /**
     * im end ------------------------------
     */
}
