package com.luckyun.base.provider.cache.dept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luckyun.core.event.DataEventQueue;
import com.luckyun.model.dept.SysDept;

@Component
public class SysDeptEvent extends DataEventQueue<SysDept>{

	@Autowired
	private SysDeptCacheHelper sysDeptCacheHelper;
	
	@Override
	protected void onUpdate(SysDept older, SysDept newer) {
		sysDeptCacheHelper.updateObj(newer.getIndocno());
	}

	@Override
	protected void onInsert(SysDept entity) {
		try {
			sysDeptCacheHelper.addObj(entity.getIndocno());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDelete(SysDept entity) {
		
	}

}
