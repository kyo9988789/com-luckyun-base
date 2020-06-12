package com.luckyun.base.hepler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luckyun.base.datadic.mapper.SysDatadicMapper;
import com.luckyun.base.datadic.param.SysDatadicParam;
import com.luckyun.core.cache.annotation.RedisCacheHash;
import com.luckyun.model.datadic.SysDatadic;

@Component
public class SysDatadicHelperSource {

	@Autowired
	private SysDatadicMapper sysDatadicMapper;
	
	@RedisCacheHash(key = "base_sys_datadic_list",rtnlistclazz = SysDatadic.class)
	protected List<SysDatadic> getDatadicBySnameAlias(String nameAlias) {
		SysDatadicParam condition = new SysDatadicParam();
		condition.setSnamealias(nameAlias);
		return sysDatadicMapper.findAll(condition);
	}
	
	@RedisCacheHash(key = "base_sys_datadic_list", rtnlistclazz = SysDatadic.class,operate = "update")
	protected List<SysDatadic> updDatadicBySnameAlias(String nameAlias) {
		SysDatadicParam condition = new SysDatadicParam();
		condition.setSnamealias(nameAlias);
		return sysDatadicMapper.findAll(condition);
	}
}
