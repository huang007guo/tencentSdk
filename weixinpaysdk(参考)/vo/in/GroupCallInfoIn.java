package com.wjj.application.util.weixinpaysdk.vo.in;
import java.util.List;

/**
 * 群回调信息
 */
public class GroupCallInfoIn {

    //回调命令
//    private String CallbackCommand;
    //操作的群ID
    private String GroupId;
    //发起创建群组请求的操作者ID
    private String Operator_Account;
    //请求创建的群的 群主ID
    private String Owner_Account;
    //请求创建的群组类型，例如Private，Public和ChatRoom, https://cloud.tencent.com/document/product/269/1502#2-.E7.BE.A4.E7.BB.84.E5.BD.A2.E6.80.81.E4.BB.8B.E7.BB.8D
    private String Type;
    //请求创建的群组的名称
    private String Name;
    //请求创建的群组的初始化成员列表 | 被解散群的成员列表
    private List<MemberList> MemberList;
    //用户建群时的自定义字段，这个字段默认是没有的，需要开通，详见自定义字段。 https://cloud.tencent.com/document/product/269/1502#6-.E8.87.AA.E5.AE.9A.E4.B9.89.E5.AD.97.E6.AE.B5
    private List<UserDefinedDataList> UserDefinedDataList;

    //成员离开方式：Kicked为被群主踢出；Quit为主动退群
    private String ExitType;
    //退出群的成员列表
    private List<MemberList> ExitMemberList;

    //入群方式：Apply（申请入群）；Invited（邀请入群）
    private String JoinType;
    //新入群成员ID集合
    private List<MemberList> NewMemberList;


    public String getJoinType() {
        return JoinType;
    }

    public void setJoinType(String joinType) {
        JoinType = joinType;
    }

    public List<GroupCallInfoIn.MemberList> getNewMemberList() {
        return NewMemberList;
    }

    public void setNewMemberList(List<GroupCallInfoIn.MemberList> newMemberList) {
        NewMemberList = newMemberList;
    }

    public List<GroupCallInfoIn.MemberList> getExitMemberList() {
        return ExitMemberList;
    }

    public void setExitMemberList(List<GroupCallInfoIn.MemberList> exitMemberList) {
        ExitMemberList = exitMemberList;
    }

    public String getExitType() {
        return ExitType;
    }

    public void setExitType(String exitType) {
        ExitType = exitType;
    }

    public static class MemberList {

        private String Member_Account;
        public void setMember_Account(String Member_Account) {
            this.Member_Account = Member_Account;
        }
        public String getMember_Account() {
            return Member_Account;
        }

    }

    public static class UserDefinedDataList {

        private String Key;
        private String Value;
        public void setKey(String Key) {
            this.Key = Key;
        }
        public String getKey() {
            return Key;
        }

        public void setValue(String Value) {
            this.Value = Value;
        }
        public String getValue() {
            return Value;
        }

    }

//    public void setCallbackCommand(String CallbackCommand) {
//        this.CallbackCommand = CallbackCommand;
//    }
//    public String getCallbackCommand() {
//        return CallbackCommand;
//    }

    public void setGroupId(String GroupId) {
        this.GroupId = GroupId;
    }
    public String getGroupId() {
        return GroupId;
    }

    public void setOperator_Account(String Operator_Account) {
        this.Operator_Account = Operator_Account;
    }
    public String getOperator_Account() {
        return Operator_Account;
    }

    public void setOwner_Account(String Owner_Account) {
        this.Owner_Account = Owner_Account;
    }
    public String getOwner_Account() {
        return Owner_Account;
    }

    public void setType(String Type) {
        this.Type = Type;
    }
    public String getType() {
        return Type;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    public String getName() {
        return Name;
    }

    public void setMemberList(List<MemberList> MemberList) {
        this.MemberList = MemberList;
    }
    public List<MemberList> getMemberList() {
        return MemberList;
    }

    public void setUserDefinedDataList(List<UserDefinedDataList> UserDefinedDataList) {
        this.UserDefinedDataList = UserDefinedDataList;
    }
    public List<UserDefinedDataList> getUserDefinedDataList() {
        return UserDefinedDataList;
    }

}