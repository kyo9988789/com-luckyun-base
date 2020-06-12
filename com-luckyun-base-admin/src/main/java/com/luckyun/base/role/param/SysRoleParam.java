package com.luckyun.base.role.param;

import java.util.List;

import com.luckyun.model.module.SysModuleOperate;
import com.luckyun.model.role.SysRole;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysRoleParam extends SysRole{

	private List<SysRole> delList;
	
	/**
	 *模块操作关系数据
	 */
	private List<SysModuleOperate> customOperates;
	
	/**
	 * 定位节点编号
	 */
	private Long pindocno;
	
	/**
	 * 拖拽节点编号
	 */
	private Long dindocno;
}
