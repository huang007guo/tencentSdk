package com.xiaochong.meet.lib.tencentimsdk.code;

public interface ImCallbackCommand {
    //创建群组之后回调
    String GCallbackAfterCreateGroup = "Group.CallbackAfterCreateGroup";
    //新成员入群之后回调
    String GCallbackAfterNewMemberJoin = "Group.CallbackAfterNewMemberJoin";
    //群成员离开之后回调
    String GCallbackAfterMemberExit = "Group.CallbackAfterMemberExit";
    //群组解散之后回调
    String GCallbackAfterGroupDestroyed = "Group.CallbackAfterGroupDestroyed";

    //状态变更回调
    String StateChange = "State.StateChange";
}
