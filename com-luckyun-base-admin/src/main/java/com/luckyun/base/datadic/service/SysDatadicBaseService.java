/**
 * Project Name: com-luckyun-base
 * File Name: SysDatadicBaseService.java
 * Package Name: com.luckyun.base.datadic.service
 * Date: 2019年8月10日 上午10:49:44
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.datadic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.luckyun.base.datadic.mapper.SysDatadicClassifyMapper;
import com.luckyun.base.datadic.mapper.SysDatadicClassifyRoleMapper;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.tool.CollecterMixUtils;
import com.luckyun.model.datadic.SysDatadicClassify;
import com.luckyun.model.datadic.SysDatadicClassifyRole;

/**
 * @author 姚小川
 *
 */
public class SysDatadicBaseService extends BaseService<SysDatadicClassify>{

	@Autowired
	private SysDatadicClassifyMapper sysDatadicClassifyMapper;
	
	@Autowired
	private SysDatadicClassifyRoleService sysDatadicClassifyRoleService;
	
	@Autowired
	private SysDatadicClassifyRoleMapper sysDatadicClassifyRoleMapper;
	
	protected void addClassifyRole(SysDatadicClassify sysDatadicClassify, String type) throws Exception {
		List<SysDatadicClassifyRole> oRoleList 
				= sysDatadicClassify.getRoleList();
		if(!CollectionUtils.isEmpty(oRoleList)) {
			List<SysDatadicClassifyRole> roleList 
					= sysDatadicClassifyRoleMapper.findByIclassifyId(sysDatadicClassify.getIndocno());
			if(!"add".equals(type)) {
				List<SysDatadicClassifyRole> mix
				= CollecterMixUtils.tMix(oRoleList, roleList, "iroleid");
				//需添加集合
				List<SysDatadicClassifyRole> addRoleList 
				= CollecterMixUtils.fdiffSet(oRoleList, mix, "iroleid");
				if(!CollectionUtils.isEmpty(addRoleList)) {
					sysDatadicClassify.setRoleList(addRoleList);
					this.sysDatadicClassifyRoleService.addAllClassifyRole(sysDatadicClassify);
				}
				//需删除集合
				List<SysDatadicClassifyRole> delRoleList 
				= CollecterMixUtils.fdiffSet(roleList, mix, "iroleid");
				if(!CollectionUtils.isEmpty(delRoleList)) {
					for (SysDatadicClassifyRole sysDatadicClassifyRole : delRoleList) {
						if(null != sysDatadicClassify)
							this.sysDatadicClassifyRoleService.delete(sysDatadicClassifyRole);
					}
				}
			}else {
				this.sysDatadicClassifyRoleService.addAllClassifyRole(sysDatadicClassify);
			}
		}
	}
	
	/**
	 * TODO(请说明该方法的实现功能).
	 * 
	 * @return
	 * @see com.luckyun.core.data.BaseService#getMapper()
	 */
	@Override
	public BaseMapper<SysDatadicClassify> getMapper() {
		return sysDatadicClassifyMapper;
	}

}
