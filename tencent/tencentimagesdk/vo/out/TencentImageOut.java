package com.xiaochong.meet.lib.tencent.tencentimagesdk.vo.out;

public class TencentImageOut {
    /**
     * 错误码，0 为成功
     * 参考枚举 ImageResCode
     */
    private int code;

    //服务器返回的信息
    private String message;

    //当前图片的 url
    private String url;

    //具体查询数据
    private Data data;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }

    /**
     * 返回内容
     * 参考：
     *  https://cloud.tencent.com/document/product/864/17609#.E8.BF.94.E5.9B.9E.E5.86.85.E5.AE.B9
     */
    public static class Data {
        /**
         * 供参考的识别结果：0 正常，1 黄图，2 疑似图片
         * 注意：
         1. 当 result=0 时，表明图片为正常图片；
         2. 当 result=1 时，表明该图片是系统判定为违禁涉黄的图片，如果存储在腾讯云则会直接被封禁掉；
         3. 当 result=2 时，表明该图片是疑似图片(83 ≤ confidence < 91)，即为黄图的可能性很大。
         */

        private int result;

        //封禁状态，0 表示正常，1 表示图片已被封禁（只有存储在腾讯云的图片才会被封禁）
        private int forbid_status;

        //识别为黄图的置信度，分值 0-100；是 normal_score , hot_score , porn_score的综合评分
        private double confidence;

        //图片为性感图片的评分
        private double hot_score;

        //图片为正常图片的评分
        private double normal_score;

        //图片为色情图片的评分
        private double porn_score;

        public void setResult(int result){
            this.result = result;
        }
        public int getResult(){
            return this.result;
        }
        public void setForbid_status(int forbid_status){
            this.forbid_status = forbid_status;
        }
        public int getForbid_status(){
            return this.forbid_status;
        }
        public void setConfidence(double confidence){
            this.confidence = confidence;
        }
        public double getConfidence(){
            return this.confidence;
        }
        public void setHot_score(double hot_score){
            this.hot_score = hot_score;
        }
        public double getHot_score(){
            return this.hot_score;
        }
        public void setNormal_score(double normal_score){
            this.normal_score = normal_score;
        }
        public double getNormal_score(){
            return this.normal_score;
        }
        public void setPorn_score(double porn_score){
            this.porn_score = porn_score;
        }
        public double getPorn_score(){
            return this.porn_score;
        }

    }
}
