package com.xiaochong.meet.lib.tencent.common;

import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import java.util.Random;
import java.util.TreeMap;

public class Sign {
    /**
     * 参数签名处理
     * @param params 业务参数
     * @param secretKey
     * @param SecretId
     * @param Region 地域参数，用来标识希望操作哪个地域的数据。可选:
                        bj:北京
                        gz:广州
                        sh:上海
                        hk:香港
                        ca:北美
                        sg:新加坡
                        usw:美西
                        cd:成都
                        de:德国
                        kr:韩国
                        shjr:上海金融
                        szjr:深圳金融
                        gzopen:广州OPEN
     * @param SignatureMethod 签名方式(老版本没有这个参数 传null 会自动使用HmacSHA1)，目前支持 HmacSHA256 和 HmacSHA1 。只有指定此参数为 HmacSHA256 时，才使用 HmacSHA256 算法验证签名，其他情况均使用 HmacSHA1 验证签名。
     * @param Action 具体操作的指令接口名称，例如想要调用云服务器的查询实例列表接口，则 Action 参数即为 DescribeInstances 。
     * @param reqMethod 请求方法： 支持 POST 和 GET 方式，这里使用 GET 请求， 注意方法为全大写
     * @param host 请求主机域名 不带协议头，请求域名由接口所属的产品或所属产品的模块决定，不同产品或不同产品的模块的请求域名会有不同。如腾讯云 CVM 的查询实例列表（DescribeInstances）的请求域名为：cvm.api.qcloud.com，具体产品请求域名详见各接口说明。
     * @param path 请求路径(也可以直接放在host的后面，这里就为"")： 腾讯云 API 对应产品的请求路径，一般是一个产品对应一个固定路径，如腾讯云 CVM 请求路径固定为/v2/index.php
     * @throws TencentCloudSDKException
     */
    public static void paramSignHandle(TreeMap<String, String> params,
                                       String secretKey, String SecretId, String Region, String SignatureMethod, String Action,
                                        String reqMethod, String host, String path
                              ) throws TencentCloudSDKException {
        long nowTime = System.currentTimeMillis()/1000;
        int nonce = (new Random()).nextInt(Integer.MAX_VALUE);
        params.put("Action", Action);
        params.put("SecretId", SecretId);
        params.put("Region", Region);
        if(null != SignatureMethod) {
            params.put("SignatureMethod", SignatureMethod);
        }
        params.put("Timestamp", String.valueOf(nowTime));
        params.put("Nonce", String.valueOf(nonce));
        String Signature = com.tencentcloudapi.common.Sign.sign(secretKey, com.tencentcloudapi.common.Sign.makeSignPlainText(params, reqMethod, host, path), (SignatureMethod == null ? "HmacSHA1" : SignatureMethod) );
        params.put("Signature", Signature);
    }
}
