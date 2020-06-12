/**
 * Project Name: com-luckyun-base
 * File Name: SysRoleOperateMapper.java
 * Package Name: com.luckyun.base.role.mapper
 * Date: 2019年8月21日 下午2:18:00
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.luckyun.base.role.param.SysRoleModuleOperate;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.module.SysModuleOperate;
import com.luckyun.model.role.SysRoleOperate;

/**
 * @author Jackie
 *
 */
public interface SysRoleOperateMapper extends BaseMapper<SysRoleOperate>{
	
    List<SysRoleOperate> findByIoperateid(@Param("ioperateid") Long ioperateid);
    
    /**
	 * 查找可以进行授权的模块
	 * @return 模块操作关系
	 */
	List<SysRoleModuleOperate> findModuleOperateByModule();
	
	/**
	 * 根据角色获取模块操作关系
	 * @param iroleid 角色编号
	 * @return 模块操作关系数据
	 */
	List<SysModuleOperate> findMopByRole(@Param("iroleid") Long iroleid);
}
