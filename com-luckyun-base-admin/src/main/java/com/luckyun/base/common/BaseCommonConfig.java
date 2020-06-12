package com.luckyun.base.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "basecommon")
@Data
public class BaseCommonConfig {

	public static String preOssUrl;
	
	@Value("${basecommon.pre-oss-url}")
	public void setPreOssUrl(String preOssUrl) {
		BaseCommonConfig.preOssUrl = preOssUrl;
	}
}
