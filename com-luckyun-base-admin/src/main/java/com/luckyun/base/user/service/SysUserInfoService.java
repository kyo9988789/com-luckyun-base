package com.luckyun.base.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.user.mapper.SysUserInfoMapper;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.model.user.SysUser;

@Service
public class SysUserInfoService extends BaseService<SysUser>{

	@Autowired
	private SysUserInfoMapper sysUserInfoMapper;
	
	@Override
	public BaseMapper<SysUser> getMapper() {
		
		return sysUserInfoMapper;
	}

}
