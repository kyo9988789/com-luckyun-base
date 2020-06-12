package com.luckyun.base.provider.cache.dept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luckyun.base.provider.cache.CacheHelper;
import com.luckyun.base.provider.feign.BaseSysDeptProvider;
import com.luckyun.core.cache.annotation.RedisCacheHash;
import com.luckyun.model.datadic.SysDatadic;
import com.luckyun.model.dept.SysDept;

@Component
public class SysDeptCacheHelper implements CacheHelper<SysDept>{
	
	@Autowired
	private BaseSysDeptProvider baseSysDeptProvider;

	@Override
	@RedisCacheHash(key = "base_sys_dept_list",rtnlistclazz = SysDatadic.class)
	public SysDept getObjByField(Object field) {
		return getObjById(field);
	}

	@Override
	@RedisCacheHash(key = "base_sys_dept_list",operate = "add")
	public SysDept addObj(Object field) {
		return getObjById(field);
	}

	@Override
	@RedisCacheHash(key = "base_sys_dept_list",operate = "update")
	public SysDept updateObj(Object field) {
		return getObjById(field);
	}

	private SysDept getObjById(Object field) {
		if(field instanceof Long) {
			return baseSysDeptProvider.findByIndocno((Long)field);
		}
		return null;
	}
}
