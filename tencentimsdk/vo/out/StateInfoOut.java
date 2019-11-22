package com.xiaochong.meet.lib.tencentimsdk.vo.out;

import java.util.List;

/**
 * 获取用户在线状态
 */
public class StateInfoOut extends TencentImOut {
    private List<ResultInfo> QueryResult;

    public List<ResultInfo> getQueryResult() {
        return QueryResult;
    }

    public void setQueryResult(List<ResultInfo> queryResult) {
        QueryResult = queryResult;
    }

    public static class ResultInfo{
        //返回的用户的 Identifier
        private String To_Account;
        /**
         * 	返回的用户状态，目前支持的状态有：
             1."Online"：客户端登录后和云通信后台有长连接
             2."PushOnline"：iOS 客户端退到后台或进程被杀或因网络问题掉线，进入 PushOnline 状态，此时仍然可以接收消息离线 APNS推送。注意，云通信后台只会保存 PushOnline 状态 7 天时间，若从掉线时刻起 7 天之内未登录过，则进入 Offline 状态。
             3."Offline"：客户端主动退出登录或者客户端自上一次登录起 7 天之内未登录过。
         */
        private String State;

        public String getTo_Account() {
            return To_Account;
        }

        public void setTo_Account(String to_Account) {
            To_Account = to_Account;
        }

        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
        }
    }
}
