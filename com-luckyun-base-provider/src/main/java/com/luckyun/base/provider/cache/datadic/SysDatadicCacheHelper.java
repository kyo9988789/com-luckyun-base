package com.luckyun.base.provider.cache.datadic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luckyun.base.provider.cache.CacheHelper;
import com.luckyun.base.provider.feign.BaseSysDatadicProvider;
import com.luckyun.core.cache.annotation.RedisCacheHash;
import com.luckyun.model.datadic.SysDatadic;

@Component
public class SysDatadicCacheHelper implements CacheHelper<List<SysDatadic>>{
	
	@Autowired
	private BaseSysDatadicProvider baseSysDatadicService;

	@Override
	@RedisCacheHash(key = "base_sys_datadic_list",rtnlistclazz = SysDatadic.class)
	public List<SysDatadic> getObjByField(Object field) {
		return getObjById(field);
	}

	@Override
	@RedisCacheHash(key = "base_sys_datadic_list",operate = "add",rtnlistclazz = SysDatadic.class)
	public List<SysDatadic> addObj(Object field) {
		return getObjById(field);
	}

	@Override
	@RedisCacheHash(key = "base_sys_datadic_list",operate = "update",rtnlistclazz = SysDatadic.class)
	public List<SysDatadic> updateObj(Object field) {
		return getObjById(field);
	}

	private List<SysDatadic> getObjById(Object field) {
		if(field instanceof String) {
			return baseSysDatadicService.findFSysDatadicByNameAlias(field.toString());
		}
		return null;
	}
}
