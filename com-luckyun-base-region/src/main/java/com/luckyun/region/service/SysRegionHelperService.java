package com.luckyun.region.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.km.config.LuckCoreLuttucePools;
import com.luckyun.model.user.SysAccount;
import com.luckyun.region.config.RegionConfigBean;
import com.luckyun.region.mapper.SysUserDeptMapper;
import com.luckyun.region.mapper.SysUserEquipMapper;
import com.luckyun.region.mapper.SysUserModuleDeptMapper;
import com.luckyun.region.model.SysUserDept;
import com.luckyun.region.model.SysUserEquip;
import com.luckyun.region.model.SysUserModuleDept;

@Service
public class SysRegionHelperService {
	
	@Autowired
	private SysUserDeptMapper sysUserDeptMapper;
	
	@Autowired
	private SysUserEquipMapper sysUserEquipMapper;
	
	@Autowired
	private SysUserModuleDeptMapper sysUserModuleDeptMapper;
	
	@Autowired
	private RegionConfigBean regionConfigBean;

	@TransactionalException
	public void addRegionForUser(SysAccount account,List<SysUserDept> data) {
		for(SysUserDept dept : data) {
			Long indocno = LuckCoreLuttucePools.getHincrById(dept.__getTableName());
			dept.setIndocno(indocno);
			dept.setIuserid(account.getIndocno());
		}
		sysUserDeptMapper.batchInsertSysUserDept(data);
	}
	
	@TransactionalException
	public void handleDeptRegionForUser(SysAccount account,List<SysUserDept> addRegion,
			List<SysUserDept> delRegion,AuthInfo authInfo) {
		if(addRegion != null && addRegion.size() >=1 ) {
			for(SysUserDept addR : addRegion) {
				addR.setIuserid(account.getIndocno());
				addR.setIndocno(LuckCoreLuttucePools.getHincrById(addR.__getTableName()));
				addR.setDregt(new Date());
				addR.setSregid(authInfo.getIndocno());
				addR.setSregnm(authInfo.getSname());
			}
			if(regionConfigBean.getDriverclass().toLowerCase().contains("mysql")) {
				sysUserDeptMapper.batchInsertSysUserDept(addRegion);
			}else {
				sysUserDeptMapper.batchInsertSysUserDeptOracle(addRegion);
			}
		}
		if(delRegion != null && delRegion.size() >= 1) {
			List<Long> delIndocnoList = 
					delRegion.stream().map(SysUserDept::getIndocno).collect(Collectors.toList());
			sysUserDeptMapper.batchDeleteSysUserDept(delIndocnoList);
		}
	}
	
	@TransactionalException
	public void handleEquipRegionForUser(SysAccount account
			,List<SysUserEquip> addRegion,List<SysUserEquip> delRegion,AuthInfo authInfo) {
		if(addRegion != null && addRegion.size() >=1 ) {
			for(SysUserEquip region : addRegion) {
				Long indocno = LuckCoreLuttucePools.getHincrById(region.__getTableName());
				region.setIndocno(indocno);
				region.setIuserid(account.getIndocno());
				region.setDregt(new Date());
				region.setSregid(authInfo.getIndocno());
				region.setSregnm(authInfo.getSname());
			}
			if(regionConfigBean.getDriverclass().toLowerCase().contains("mysql")) {
				sysUserEquipMapper.batchInsertSysUserEquip(addRegion);
			}else {
				sysUserEquipMapper.batchInsertSysUserEquipOracle(addRegion);
			}
		}
		if(delRegion != null && delRegion.size() >= 1){
			List<Long> delRegions = delRegion.stream().map(SysUserEquip::getIndocno).collect(Collectors.toList());
			sysUserEquipMapper.batchDeleteSysUserEquip(delRegions);
		}
	}
	
	@TransactionalException
	public void handleModuleDeptForUser(SysAccount account,List<SysUserModuleDept> addRegion
			,List<SysUserModuleDept> delRegion, AuthInfo authInfo) {
		if(addRegion != null && addRegion.size() >= 1) {
			for(SysUserModuleDept region : addRegion) {
				Long indocno = LuckCoreLuttucePools.getHincrById(region.__getTableName());
				region.setIndocno(indocno);
				region.setIuserid(account.getIndocno());
				region.setDregt(new Date());
				region.setSregid(authInfo.getIndocno());
				region.setSregnm(authInfo.getSname());
			}
			if(regionConfigBean.getDriverclass().toLowerCase().contains("mysql")) {
				sysUserModuleDeptMapper.batchInsertSysUserModuleDept(addRegion);
			}else {
				sysUserModuleDeptMapper.batchInsertSysUserModuleDeptOracle(addRegion);
			}
		}
		if(delRegion != null && delRegion.size() >= 1) {
			List<Long> delRegions = delRegion.stream()
					.map(SysUserModuleDept::getIndocno).collect(Collectors.toList());
			sysUserModuleDeptMapper.batchDeleteSysUserModuleDept(delRegions);
		}
	}
}
