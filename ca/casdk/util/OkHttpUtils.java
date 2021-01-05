package com.wjj.application.facade.ca.casdk.util;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * http 工具
 *
 * @author hank
 */
@Component
public class OkHttpUtils {
    private final static Logger logger = LoggerFactory.getLogger(OkHttpUtils.class);
    private static OkHttpClient mHttpClient;

    static {
        if (mHttpClient == null) {
            mHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectionPool(new ConnectionPool(50, 5L, TimeUnit.MINUTES))
                    .build();
        }

    }

    /**
     * 发起异步post请求
     *
     * @param url
     * @param params
     * @return
     */
    public static void postAsync(String url, Map<String, Object> params, Consumer<Response> success, Consumer<Exception> fail) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            params.forEach((k, v) -> {
                if (null != v) {
                    builder.add(k, String.valueOf(v));
                }
            });
        }

        RequestBody body = builder.build();
        Request req = new Request.Builder().url(url).post(body)
                .build();
        logger.info("postAsync request url:{}; params: {}", url, JSON.toJSON(params));
        mHttpClient.newCall(req).enqueue(new Callback() {
            //成功回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                logger.info("postAsync response url:{}; HTTP status message: {}", url, response.message());
                if (response.isSuccessful()) {
                    if (null != success) {
                        success.accept(response);
                    }
                } else {
                    if (null != fail) {
                        fail.accept(new Exception(response.message()));
                    }
                }
                response.body().close();
            }

            //请求错误回调方法
            @Override
            public void onFailure(Call call, IOException e) {
                if (null != fail) {
                    fail.accept(e);
                }
            }
        });
    }

    /**
     * 发起异步post请求
     *
     * @param url
     * @param params
     * @return
     */
    public static void postAsync(String url, Map<String, Object> params, Consumer<Response> success) {
        OkHttpUtils.postAsync(url, params, success, e -> logger.error("http post error:" + url, e));
    }

    /**
     * 发起同步post请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String, Object> params) throws Exception {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            params.forEach((k, v) -> {
                if (null != v) {
                    builder.add(k, String.valueOf(v));
                }
            });
        }

        RequestBody body = builder.build();
        Request req = new Request.Builder().url(url).post(body)
                .build();
        return executeRequest(req);
    }

    /**
     * Get请求
     *
     * @param url url
     * @param params 参数
     * @param isParamEncode 参数是否需要encode
     */
    public static String httpGet(String url, Map<String, ?> params, boolean isParamEncode) throws Exception {
        if(null != params && !params.isEmpty()){
            StringBuilder paramStr = new StringBuilder();
            int i = 0;
            for (Map.Entry<String, ?> item : params.entrySet()) {

                paramStr.append(item.getKey()).append("=");
                if(isParamEncode){
                    paramStr.append(URLEncoder.encode(item.getValue().toString(), "utf-8").toString());
                }else{
                    paramStr.append(item.getValue().toString());
                }
                i++;
                if(i!=params.size()){
                    paramStr.append("&");
                }
            }
            //没有?加?
            if(!url.contains("?")){
                url += "?" + paramStr;
            }
            //有? （?是最后或者&是最后）
            else if(url.indexOf("?") == url.length()-1 || url.indexOf("&") == url.length()-1){
                url += paramStr;
            }
            else {
                url += "&" + paramStr;
            }
        }

        Request request = new Request.Builder().url(url).build();
        return executeRequest(request);
    }

    /**
     * get 请求
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String httpGet(String url, Map<String, ?> params) throws Exception {
        return httpGet(url, params, true);
    }

    private static String executeRequest(Request request) throws Exception {
        Response response = null;
        try {
            response = mHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new Exception("response not Success response_code: " + response.code() + "response_msg: "+response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (null != response) {
                response.body().close();
            }
        }
    }
}
