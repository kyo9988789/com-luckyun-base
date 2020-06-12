/**
 * Project Name: com-luckyun-base
 * File Name: SysDatadicClassifyRoleService.java
 * Package Name: com.luckyun.base.datadicrole.service
 * Date: 2019年8月9日 下午2:35:57
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.datadic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.luckyun.base.datadic.mapper.SysDatadicClassifyRoleMapper;
import com.luckyun.base.datadic.param.SysDatadicClassifyRoleParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.model.datadic.SysDatadicClassify;
import com.luckyun.model.datadic.SysDatadicClassifyRole;

/**
 * 	数据字典/角色关系服务类
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 下午2:35:57
 */

@Service
public class SysDatadicClassifyRoleService extends BaseService<SysDatadicClassifyRole>{

	@Autowired
	private SysDatadicClassifyRoleMapper sysDatadicClassifyRoleMapper;
	
	/**
	 * 
	 * 	添加数据字典/角色关系
	 * 
	 * @param sysDatadicClassifyRole
	 * @return
	 * @author yaoxc
	 * @date 2019年8月9日 下午2:38:19
	 *
	 */
	protected SysDatadicClassifyRole addClassifyRole(SysDatadicClassifyRole sysDatadicClassifyRole) {
		this.insert(sysDatadicClassifyRole);
		return sysDatadicClassifyRole;
	}
	
	/**
	 * 
	 * 	批量添加数据字典/角色关系
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 * @author yaoxc
	 * @date 2019年8月9日 下午3:27:45
	 *
	 */
	protected List<SysDatadicClassifyRole> addAllClassifyRole(SysDatadicClassify sysDatadicClassify) throws Exception{
		List<SysDatadicClassifyRole> roleList = sysDatadicClassify.getRoleList();
		if(CollectionUtils.isEmpty(roleList)) {
			throw new Exception("base.dic.addrole.error");
		}
		for (SysDatadicClassifyRole sysDatadicClassifyRole : roleList) {
			sysDatadicClassifyRole.setIclassifyid(sysDatadicClassify.getIndocno());
			this.insert(sysDatadicClassifyRole);
		}
		return roleList;
	}
	
	/**
	 * 
	 * 	删除数据字典/角色对象
	 * 
	 * @param sysDatadicClassifyRoleParam
	 * @throws Exception
	 * @author yaoxc
	 * @date 2019年8月9日 下午2:41:38
	 *
	 */
	protected void deleteClassRole(SysDatadicClassifyRoleParam sysDatadicClassifyRoleParam) throws Exception {
		List<SysDatadicClassifyRole> delList = sysDatadicClassifyRoleParam.getDelList();
		if(CollectionUtils.isEmpty(delList)) {
			throw new Exception("base.dic.delrole.error");
		}
	}
	
	/**
	 * TODO(请说明该方法的实现功能).
	 * 
	 * @return
	 * @see com.luckyun.core.data.BaseService#getMapper()
	 */
	@Override
	public BaseMapper<SysDatadicClassifyRole> getMapper() {
		return sysDatadicClassifyRoleMapper;
	}

}
