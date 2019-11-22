package com.xiaochong.meet.lib.tencentimsdk.vo.in;

/**
 * im消息内容 参考：
 * https://cloud.tencent.com/document/product/269/2720
 * @param <T>
 */
public class ImMsgBody <T>{
    //消息元素类别；目前支持的消息对象包括：TIMTextElem(文本消息)，TIMFaceElem(表情消息)，TIMLocationElem(位置消息)，TIMCustomElem(自定义消息)。
    private String MsgType;
    //	消息元素的内容，不同的MsgType有不同的MsgContent格式
    private T MsgContent;

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public T getMsgContent() {
        return MsgContent;
    }

    public void setMsgContent(T msgContent) {
        MsgContent = msgContent;
    }
}
