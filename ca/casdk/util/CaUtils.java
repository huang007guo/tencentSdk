package com.wjj.application.facade.ca.casdk.util;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.wjj.application.facade.ca.casdk.common.code.ResCode;
import com.wjj.application.facade.ca.casdk.common.CaConst;
import com.wjj.application.facade.ca.casdk.config.CaConfig;
import com.wjj.application.facade.ca.casdk.exception.CaTokenException;
import com.wjj.application.facade.ca.casdk.vo.out.TokenData;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * ca工具类
 * @author hank
 * @since 5.1.0
 */
@Component
public class CaUtils{

	private final static Logger logger = LoggerFactory.getLogger(CaUtils.class);

	@Autowired
	private CaConfig caConfig;


	/**
	 * 校验签名
	 * @param body
	 * @param sign
	 * @return
	 */
	public boolean checkSign(Object body, String sign) {
		if(body == null || StringUtils.isBlank(sign)){
			return false;
		}
		Map<String, Object> bodyMap = null;
		try {
			bodyMap = BeanUtil.beanToMap(body, false ,true);
			String trueSign=buildThirdSign(bodyMap, caConfig.getAppSecret());
			return sign.equals(trueSign);
		} catch (NoSuchAlgorithmException e) {
			logger.error("ca 验签失败", e);
			return false;
		}
	}

	public String getSignQRCodeUrl(String redirectUrl, String openId, String uniqueId){
		return caConfig.getBaseUrl() + CaConst.URL_RECIPE_GET_SIGN_QR_CODE + "?" +
				"openId=" + openId + "&" +
				"uniqueId=" + uniqueId + "&" +
				"clientId=" + caConfig.getClientId() + "&" +
				"redirectUrl=" + redirectUrl + "&" +
				"accessToken=" + CaUtils.getAccessToken(caConfig);
	}

	public static String getAccessToken(CaConfig caConfig) throws CaTokenException {
		return getAccessToken(caConfig, false);
	}
	/**
	 * 获取AccessToken
	 * @param isForce 是否强制获取,不走缓存
	 * @return AccessToken
	 */
	public static String getAccessToken(CaConfig caConfig, boolean isForce) throws CaTokenException {
		StringRedisTemplate stringRedisTemplate = null;
		try {
			stringRedisTemplate = SpringUtils.getBean(StringRedisTemplate.class);
		} catch (Exception e) {
			logger.warn("getBean StringRedisTemplate.class error.", e);
		}
		String accessToken = null;
		if(stringRedisTemplate != null) {
			accessToken = stringRedisTemplate.boundValueOps(caConfig.getAccessTokenCacheKey()).get();
		}
		if(StringUtils.isBlank(accessToken) || isForce){
			// 获取accessToken的实际代码
			HashMap<String, String> param = new HashMap<>(2);
			param.put("clientId", caConfig.getClientId());
			param.put("appSecret", caConfig.getAppSecret());
			String res = null;
			try {
				logger.debug("ca getToken req:{}", param);
				res = OkHttpUtils.httpGet(caConfig.getBaseUrl() + CaConst.URL_GET_ACCESS_TOKEN, param);
				logger.debug("ca getToken res:{}", res);
			} catch (Exception e) {
				logger.error("ca 获取accessToken失败,抛出异常", e);
				throw new CaTokenException("获取accessToken失败,抛出异常", e);
			}
			TokenData tokenData = JSON.parseObject(res, TokenData.class);
			// 结果不正确
			if(!ResCode.SUCCESS.getStatus().equals(tokenData.getStatus()) || tokenData.getData() == null || StringUtils.isBlank(tokenData.getData().getAccessToken())) {
				logger.error("获取accessToken失败,返回结果非成功状态 res:{}", res);
				throw new CaTokenException("获取accessToken失败, 返回结果非成功状态", param, res);
			}
			accessToken = tokenData.getData().getAccessToken();

			if(stringRedisTemplate != null && tokenData.getData().getExpiresIn() != null) {
				// 设置到缓存里, 过期时间预留20秒
				stringRedisTemplate.opsForValue().set(caConfig.getAccessTokenCacheKey(), accessToken, Integer.parseInt(tokenData.getData().getExpiresIn()) - 20L, TimeUnit.SECONDS);
			}
		}
		return accessToken;
	}

	private String buildThirdSign(Map<String, Object> mapBody, String clientSecret) throws NoSuchAlgorithmException {
		StringBuilder paramStr = new StringBuilder();
		// 字母排序,使用key=value,并用&分割的形式拼接字典排序字符串
		if (mapBody != null && mapBody.size() > 0) {
			List<String> paramsKeyList = new ArrayList<String>(mapBody.keySet());
			Collections.sort(paramsKeyList);
			for (String key : paramsKeyList) {
				if(mapBody.get(key) == null){
					continue;
				}
				String value = String.valueOf(mapBody.get(key));
				if (value != null && !"".equals(value)) {
					paramStr.append("&").append(key);
					paramStr.append("=").append(value);
				}
			}
		}
		// 去掉最左侧第一个&符号，并用#把clientSecret与字典排序字符串进行拼接
		String signSourceStr = paramStr.substring(1) + "#" + clientSecret;
		// 计算字符串md5 16进制字符串，此值就是回调数据中的sign
		return CryptUtils.MD5Encode(signSourceStr);
	}
}




