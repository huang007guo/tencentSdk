package com.xiaochong.meet.lib.tencent.tencentvodsdk.vo.out;

import java.util.List;

public class VodFileBasicInfo extends TencentVodOut {

    private BasicInfo basicInfo;

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public static class BasicInfo {
        private String name;
        private long size;
        private long totalSize;
        private int duration;
        private String description;
        private String status;
        private long createTime;
        private long updateTime;
        private int expireTime;
        private int classificationId;
        private String classificationName;
        private long playerId;
        private String coverUrl;
        private String type;
        private String sourceVideoUrl;
        private List<String> tags;
        private String sourceInfo;
        private String sourceContext;
        private String region;
        private int storageMode;
        private int sourceType;
        private String classificationPath;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
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

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getDuration() {
            return duration;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setExpireTime(int expireTime) {
            this.expireTime = expireTime;
        }

        public int getExpireTime() {
            return expireTime;
        }

        public void setClassificationId(int classificationId) {
            this.classificationId = classificationId;
        }

        public int getClassificationId() {
            return classificationId;
        }

        public void setClassificationName(String classificationName) {
            this.classificationName = classificationName;
        }

        public String getClassificationName() {
            return classificationName;
        }

        public void setPlayerId(long playerId) {
            this.playerId = playerId;
        }

        public long getPlayerId() {
            return playerId;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setSourceVideoUrl(String sourceVideoUrl) {
            this.sourceVideoUrl = sourceVideoUrl;
        }

        public String getSourceVideoUrl() {
            return sourceVideoUrl;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setSourceInfo(String sourceInfo) {
            this.sourceInfo = sourceInfo;
        }

        public String getSourceInfo() {
            return sourceInfo;
        }

        public void setSourceContext(String sourceContext) {
            this.sourceContext = sourceContext;
        }

        public String getSourceContext() {
            return sourceContext;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getRegion() {
            return region;
        }

        public void setStorageMode(int storageMode) {
            this.storageMode = storageMode;
        }

        public int getStorageMode() {
            return storageMode;
        }

        public void setSourceType(int sourceType) {
            this.sourceType = sourceType;
        }

        public int getSourceType() {
            return sourceType;
        }

        public void setClassificationPath(String classificationPath) {
            this.classificationPath = classificationPath;
        }

        public String getClassificationPath() {
            return classificationPath;
        }
    }

}
