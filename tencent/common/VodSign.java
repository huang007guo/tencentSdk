package com.xiaochong.meet.lib.tencent.common;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;

/**
 * vod签名参考：
 * https://cloud.tencent.com/document/product/266/9221#.E7.AD.BE.E5.90.8D.E5.8F.82.E6.95.B0
 */
public class VodSign {
    private String secretId;
    private String secretKey;
    private long currentTime;
    private int random;
    //签名有效时长秒 最大 90 天。
    private int signValidDuration;
    //签名是否单次有效，参见 客户端上传指引-单次有效签名，默认为 0 表示不启用；1 表示签名单次有效，相关错误码详见下文的单次有效签名相关部分。
    private int oneTimeValid = 0;
    //视频后续任务操作，详见 任务流综述
    private String procedure;

    //任务流状态变更通知模式（仅当指定了 procedure 时才有效）。
    //Finish：只有当任务流全部执行完毕时，才发起一次事件通知；
    //Change：只要任务流中每个子任务的状态发生变化，都进行事件通知；
    //None：不接受该任务流回调。
    //默认为Finish。
    private String taskNotifyMode;


    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private static final String CONTENT_CHARSET = "UTF-8";

    public static byte[] byteMerger(byte[] byte1, byte[] byte2) {
        byte[] byte3 = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, byte3, 0, byte1.length);
        System.arraycopy(byte2, 0, byte3, byte1.length, byte2.length);
        return byte3;
    }


    public String getUploadSignature() throws Exception {
        String strSign = "";
        String contextStr = "";

        long endTime = (currentTime + signValidDuration);
        contextStr += "secretId=" + java.net.URLEncoder.encode(secretId, "utf8");
        contextStr += "&currentTimeStamp=" + currentTime;
        contextStr += "&expireTime=" + endTime;
        if(StringUtils.isNotBlank(procedure))
            contextStr += "&procedure=" + java.net.URLEncoder.encode(procedure, "utf8");
        contextStr += "&random=" + random;
        if(StringUtils.isNotBlank(taskNotifyMode))
            contextStr += "&taskNotifyMode=" + taskNotifyMode;

        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(this.secretKey.getBytes(CONTENT_CHARSET), mac.getAlgorithm());
            mac.init(secretKey);

            byte[] hash = mac.doFinal(contextStr.getBytes(CONTENT_CHARSET));
            byte[] sigBuf = byteMerger(hash, contextStr.getBytes("utf8"));
            strSign = new String(new BASE64Encoder().encode(sigBuf).getBytes());
            strSign = strSign.replace(" ", "").replace("\n", "").replace("\r", "");
        } catch (Exception e) {
            throw e;
        }
        return strSign;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public void setTaskNotifyMode(String taskNotifyMode) {
        this.taskNotifyMode = taskNotifyMode;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setRandom(int random) {
        this.random = random;
    }

    public void setSignValidDuration(int signValidDuration) {
        this.signValidDuration = signValidDuration;
    }

    public void setOneTimeValid(int oneTimeValid) {
        this.oneTimeValid = oneTimeValid;
    }
}
