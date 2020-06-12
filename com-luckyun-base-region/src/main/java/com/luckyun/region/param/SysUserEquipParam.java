package com.luckyun.region.param;

import java.util.List;

import com.luckyun.model.user.SysAccount;
import com.luckyun.region.model.SysUserEquip;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class SysUserEquipParam extends SysUserEquip{

	/**
	 * 管辖的部门列表
	 */
	List<SysUserEquip> regions;
	
	/**
	 * 需要被clone的人员
	 */
	List<SysAccount> needCloneMembers;
}
