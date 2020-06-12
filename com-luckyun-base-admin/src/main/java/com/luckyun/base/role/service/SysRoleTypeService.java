package com.luckyun.base.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.luckyun.base.role.mapper.SysRoleMapper;
import com.luckyun.base.role.mapper.SysRoleTypeMapper;
import com.luckyun.base.role.param.SysRoleParam;
import com.luckyun.base.role.param.SysRoleTypeParam;
import com.luckyun.core.annotation.GlobalLockByRedis;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.drag.DragHelperService;
import com.luckyun.core.exception.CustomException;
import com.luckyun.model.role.SysRole;
import com.luckyun.model.role.SysRoleType;

@Service
public class SysRoleTypeService extends BaseService<SysRoleType>{

	@Autowired
	private SysRoleTypeMapper sysRoleTypeMapper;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	@Qualifier("dragHelperService")
	private DragHelperService dragHelperService;
	
	public List<SysRoleType> findAll(){
		return sysRoleTypeMapper.findAll();
	}
	
	public SysRoleType findOne(SysRoleTypeParam syRoleTypeParam) {
		SysRoleType entity = new SysRoleType();
		entity.setIndocno(syRoleTypeParam.getIndocno());
		return sysRoleTypeMapper.select(entity);
	}
	
	@TransactionalException
	public void delete(SysRoleTypeParam condition) {
		SysRoleParam roleParam = new SysRoleParam();
		List<SysRoleType> delList = condition.getDelList();
		for(SysRoleType roleType : delList) {
			roleParam.setItypeid(roleType.getIndocno());
			List<SysRole> roles = sysRoleMapper.findAll(roleParam);
			if(roles != null && roles.size()>=1) {
				throw new CustomException("base.role.delete.type");
			}
			sysRoleTypeMapper.delete(roleType);
		}
	}
	@TransactionalException
	public SysRoleType add(SysRoleType entity,SysRoleTypeParam condition) {
		super.insert(entity);
		return entity;
	}
	
	@TransactionalException
	public SysRoleType update(SysRoleType entity,SysRoleTypeParam condition) {
		sysRoleTypeMapper.update(entity);
		return entity;
	}
	
	@Override
	public BaseMapper<SysRoleType> getMapper() {
		return sysRoleTypeMapper;
	}

	@TransactionalException
	@GlobalLockByRedis
	public void dragSort(SysRoleTypeParam condition) {
		SysRoleType pRoleType = sysRoleTypeMapper.findOne(condition.getPindocno());
		SysRoleType dRoleType = sysRoleTypeMapper.findOne(condition.getDindocno());
		dragHelperService.drag(pRoleType, dRoleType);
	}
	
	@TransactionalException
	public void resetSort() {
		List<SysRoleType> sysRoleTypes = sysRoleTypeMapper.findAllAndIdel();
		for(int i =1;i<=sysRoleTypes.size();i++) {
			SysRoleType roleType = new SysRoleType();
			roleType.setIsort(i);
			roleType.setIndocno(sysRoleTypes.get(i-1).getIndocno());
			sysRoleTypeMapper.update(roleType);
		}
	}
}
