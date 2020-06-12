package com.luckyun.base.provider.cache.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luckyun.core.event.DataEventQueue;
import com.luckyun.model.user.SysAccount;

@Component
public class SysUserDataEvent extends DataEventQueue<SysAccount>{

	@Autowired
	private SysUserCacheHelper sysUserCacheHelper; 
	
	@Override
	protected void onUpdate(SysAccount older, SysAccount newer) {
		sysUserCacheHelper.updateObj(older.getIndocno());
	}

	@Override
	protected void onInsert(SysAccount entity) {
		try {
			sysUserCacheHelper.addObj(entity.getIndocno());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDelete(SysAccount entity) {
		
	}

}
