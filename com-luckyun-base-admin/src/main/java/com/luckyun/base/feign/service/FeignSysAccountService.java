package com.luckyun.base.feign.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.luckyun.base.user.service.SysUserService;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.model.user.SysAccount;

@Service
public class FeignSysAccountService {
	
	@Autowired
	private SysUserService sysUserService;

	@TransactionalException
	public void batchSysAccount(List<SysAccount> sysAccounts) throws Exception {
		for(SysAccount entity : sysAccounts) {
			if(StringUtils.isEmpty(entity.getSpassword())) {
				entity.setSpassword("123456");
			}
			if(!StringUtils.isEmpty(entity.getSpwd())) {
			    entity.setSpassword(entity.getSpwd());
			}
			sysUserService.add(entity, null);
		}
	}
}
