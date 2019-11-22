/**
 * Copyright 2018 bejson.com
 */
package com.xiaochong.meet.lib.tencent.tencentvodsdk.vo.in;

import java.util.List;

/**
 *
 * 腾讯点播api 鉴黄的回调数据，参考：
 * https://cloud.tencent.com/document/product/266/9636#porn.EF.BC.88.E9.89.B4.E9.BB.84.EF.BC.89
 */
public class TencentVodPornIn {

    private String vodTaskId;
    //任务状态，有PROCESSING，SUCCESS，FAIL三种
    private String status;
    //错误信息
    private String message;
    //错误码。 0: 成功, 其他值: 失败,其中30009为原文件异常失败；30010为系统失败或未知
    private int errCode;
    private String fileId;
    private MetaData metaData;
    //内容审核结果列表
    private List<ContentReviewList> contentReviewList;

    public static class AudioStreamList {

        private long bitrate;
        private String codec;
        private int samplingRate;
        public void setBitrate(long bitrate) {
            this.bitrate = bitrate;
        }
        public long getBitrate() {
            return bitrate;
        }

        public void setCodec(String codec) {
            this.codec = codec;
        }
        public String getCodec() {
            return codec;
        }

        public void setSamplingRate(int samplingRate) {
            this.samplingRate = samplingRate;
        }
        public int getSamplingRate() {
            return samplingRate;
        }

    }

    public static class VideoStreamList {

        private long bitrate;
        private String codec;
        private int fps;
        private int height;
        private int width;
        public void setBitrate(long bitrate) {
            this.bitrate = bitrate;
        }
        public long getBitrate() {
            return bitrate;
        }

        public void setCodec(String codec) {
            this.codec = codec;
        }
        public String getCodec() {
            return codec;
        }

        public void setFps(int fps) {
            this.fps = fps;
        }
        public int getFps() {
            return fps;
        }

        public void setHeight(int height) {
            this.height = height;
        }
        public int getHeight() {
            return height;
        }

        public void setWidth(int width) {
            this.width = width;
        }
        public int getWidth() {
            return width;
        }

    }

    public static class MetaData {

        private double audioDuration;
        private List<AudioStreamList> audioStreamList;
        private long bitrate;
        private String container;
        private int duration;
        private double floatDuration;
        private int height;
        private String md5;
        private int rotate;
        private long size;
        private long totalSize;
        private double videoDuration;
        private List<VideoStreamList> videoStreamList;
        private int width;
        public void setAudioDuration(double audioDuration) {
            this.audioDuration = audioDuration;
        }
        public double getAudioDuration() {
            return audioDuration;
        }

        public void setAudioStreamList(List<AudioStreamList> audioStreamList) {
            this.audioStreamList = audioStreamList;
        }
        public List<AudioStreamList> getAudioStreamList() {
            return audioStreamList;
        }

        public void setBitrate(long bitrate) {
            this.bitrate = bitrate;
        }
        public long getBitrate() {
            return bitrate;
        }

        public void setContainer(String container) {
            this.container = container;
        }
        public String getContainer() {
            return container;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
        public int getDuration() {
            return duration;
        }

        public void setFloatDuration(double floatDuration) {
            this.floatDuration = floatDuration;
        }
        public double getFloatDuration() {
            return floatDuration;
        }

        public void setHeight(int height) {
            this.height = height;
        }
        public int getHeight() {
            return height;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }
        public String getMd5() {
            return md5;
        }

        public void setRotate(int rotate) {
            this.rotate = rotate;
        }
        public int getRotate() {
            return rotate;
        }

        public void setSize(long size) {
            this.size = size;
        }
        public long getSize() {
            return size;
        }

        public void setTotalSize(long totalSize) {
            this.totalSize = totalSize;
        }
        public long getTotalSize() {
            return totalSize;
        }

        public void setVideoDuration(double videoDuration) {
            this.videoDuration = videoDuration;
        }
        public double getVideoDuration() {
            return videoDuration;
        }

        public void setVideoStreamList(List<VideoStreamList> videoStreamList) {
            this.videoStreamList = videoStreamList;
        }
        public List<VideoStreamList> getVideoStreamList() {
            return videoStreamList;
        }

        public void setWidth(int width) {
            this.width = width;
        }
        public int getWidth() {
            return width;
        }

    }

