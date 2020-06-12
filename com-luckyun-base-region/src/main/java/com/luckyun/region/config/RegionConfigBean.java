package com.luckyun.region.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class RegionConfigBean {

	@Value("${spring.datasource.driver-class-name}")
	public String driverclass;
}
