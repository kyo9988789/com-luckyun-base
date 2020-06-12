package com.luckyun.base.other.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.other.mapper.SysUserVisitMapper;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.model.module.SysModule;
import com.luckyun.model.user.SysUserVisit;

@Service
public class SysUserVisitService extends BaseService<SysUserVisit>{

	@Autowired
	private SysUserVisitMapper sysUserVisitMapper;
	
	public List<SysModule> findModuleByUserId(){
		AuthInfo authInfo = getAuthInfo();
		return findSysModuleByUserId(authInfo.getIndocno());
	}
	
	private List<SysModule> findSysModuleByUserId(Long iuserId){
		return sysUserVisitMapper.findOftenModule(iuserId);
	}
	
	@Override
	public BaseMapper<SysUserVisit> getMapper() {
		return sysUserVisitMapper;
	}

}
