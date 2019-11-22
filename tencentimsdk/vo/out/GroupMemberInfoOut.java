package com.xiaochong.meet.lib.tencentimsdk.vo.out;

import java.util.List;

/**
 * 获取群组成员详细资料
 * 参考地址
 * https://cloud.tencent.com/document/product/269/1617
 */
public class GroupMemberInfoOut extends TencentImOut {
    private int MemberNum;

    private List<MemberList> MemberList ;

    public int getMemberNum() {
        return MemberNum;
    }

    public void setMemberNum(int memberNum) {
        MemberNum = memberNum;
    }

    public List<MemberList> getMemberList() {
        return MemberList;
    }

    public void setMemberList(List<MemberList> memberList) {
        MemberList = memberList;
    }

    public static class MemberList {
        //群成员 ID
        private String Member_Account;
        //群内身份包括如下几种：Owner 表示群主，Admin 表示群管理员，Member 表示群成员。
        private String Role;

        private int JoinTime;

        private int MsgSeq;

        private String MsgFlag;

        private int LastSendMsgTime;

        private int ShutUpUntil;

        private List<AppMemberDefinedData> AppMemberDefinedData ;

        public void setMember_Account(String Member_Account){
            this.Member_Account = Member_Account;
        }
        public String getMember_Account(){
            return this.Member_Account;
        }
        public void setRole(String Role){
            this.Role = Role;
        }
        public String getRole(){
            return this.Role;
        }
        public void setJoinTime(int JoinTime){
            this.JoinTime = JoinTime;
        }
        public int getJoinTime(){
            return this.JoinTime;
        }
        public void setMsgSeq(int MsgSeq){
            this.MsgSeq = MsgSeq;
        }
        public int getMsgSeq(){
            return this.MsgSeq;
        }
        public void setMsgFlag(String MsgFlag){
            this.MsgFlag = MsgFlag;
        }
        public String getMsgFlag(){
            return this.MsgFlag;
        }
        public void setLastSendMsgTime(int LastSendMsgTime){
            this.LastSendMsgTime = LastSendMsgTime;
        }
        public int getLastSendMsgTime(){
            return this.LastSendMsgTime;
        }
        public void setShutUpUntil(int ShutUpUntil){
            this.ShutUpUntil = ShutUpUntil;
        }
        public int getShutUpUntil(){
            return this.ShutUpUntil;
        }
        public void setAppMemberDefinedData(List<AppMemberDefinedData> AppMemberDefinedData){
            this.AppMemberDefinedData = AppMemberDefinedData;
        }
        public List<AppMemberDefinedData> getAppMemberDefinedData(){
            return this.AppMemberDefinedData;
        }
    }
    public static class AppMemberDefinedData {
        private String Key;

        private String Value;

        public void setKey(String Key){
            this.Key = Key;
        }
        public String getKey(){
            return this.Key;
        }
        public void setValue(String Value){
            this.Value = Value;
        }
        public String getValue(){
            return this.Value;
        }

    }
}
