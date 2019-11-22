package com.xiaochong.meet.lib.tencent.tencentlivesdk.vo.in;
import java.util.List;

/**
 * 直播鉴黄回调数据
 * https://cloud.tencent.com/document/product/267/17933#.E7.A4.BA.E4.BE.8B
 */
public class TencentLivePornIn {

    //图片的 OCR 识别信息（如果有）
    private String ocrMsg;
    //图片房间类型
    //1 : 色情图片
    //2 : 性感图片
    //3 : OCR 识别恶意
    //4 : 敏感内容
    //5 : 政治人物
    //6 : 暴恐
    //7 : 违法
    //8 : 血腥
    //9 : 其他
    private List<Integer> type;
    //识别为黄图的置信度，范围 0-100；
    //normalScore, hotScore, pornScore 的综合评分，
    //confidence 大于 83 定为疑似图片
    private int confidence;
    //图片为正常图片的评分
    private int normalScore;
    //图片为性感图片的评分
    private int hotScore;
    //图片为色情图片的评分
    private int pornScore;
    //截图时间
    private int screenshotTime;
    //图片的级别
    private int level;
    //预警图片链接
    private String img;
    //一个包含 AbductionRisk 结构的数组
    private List<String> abductionRisk;
    //一个包含人脸属性 faceDetail 的结构的数组
    private List<String> faceDetails;
    //请求发送时间，Unix 时间戳
    private long sendTime;
    //预警策略 ID，视频内容预警：20001
    private int tid;
    //流 ID
    private String streamId;
    //频道 ID
    private String channelId;
    public void setOcrMsg(String ocrMsg) {
        this.ocrMsg = ocrMsg;
    }
    public String getOcrMsg() {
        return ocrMsg;
    }

    public void setType(List<Integer> type) {
        this.type = type;
    }
    public List<Integer> getType() {
        return type;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }
    public int getConfidence() {
        return confidence;
    }

    public void setNormalScore(int normalScore) {
        this.normalScore = normalScore;
    }
    public int getNormalScore() {
        return normalScore;
    }

    public void setHotScore(int hotScore) {
        this.hotScore = hotScore;
    }
    public int getHotScore() {
        return hotScore;
    }

    public void setPornScore(int pornScore) {
        this.pornScore = pornScore;
    }
    public int getPornScore() {
        return pornScore;
    }

    public void setScreenshotTime(int screenshotTime) {
        this.screenshotTime = screenshotTime;
    }
    public int getScreenshotTime() {
        return screenshotTime;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getImg() {
        return img;
    }

    public void setAbductionRisk(List<String> abductionRisk) {
        this.abductionRisk = abductionRisk;
    }
    public List<String> getAbductionRisk() {
        return abductionRisk;
    }

    public void setFaceDetails(List<String> faceDetails) {
        this.faceDetails = faceDetails;
    }
    public List<String> getFaceDetails() {
        return faceDetails;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
    public long getSendTime() {
        return sendTime;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }
    public int getTid() {
        return tid;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }
    public String getStreamId() {
        return streamId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    public String getChannelId() {
        return channelId;
    }

}