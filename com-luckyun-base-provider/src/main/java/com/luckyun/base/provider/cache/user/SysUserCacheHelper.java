package com.luckyun.base.provider.cache.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luckyun.base.provider.cache.CacheHelper;
import com.luckyun.base.provider.feign.BaseSysUserProvider;
import com.luckyun.core.cache.annotation.RedisCacheHash;
import com.luckyun.model.user.SysAccount;

@Component
public class SysUserCacheHelper implements CacheHelper<SysAccount>{

	@Override
	@RedisCacheHash(key = "base_user_info_list")
	public SysAccount getObjByField(Object field) {
		return getUserById(field);
	}

	@Override
	@RedisCacheHash(key = "base_user_info_list",operate = "add")
	public SysAccount addObj(Object field) {
		return getUserById(field);
	}

	@Override
	@RedisCacheHash(key = "base_user_info_list",operate = "update")
	public SysAccount updateObj(Object field) {
		return getUserById(field);
	}
	
	@Autowired
	private BaseSysUserProvider baseSysUserService;

	private SysAccount getUserById(Object field) {
		if(field instanceof Long) {
			return baseSysUserService.findFSysUserByIndocno((Long)field);
		}
		return null;
	}
}
