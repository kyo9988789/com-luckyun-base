package com.luckyun.base.appversion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.appversion.mapper.SysAppMapper;
import com.luckyun.base.appversion.param.SysAppParam;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.model.appversion.SysApp;

@Service
public class SysAppService extends BaseService<SysApp>{

	@Autowired
	private SysAppMapper sysAppMapper;
	
	public List<SysApp> findAll(SysAppParam appParam){
		
		return sysAppMapper.findAll(appParam);
	}
	
	public SysApp findLastVersion(Integer lastVersion,String appnm,String ctype) {
		List<SysApp> greaterVersions = sysAppMapper
				.findLastVersion(lastVersion, appnm, ctype);
		if(greaterVersions != null && greaterVersions.size() >=1) {
			return greaterVersions.get(0);
		}
		return null;
	}
	
	@TransactionalException
	public void deleteApps(List<SysApp> sysApps) {
		for(SysApp sysApp : sysApps) {
			super.delete(sysApp);
		}
	}
	
	@Override
	public BaseMapper<SysApp> getMapper() {
		return sysAppMapper;
	}

	
}
