package com.luckyun.base.preset.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.luckyun.base.preset.mapper.SysUserPresetMapper;
import com.luckyun.base.preset.param.SysUserPresetParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.exception.CustomException;
import com.luckyun.model.preset.SysUserPreset;

@Service
public class SysUserPresetService extends BaseService<SysUserPreset>{

	@Autowired
	private SysUserPresetMapper sysUserPresetMapper; 
	
	public SysUserPreset findOneBySpath(String spath) {
		SysUserPresetParam condition = new SysUserPresetParam();
		condition.setSpath(spath);
		List<SysUserPreset> preSets = findAllPreset(condition);
		//根据用户编号和地址可以获取唯一的一条配置数据
		if(preSets != null && preSets.size() >= 1) {
			return preSets.get(0);
		}
		return null;
	}
	
	public void add(SysUserPreset preset) {
		AuthInfo authInfo = getAuthInfo();
		if(StringUtils.isEmpty(preset.getSpath())) {
			throw new CustomException("base.preset.spath.isnotnull");
		}
		SysUserPresetParam condition = new SysUserPresetParam();
		condition.setSpath(preset.getSpath());
		List<SysUserPreset> preSets = findAllPreset(condition);
		if(preSets != null && preSets.size() >= 1) {
			updatePreset(preset);
		}else {
			preset.setIuserid(authInfo.getIndocno());
			super.insert(preset);
		}
	}
	
	public void updatePreset(SysUserPreset preset) {
		if(StringUtils.isEmpty(preset.getSpath())) {
			throw new CustomException("base.preset.spath.isnotnull");
		}
		SysUserPresetParam condition = new SysUserPresetParam();
		condition.setSpath(preset.getSpath());
		List<SysUserPreset> preSets = findAllPreset(condition);
		if(preSets != null && preSets.size() == 1) {
			preset.setIndocno(preSets.get(0).getIndocno());
		}else {
			throw new CustomException("base.preset.data.error");
		}
		super.update(preset);
	}
	
	private List<SysUserPreset> findAllPreset(SysUserPresetParam condition ){
		AuthInfo authInfo = getAuthInfo();
		condition.setIuserid(authInfo.getIndocno());
		return sysUserPresetMapper.findAll(condition);
	}
	
	@Override
	public BaseMapper<SysUserPreset> getMapper() {
		return sysUserPresetMapper;
	}

}
