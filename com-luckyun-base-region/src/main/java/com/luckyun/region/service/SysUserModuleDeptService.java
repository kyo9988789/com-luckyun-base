package com.luckyun.region.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.exception.CustomException;
import com.luckyun.core.tool.CollecterMixUtils;
import com.luckyun.model.user.SysAccount;
import com.luckyun.region.mapper.SysUserModuleDeptMapper;
import com.luckyun.region.model.SysUserModuleDept;
import com.luckyun.region.param.SysUserModuleDeptParam;

@Service
public class SysUserModuleDeptService extends BaseService<SysUserModuleDept>{

	@Autowired
	private SysUserModuleDeptMapper sysUserModuleDeptMapper; 
	
	@Autowired
	private SysRegionHelperService sysRegionHelperService;
	
	public List<SysUserModuleDept> readAll(SysUserModuleDeptParam condition){
		Long iuserid = 0L;
		if(condition.getIuserid() != null) {
			iuserid = condition.getIuserid();
		}else {
			iuserid = getAuthInfo().getIndocno();
		}
		if(condition.getImodid() == null) {
			throw new CustomException("模板编号不能为空");
		}
		return sysUserModuleDeptMapper.findAll(iuserid, condition.getImodid());
	}
	
	public void handleModuleDept(SysUserModuleDeptParam condition) {
		AuthInfo authInfo = getAuthInfo();
		if(condition.getRegions() != null && condition.getRegions().size()>=1) {
			for(SysUserModuleDept moduleDept : condition.getRegions()) {
				moduleDept.setImodid(condition.getImodid());
			}
		}
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				setUserModuleDeptRegion(condition.getRegions(),condition.getNeedCloneMembers(),authInfo,
						condition.getImodid());
			}
		});
		thread.start();
	}
	
	private void setUserModuleDeptRegion(List<SysUserModuleDept> nRegion, List<SysAccount> members,
			AuthInfo authInfo,Long imodid) {
		if(nRegion != null && nRegion.size() >= 1 && members != null && members.size() >= 1) {
			for(SysAccount account : members) {
				List<SysUserModuleDept> oldRegion = 
						sysUserModuleDeptMapper.findAll(account.getIndocno(),imodid);
				handleModuleDeptRegion(account,nRegion,oldRegion,authInfo);
			}
		}
	}
	private void handleModuleDeptRegion(SysAccount account,List<SysUserModuleDept> nRegion
			, List<SysUserModuleDept> oldRegion,AuthInfo authInfo) {
		List<SysUserModuleDept> mixRegion = CollecterMixUtils.tMix(nRegion, oldRegion, "ideptid");
		List<SysUserModuleDept> addRegion = CollecterMixUtils.fdiffSet(nRegion, mixRegion, "ideptid");
		List<SysUserModuleDept> delRegion = CollecterMixUtils.fdiffSet(oldRegion, mixRegion, "ideptid");
		sysRegionHelperService.handleModuleDeptForUser(account, addRegion, delRegion, authInfo);
	}
	
	@Override
	public BaseMapper<SysUserModuleDept> getMapper() {
		return sysUserModuleDeptMapper;
	}

}
