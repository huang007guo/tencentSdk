package com.xiaochong.meet.lib.tencentimsdk.vo.out;

/**
 * 查询全局禁言
 */
public class NoSpeakingInfoOut extends TencentImOut {
    /**
     * 单聊消息禁言时长，秒为单位，非负整数。等于 0 代表没有被设置禁言；等于最大值 4294967295(十六进制 0xFFFFFFFF)代表被永久设置禁言；其它代表该账户禁言时长，如果等于 3600 表示账户被禁言一小时。
     */
    private Long C2CmsgNospeakingTime;

    /**
     * 群组消息禁言时长，秒为单位，非负整数。等于 0 代表没有被设置禁言；等于最大值 4294967295(十六进制 0xFFFFFFFF)代表被永久设置禁言；其它代表该账户禁言时长，如果等于 3600 表示账户被禁言一小时。
     */
    public Long GroupmsgNospeakingTime;

    public Long getC2CmsgNospeakingTime() {
        return C2CmsgNospeakingTime;
    }

    public void setC2CmsgNospeakingTime(Long c2CmsgNospeakingTime) {
        C2CmsgNospeakingTime = c2CmsgNospeakingTime;
    }

    public Long getGroupmsgNospeakingTime() {
        return GroupmsgNospeakingTime;
    }

    public void setGroupmsgNospeakingTime(Long groupmsgNospeakingTime) {
        GroupmsgNospeakingTime = groupmsgNospeakingTime;
    }
}
