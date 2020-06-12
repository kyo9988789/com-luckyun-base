package com.luckyun.base.appversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.appversion.mapper.SysAppVersionMapper;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.model.appversion.SysAppVersion;

@Service
public class SysAppVersionService extends BaseService<SysAppVersion>{

	@Autowired
	private SysAppVersionMapper sysAppVersionMapper;
	
	@Override
	public BaseMapper<SysAppVersion> getMapper() {
		return sysAppVersionMapper;
	}

}
