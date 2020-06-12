package com.luckyun.base.role.param;

import java.util.List;

import com.luckyun.model.role.SysRoleType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysRoleTypeParam extends SysRoleType{

	/**
	 * 需要删除的角色分类列表
	 */
	private List<SysRoleType> delList;
	
	/**
	 * 定位节点编号
	 */
	private Long pindocno;
	
	/**
	 * 拖拽定位编号
	 */
	private Long dindocno;
}
