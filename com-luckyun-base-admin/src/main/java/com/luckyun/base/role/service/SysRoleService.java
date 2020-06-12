package com.luckyun.base.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.luckyun.base.role.mapper.SysRoleMapper;
import com.luckyun.base.role.mapper.SysRoleOperateMapper;
import com.luckyun.base.role.param.SysRoleParam;
import com.luckyun.core.annotation.GlobalLockByRedis;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.drag.DragHelperService;
import com.luckyun.core.exception.CustomException;
import com.luckyun.model.module.SysModuleOperate;
import com.luckyun.model.role.SysRole;
import com.luckyun.model.role.SysRoleOperate;

@Service
public class SysRoleService extends BaseService<SysRole>{

	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysRoleOperateMapper sysRoleOperateMapper;
	
	@Autowired
	@Qualifier("dragHelperService")
	private DragHelperService dragHelperService;
	
	public List<SysRole> findAll(SysRoleParam condition){
		return sysRoleMapper.findAll(condition);
	} 
	
	public List<SysRole> findAllStatis(SysRoleParam condition){
		return sysRoleMapper.findAllStatis(condition);
	}
	
	public SysRole findOne(SysRoleParam sysRoleParam) {
		SysRole sysRole = new SysRole();
		sysRole.setIndocno(sysRoleParam.getIndocno());
		return super.select(sysRole);
	}
	
	@TransactionalException
	public void delete(SysRoleParam condition) {
		List<SysRole> delList = condition.getDelList();
		for(SysRole sysRole : delList) {
			Long count = sysRoleMapper.findCountUserByRole(sysRole.getIndocno());
			if(count >= 1) {
				throw new CustomException("base.role.delete.info");
			}
			super.delete(sysRole);
		}
	}
	
	@TransactionalException
	public SysRole add(SysRole entity,SysRoleParam condition) {
		entity.setRolemembercount(null);
		super.insert(entity);
		return entity;
	}
	
	@TransactionalException
	public SysRole update(SysRole entity,SysRoleParam condition) {
		entity.setRolemembercount(null);
		super.update(entity);
		return entity;
	}
	
	@Override
	public BaseMapper<SysRole> getMapper() {
		return sysRoleMapper;
	}

	@TransactionalException
	public SysRole clone(SysRole orgSysRole) {
		Long orgIndonco = orgSysRole.getIndocno();
		orgSysRole.setIndocno(null);
		super.insert(orgSysRole);
		List<SysModuleOperate> bnewMop = sysRoleOperateMapper.findMopByRole(orgIndonco);
		for(SysModuleOperate moduleOperate : bnewMop) {
			SysRoleOperate operate = new SysRoleOperate();
			operate.setIroleid(orgSysRole.getIndocno());
			//存储的是moduleoperate关系表的主键
			operate.setIoperateid(moduleOperate.getIndocno());
			sysRoleOperateMapper.insert(operate);
		}
		return orgSysRole;
	}
	@TransactionalException
	@GlobalLockByRedis
	public void dragSort(SysRoleParam condition) {
		SysRole pSysRole = sysRoleMapper.findOne(condition.getPindocno());
		SysRole dSysRole = sysRoleMapper.findOne(condition.getDindocno());
		dragHelperService.drag(pSysRole, dSysRole, true, "itypeid", dSysRole.getItypeid());
	}
	
	@TransactionalException
	public void resetSort(Long itypeid) {
		SysRoleParam condition = new SysRoleParam();
		condition.setItypeid(itypeid);
		List<SysRole> sysRoles = sysRoleMapper.findAllNotDel(condition);
		for(int i =1;i<=sysRoles.size();i++) {
			SysRole sysRole = new SysRole();
			sysRole.setIsort(i);
			sysRole.setIndocno(sysRoles.get(i-1).getIndocno());
			sysRoleMapper.update(sysRole);
		}
	}
}
