package com.wjj.application.facade.kangmei;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 康美配置
 * @author hank
 */
@Component
@PropertySource(value = {"classpath:config/kangmei.yml", "classpath:config/kangmei-${spring.profiles.active:${env:prod}}.yml"}, ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "kangmei")
@Data
@Lazy
public class KangMeiConfig{

	/**
	 * 最大重试次数
	 */
	@Value("${tryCount:3}")
	private Integer tryCount;

	/**
	 * 用户名
	 */
	@Value("${companyNum}")
	private String companyNum;
	/**
	 * 密码
	 */
	@Value("${password}")
	private String password;

	/**
	 * 地址
	 */
	@Value("${wsdlUrl}")
	private String wsdlUrl;
	/**
	 * 康美物流url
	 */
	@Value("${logisticsUrl}")
	private String logisticsUrl;


	/**
	 * 康美接口调用失败收件人邮箱
	 */
	@Value("${emails}")
	private String emails;
}
