package com.luckyun.base.provider.feign.intercepter;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class BaseFeignInterceptor implements RequestInterceptor{
	
	@Override
	public void apply(RequestTemplate template) {
		template.header("rpc", "true");
	}
}