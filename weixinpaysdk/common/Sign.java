package com.wjj.application.util.weixinpaysdk.common;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.wjj.application.util.weixinpaysdk.util.CryptUtils;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 微信签名类
 * @author hank
 */
public class Sign {
    /**
     * 参数签名处理
     * stringSignTemp=stringA+"&key=192006250b4c09247ec02edce69f6a2d" //注：key为商户平台设置的密钥key
     * sign=MD5(stringSignTemp).toUpperCase()="9A0A8659F005D6984697E2CA0A9CF3B7" //注：MD5签名方式
     * sign=hash_hmac("sha256",stringSignTemp,key).toUpperCase()="6A9AE1657590FD6257D693A078E1C3E4BB6BA4DC30B23E0EE2496E54170DACD6" //注：HMAC-SHA256签名方式，，部分语言的hmac方法生成结果二进制结果，需要调对应函数转化为十六进制字符串。
     * @param params 业务参数
     * @throws TencentCloudSDKException
     */
    public static String paramSignHandle(TreeMap<String, String> params, String key) throws NoSuchAlgorithmException {

        String signStr = getSignStr(params, key);
        return CryptUtils.MD5Encode(signStr).toUpperCase();
    }

    private static String getSignStr(TreeMap<String, String> params, String key){
        StringBuilder signStr = new StringBuilder();
        for (Map.Entry<String, String> paramsEntry : params.entrySet()) {
            signStr.append(paramsEntry.getKey()).append("=").append(paramsEntry.getValue());
            signStr.append("&");
        }
        signStr.append("key").append("=").append(key);
        return signStr.toString();
    }
}
