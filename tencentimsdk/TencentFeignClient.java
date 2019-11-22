package com.xiaochong.meet.lib.tencentimsdk;

import com.xiaochong.meet.lib.tencentimsdk.vo.in.ImMsgBody;
import com.xiaochong.meet.lib.tencentimsdk.vo.out.*;
import feign.Body;
import feign.Param;
import feign.RequestLine;

/**
 *  腾讯rest api服务client
 *  ！注意： 一般情况最高调用频率，100次/秒。如需提升调用频率，请根据 工单模板 提交工单申请处理。！
 */
public interface TencentFeignClient {
    /**
     * 独立模式账号导入接口 这里可用设置 昵称 头像
     * @param Identifier 必填 用户名，长度不超过 32 字节
     * @param Nick 选填 用户昵称
     * @param FaceUrl 选填 用户头像URL。
     * @param Type 选填 帐号类型，开发者默认无需填写，值0表示普通帐号，1表示机器人帐号。
     * @return
     */
    @RequestLine("POST /im_open_login_svc/account_import")
    TencentImOut accountImport(
            @Param("Identifier") String Identifier,
            @Param("Nick") String Nick,
            @Param("FaceUrl") String FaceUrl,
            @Param("Type") Integer Type
    );

    /**
     * 获取用户在线状态
     * @param To_Account 必填 需要查询这些 Identifier 的登录状态， 一次最多查询 500 个 Identifier 的状态
     * @return
     */
    @RequestLine("POST /openim/querystate")
    StateInfoOut queryState(
            @Param("To_Account") String[] To_Account
    );

    /**
     * 查询APP自定义脏字
     * @return
     */
    @RequestLine("POST /openim_dirty_words/get")
    @Body("{}")
    DirtyWordsOut getImDirtyWords();

    /**
     * 添加APP自定义脏字
     * @param DirtyWordsList 自定义脏字列表（必填），列表中的脏字不能超过 50 个, 每个自定义脏字不能超过 200 字节
     * @return
     */
    @RequestLine("POST /openim_dirty_words/add")
    TencentImOut addImDirtyWords(
            @Param("DirtyWordsList") String[] DirtyWordsList
    );

    /**
     * 删除APP自定义脏字
     * @param DirtyWordsList
     * @return
     */
    @RequestLine("POST /openim_dirty_words/delete")
    TencentImOut deleteImDirtyWords(
            @Param("DirtyWordsList") String[] DirtyWordsList
    );

    /**
     * 设置全局禁言（启用 禁用）
     * @param Set_Account	必填	设置禁言配置的帐号。
     * @param C2CmsgNospeakingTime 选填	 单聊消息禁言时间，秒为单位，非负整数，最大值为 4294967295(十六进制 0xFFFFFFFF)。等于 0 代表取消账户禁言；等于最大值 4294967295(十六进制 0xFFFFFFFF)代表账户永久被设置禁言；其它代表该账户禁言时间。
     * @param GroupmsgNospeakingTime 选填	群组消息禁言时间，秒为单位，非负整数，最大值为 4294967295(十六进制0xFFFFFFFF)。等于 0 代表取消帐号禁言；最大值 4294967295(0xFFFFFFFF)代表账户永久禁言；其它代表该账户禁言时间。
     * @return
     */
    @RequestLine("POST /openconfigsvr/setnospeaking")
    TencentImOut setNoSpeaking(
            @Param("Set_Account") String Set_Account,
            @Param("C2CmsgNospeakingTime") Long C2CmsgNospeakingTime,
            @Param("GroupmsgNospeakingTime") Long GroupmsgNospeakingTime
    );

    /**
     * 查询全局禁言
     * @param Get_Account 必填	查询禁言信息的帐号
     * @return
     */
    @RequestLine("POST /openconfigsvr/getnospeaking")
    NoSpeakingInfoOut getNoSpeaking(
            @Param("Get_Account") String Get_Account
    );

    /**
     * 获取群组成员详细资料
     * 可用来查询 实时音视频房间 成员
     * @param GroupId 必填	群组ID（必填）
     * @return
     */
    @RequestLine("POST /group_open_http_svc/get_group_member_info")
    GroupMemberInfoOut getGroupMemberInfo(
            @Param("GroupId") String GroupId

    );

    /**
     * 群 批量禁言和取消禁言
     * 互动直播踢人
     *     互动直播聊天室不支持删除群成员，对这种类型的群组进行操作时会返回 10004 错误。如果管理员希望达到删除群成员的效果，可以通过 设置禁言 的方式实现。
     *
     * @param GroupId 必填	群组ID（必填）
     * @param Members_Account 需要禁言的用户帐号，最多支持 500 个帐号。
     * @param ShutUpTime 需禁言时间，单位为秒，为 0 时表示取消禁言。
     * @return
     */
    @RequestLine("POST /group_open_http_svc/forbid_send_msg")
    TencentImOut groupForbidSendMsg(
            @Param("GroupId") String GroupId,
            @Param("Members_Account") String[] Members_Account,
            @Param("ShutUpTime") int ShutUpTime
    );

    /**
     * 群 踢人
     * @param GroupId 必填	操作的群 ID
     * @param MemberToDel_Account 必填	待删除的群成员
     * @param Silence 选填	是否静默删人。0：非静默删人；1：静默删人。不填该字段默认为 0
     * @param Reason 选填	踢出用户原因
     * @return
     */
    @RequestLine("POST /group_open_http_svc/delete_group_member")
    TencentImOut groupDeleteMember(
            @Param("GroupId") String GroupId,
            @Param("MemberToDel_Account") String[] MemberToDel_Account,
            @Param("Silence") Integer Silence,
            @Param("Reason") String Reason
    );

    /**
     * 解散群
     * @param GroupId 必填	操作的群 ID
     * @return
     */
    @RequestLine("POST /group_open_http_svc/destroy_group")
    TencentImOut groupDestroy(
            @Param("GroupId") String GroupId
    );

    /**
     * 单发单聊消息
     * @param From_Account 消息发送方帐号（用于指定发送消息方帐号）
     * @param To_Account 消息接收方帐号
     * @param MsgRandom 消息随机数,由随机函数产生。（用作消息去重）
     * @param MsgBody 消息内容，具体格式请参考 消息格式描述。（注意，一条消息可包括多种消息元素，MsgBody 为 Array 类型） https://cloud.tencent.com/document/product/269/2720
     * @param SyncOtherMachine 1：把消息同步到 From_Account 在线终端和漫游上 2：消息不同步至 From_Account；若不填写默认情况下会将消息存 From_Account 漫游
     * @param OfflinePushInfo 离线推送信息配置 https://cloud.tencent.com/document/product/269/2720
     * @return
     */
    @RequestLine("POST /openim/sendmsg")
    TencentImOut sendmsg(
            @Param("From_Account") String From_Account,
            @Param("To_Account") String To_Account,
            @Param("MsgRandom") Integer MsgRandom,
            @Param("MsgBody") ImMsgBody[] MsgBody,
            @Param("SyncOtherMachine") Integer SyncOtherMachine,
            @Param("OfflinePushInfo") Object OfflinePushInfo
    );


}
