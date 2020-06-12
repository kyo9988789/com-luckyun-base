package com.luckyun.region.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.provider.feign.BaseSysDeptProvider;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.tool.CollecterMixUtils;
import com.luckyun.model.dept.SysDept;
import com.luckyun.model.user.SysAccount;
import com.luckyun.region.mapper.SysUserDeptMapper;
import com.luckyun.region.model.SysUserDept;
import com.luckyun.region.param.SysUserDeptParam;

@Service
public class SysUserDeptService extends BaseService<SysUserDept>{
	
	@Autowired
	private SysUserDeptMapper sysUserDeptMapper;
	
	@Autowired
	BaseSysDeptProvider baseSysDeptProvider;
	
	@Autowired
	SysRegionHelperService sysRegionHelperService;
	
	public List<SysUserDept> readAll(){
		AuthInfo authInfo = getAuthInfo();
		return sysUserDeptMapper.findAll(authInfo.getIndocno());
	}
	
	public List<SysUserDept> readAllByOne(Long iuserid){
		return sysUserDeptMapper.findAll(iuserid);
	}
	
	public void cloneMembers(SysUserDeptParam sysUserDeptParam) {
		List<SysUserDept> needCloneData = sysUserDeptMapper.findAll(sysUserDeptParam.getIuserid());
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				cloneData(sysUserDeptParam.getNeedCloneMembers(),needCloneData);
			}
		});
		thread.start();
	}
	
	private void cloneData(List<SysAccount> userMembers,List<SysUserDept> cloneData) {
		for(SysAccount account : userMembers) {
			sysRegionHelperService.addRegionForUser(account, cloneData);
		}
	}
	
	public void updateDeptRegion(SysUserDeptParam sysUserDeptParam) {
		AuthInfo authInfo = getAuthInfo();
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				List<SysUserDept> newRegion = sysUserDeptParam.getRegionDepts();
				for(SysAccount account : sysUserDeptParam.getNeedCloneMembers()) {
					setUserRegion(newRegion, account,authInfo);
				}
			}
		});
		thread.start();
 	}
	
	private void setUserRegion(List<SysUserDept> newRegion, SysAccount account,AuthInfo authInfo) {
		List<SysUserDept> oldRegion = sysUserDeptMapper.findAll(account.getIndocno());
		List<SysUserDept> mixDept = CollecterMixUtils.tMix(newRegion, oldRegion, "ideptid");
		List<SysUserDept> addRegion = CollecterMixUtils.fdiffSet(newRegion, mixDept, "ideptid");
		List<SysUserDept> delRegion = CollecterMixUtils.fdiffSet(oldRegion, mixDept, "ideptid");
		sysRegionHelperService.handleDeptRegionForUser(account
				, addRegion, delRegion, authInfo);
	}
	
	protected List<SysUserDept> readAllRegionDept(List<SysUserDept> region){
		List<SysDept> result = new ArrayList<SysDept>();
		for(SysUserDept dept : region) {
			List<SysDept> depts = baseSysDeptProvider.findDeptByParenId(dept.getIdeptid());
			result.addAll(depts);
		}
		if(result.size() >= 1) {
			Map<Long, SysDept> uniqueDept = 
					result.stream().collect(Collectors.toMap(SysDept::getIndocno, Function.identity(),(oldValue, newValue) -> oldValue));
			List<SysDept> lastResult = new ArrayList<SysDept>(uniqueDept.size());
			for(Map.Entry<Long, SysDept> entry : uniqueDept.entrySet()) {
				lastResult.add(entry.getValue());
			}
			return lastResult.stream().map(e -> new SysUserDept(e.getIndocno(),e.getSname())).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public BaseMapper<SysUserDept> getMapper() {
		return sysUserDeptMapper;
	}

}
