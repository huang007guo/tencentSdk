package com.wjj.application.util.weixinpaysdk.vo.in;

/**
 * im状态变更回调
 * https://cloud.tencent.com/document/product/269/2570
 */
public class StateChangeIn {

    //回调命令
//    private String CallbackCommand;
    //用户上下线的信息
    private Info Info;
    public static class Info {

        //App上线或者下线的动作， Login：上线（TCP建立），Logout下线（TCP断开）
        private String Action;
        //用户的ID
        private String To_Account;
        /**
         * App上下线触发的原因。
         * 上线的原因有 Register:App TCP连接建立。
         * 下线的原因有:
         *  Unregister:App用户注销账号导致TCP断开;
         *  LinkClose:云通信检测到App TCP连接断开;
         *  Timeout：云通信检测到App心跳包超时，认为TCP已断开（客户端杀后台或Crash）
         */
        private String Reason;
        public void setAction(String Action) {
            this.Action = Action;
        }
        public String getAction() {
            return Action;
        }

        public void setTo_Account(String To_Account) {
            this.To_Account = To_Account;
        }
        public String getTo_Account() {
            return To_Account;
        }

        public void setReason(String Reason) {
            this.Reason = Reason;
        }
        public String getReason() {
            return Reason;
        }

    }
//    public void setCallbackCommand(String CallbackCommand) {
//         this.CallbackCommand = CallbackCommand;
//     }
//     public String getCallbackCommand() {
//         return CallbackCommand;
//     }

    public void setInfo(Info Info) {
         this.Info = Info;
     }
     public Info getInfo() {
         return Info;
     }

}