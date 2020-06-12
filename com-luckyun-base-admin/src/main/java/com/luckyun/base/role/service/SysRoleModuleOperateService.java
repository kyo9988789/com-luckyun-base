package com.luckyun.base.role.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.luckyun.base.module.mapper.SysModuleMapper;
import com.luckyun.base.module.mapper.SysModuleOperateMapper;
import com.luckyun.base.module.param.SysModuleParam;
import com.luckyun.base.role.mapper.SysRoleOperateMapper;
import com.luckyun.base.role.param.SysRoleModuleOperate;
import com.luckyun.base.role.param.SysRoleParam;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.exception.CustomException;
import com.luckyun.core.tool.CollecterMixUtils;
import com.luckyun.model.data.BaseEntity;
import com.luckyun.model.module.SysModule;
import com.luckyun.model.module.SysModuleOperate;
import com.luckyun.model.role.SysRoleOperate;

import cn.hutool.json.JSONObject;

@Service
public class SysRoleModuleOperateService extends BaseService<BaseEntity>{

	@Autowired
	private SysRoleOperateMapper sysRoleOperateMapper;
	
	@Autowired
	private SysModuleMapper sysModuleMapper;
	
	@Autowired
	private SysModuleOperateMapper sysModuleOperateMapper;
	
	public List<SysModule> findModuleOperates(){
		List<SysRoleModuleOperate> moduleOperates = sysRoleOperateMapper.findModuleOperateByModule();
		List<Long> moduleIds = new ArrayList<>();
		Map<Long,List<SysModuleOperate>> content = convertMap(moduleOperates,moduleIds);
		SysModuleParam condition = new SysModuleParam();
		condition.setModuleIds(moduleIds);
		//查找所有符合条件的模块数据
		List<SysModule> sysModules = sysModuleMapper.findAllNoOtherCondition(condition);
		for(SysModule module : sysModules) {
			List<SysModuleOperate> modulSysModuleOperates = content.get(module.getIndocno());
			if(modulSysModuleOperates != null && modulSysModuleOperates.size()>=1) {
				module.setCustomOperates(modulSysModuleOperates);
			}
		}
		return sysModules;
	}
	public List<SysModuleOperate> findMopByRoleId(Long iroleid){
		return sysRoleOperateMapper.findMopByRole(iroleid);
	}
	
	@TransactionalException
	public void updateRoleModule(SysRoleParam condition) {
		List<SysModuleOperate> oldMop = sysRoleOperateMapper.findMopByRole(condition.getIndocno());
		List<SysModuleOperate> newMop = condition.getCustomOperates();
		List<SysModuleOperate> mopMix = CollecterMixUtils.tMix(newMop, oldMop, "indocno");
		List<SysModuleOperate> addMop = CollecterMixUtils.fdiffSet(newMop, mopMix, "indocno");
		List<SysModuleOperate> delMop = CollecterMixUtils.fdiffSet(oldMop, mopMix, "indocno");
		for(SysModuleOperate moduleOperate : addMop) {
			SysRoleOperate operate = new SysRoleOperate();
			operate.setIroleid(condition.getIndocno());
			//存储的是moduleoperate关系表的主键
			operate.setIoperateid(moduleOperate.getIndocno());
			sysRoleOperateMapper.insert(operate);
		}
		JSONObject userJson = new JSONObject();
		AuthInfo authInfo = this.getAuthInfo();
		if (authInfo != null) {
			userJson.put("indocno", authInfo.getIndocno());
			userJson.put("sname", authInfo.getSname());
		}
		List<String> addSccs = getOperateScc(addMop);
		if(addSccs.size()>0) {
			this.saveOptLog("DBOPT", "INSERT", condition.getIndocno(), JSON.toJSONString(addSccs), JSON.toJSONString(userJson));
		}
		for(SysModuleOperate moduleOperate : delMop) {
			if(moduleOperate.getIroleoperateid() != null) {
				SysRoleOperate operate = new SysRoleOperate();
				operate.setIndocno(moduleOperate.getIroleoperateid());
				sysRoleOperateMapper.delete(operate);
			}
		}
		List<String> delSccs = getOperateScc(delMop);
		if(delSccs.size()>0) {
			this.saveOptLog("DBOPT", "DELETE", condition.getIndocno(), JSON.toJSONString(delSccs), JSON.toJSONString(userJson));
		}
 	}
	
	/**
	 * 根据权限数组返回操作scc
	 * @param mops
	 * @return
	 */
	private List<String> getOperateScc(List<SysModuleOperate> mops){
		List<Long> indocnos = new ArrayList<Long>();
		for(SysModuleOperate moduleOperate:mops) {
			indocnos.add(moduleOperate.getIndocno());
		}
		List<SysModuleOperate> moduleOperates = sysModuleOperateMapper.findByImoduleOperateIds(indocnos);
		List<String> sccs = new ArrayList<String>();
		for(SysModuleOperate operate:moduleOperates) {
			if(StringUtils.isEmpty(operate.getSname())) {
				sccs.add(operate.getSmodulename()+"-"+operate.getSoperatename());
			}else {
				sccs.add(operate.getSmodulename()+"-"+operate.getSname());
			}
		}
		return sccs;
	}
	
	private Map<Long,List<SysModuleOperate>> convertMap(List<SysRoleModuleOperate> moduleOperates,
			List<Long> moduleLongs){
		Map<Long,List<SysModuleOperate>> result = new HashMap<Long, List<SysModuleOperate>>();
		Set<Long> setList = new HashSet<>();
		moduleOperates.forEach(e -> {
			if(result.containsKey(e.getMindocno())) {
				result.get(e.getMindocno()).add(getNsmop(e));
			}else {
				List<SysModuleOperate> mapVal = new ArrayList<>();
				mapVal.add(getNsmop(e));
				result.put(e.getMindocno(), mapVal);
			}
			getModuleIds(e.getSidcc(),setList);
		});
		moduleLongs.addAll(setList);
		return result;
	}
	
	private void getModuleIds(String sidcc,Set<Long> result){
		if(sidcc != null) {
			String[] arrs = sidcc.split("/");
			for(String sid : arrs) {
				if(!StringUtils.isEmpty(sid)) {
					result.add(Long.valueOf(sid));
				}
			}
		}else {
			throw new CustomException("base.module.sidcc.error");
		}
	}
	
	private SysModuleOperate getNsmop(SysRoleModuleOperate e) {
		SysModuleOperate moduleOperate = new SysModuleOperate();
		//自定义操作
		if(e.getMopsname() != null) {
			moduleOperate.setImoduleid(e.getMindocno());
			moduleOperate.setIndocno(e.getMopindocno());
			moduleOperate.setSname(e.getMopsname());
		}else {
			moduleOperate.setImoduleid(e.getMindocno());
			moduleOperate.setIndocno(e.getMopindocno());
			moduleOperate.setSname(e.getOpsname());
		}
		return moduleOperate;
	}
	
	@Override
	public BaseMapper<BaseEntity> getMapper() {
		return null;
	}
}
