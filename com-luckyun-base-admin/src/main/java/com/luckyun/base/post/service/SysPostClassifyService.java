package com.luckyun.base.post.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.luckyun.base.post.mapper.SysPostClassifyMapper;
import com.luckyun.base.post.param.SysPostClassifyParam;
import com.luckyun.core.annotation.GlobalLockByRedis;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.drag.DragHelperService;
import com.luckyun.model.post.SysPostClassify;

@Service
public class SysPostClassifyService extends BaseService<SysPostClassify>{

	@Autowired
	private SysPostClassifyMapper sysPostClassifyMapper;
	
	@Autowired
	@Qualifier("dragHelperService")
	private DragHelperService dragHelperService;
	
	public List<SysPostClassify> findAll(SysPostClassifyParam condition){
		return sysPostClassifyMapper.findAll(condition);
	}
	
	public SysPostClassify findOne(SysPostClassifyParam condition) {
		return sysPostClassifyMapper.select(condition);
	}
	
	@TransactionalException
	public SysPostClassify add(SysPostClassify entity,SysPostClassifyParam condition) {
		this.insert(entity);
		return entity;
	}
	
	@TransactionalException
	public SysPostClassify update(SysPostClassify entity
			,SysPostClassifyParam condition) {
		this.update(entity);
		return entity;
	}
	
	@TransactionalException
	public void deleteLogic(SysPostClassifyParam entity) {
		List<SysPostClassify> delList = entity.getDelList();
		for(SysPostClassify postClassify :delList) {
			sysPostClassifyMapper.delete(postClassify);
		}
	}
	
	@TransactionalException
	@GlobalLockByRedis
	public void dragPostClassify(SysPostClassifyParam condition) {
		SysPostClassify ppostClassify = sysPostClassifyMapper.findOne(condition.getPindocno());
		SysPostClassify dPostClassify = sysPostClassifyMapper.findOne(condition.getDindocno());
		dragHelperService.drag(ppostClassify, dPostClassify);
	}
	
	@Override
	public BaseMapper<SysPostClassify> getMapper() {
		return sysPostClassifyMapper;
	}
	
}