    public static class Input {
        //鉴黄模板ID
        private int definition;
        public void setDefinition(int definition) {
            this.definition = definition;
        }
        public int getDefinition() {
            return definition;
        }

    }

    public static class Output {

        //视频鉴黄评分，分值为0到100
        private int confidence;
        //鉴黄结果建议，有pass，review，block三种
        private String suggestion;
        //有涉黄嫌疑的视频片段
        private List<Segments> segments;
        public void setConfidence(int confidence) {
            this.confidence = confidence;
        }
        public int getConfidence() {
            return confidence;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }
        public String getSuggestion() {
            return suggestion;
        }

        public void setSegments(List<Segments> segments) {
            this.segments = segments;
        }
        public List<Segments> getSegments() {
            return segments;
        }

    }

    public static class ContentReviewList {

        //错误码。 0: 成功, 其他值: 失败,其中30009为原文件异常失败；30010为系统失败或未知
        private int errCode;
        //错误信息
        private String message;
        //任务类型，固定为Porn
        private String taskType;
        private String status;
        //任务的输入信息
        private Input input;
        //任务的输出信息，任务成功时会有该字段，失败则没有
        private Output output;
        public void setErrCode(int errCode) {
            this.errCode = errCode;
        }
        public int getErrCode() {
            return errCode;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }

        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }
        public String getTaskType() {
            return taskType;
        }

        public void setStatus(String status) {
            this.status = status;
        }
        public String getStatus() {
            return status;
        }

        public void setInput(Input input) {
            this.input = input;
        }
        public Input getInput() {
            return input;
        }

        public void setOutput(Output output) {
            this.output = output;
        }
        public Output getOutput() {
            return output;
        }

    }

    public static class Segments {

        //嫌疑片段起始的偏移时间，单位秒
        private int startTimeOffset;
        //嫌疑片段结束的偏移时间，单位秒
        private int endTimeOffset;
        //嫌疑片段涉黄分数
        private int confidence;
        //嫌疑片段鉴黄结果建议，有 pass，review，block 三种
        private String suggestion;
        //涉黄嫌疑图片 URL（图片不会永久存储，在一段时间后失效）
        private String url;
        //涉黄嫌疑图片 URL 失效时间（Unix 时间戳）
        private int picUrlExpireTimeStamp;
        public void setStartTimeOffset(int startTimeOffset) {
            this.startTimeOffset = startTimeOffset;
        }
        public int getStartTimeOffset() {
            return startTimeOffset;
        }

        public void setEndTimeOffset(int endTimeOffset) {
            this.endTimeOffset = endTimeOffset;
        }
        public int getEndTimeOffset() {
            return endTimeOffset;
        }

        public void setConfidence(int confidence) {
            this.confidence = confidence;
        }
        public int getConfidence() {
            return confidence;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }
        public String getSuggestion() {
            return suggestion;
        }

        public void setUrl(String url) {
            this.url = url;
        }
        public String getUrl() {
            return url;
        }

        public void setPicUrlExpireTimeStamp(int picUrlExpireTimeStamp) {
            this.picUrlExpireTimeStamp = picUrlExpireTimeStamp;
        }
        public int getPicUrlExpireTimeStamp() {
            return picUrlExpireTimeStamp;
        }

    }


    public void setVodTaskId(String vodTaskId) {
        this.vodTaskId = vodTaskId;
    }
    public String getVodTaskId() {
        return vodTaskId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
    public int getErrCode() {
        return errCode;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    public String getFileId() {
        return fileId;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }
    public MetaData getMetaData() {
        return metaData;
    }

    public void setContentReviewList(List<ContentReviewList> contentReviewList) {
        this.contentReviewList = contentReviewList;
    }
    public List<ContentReviewList> getContentReviewList() {
        return contentReviewList;
    }

}