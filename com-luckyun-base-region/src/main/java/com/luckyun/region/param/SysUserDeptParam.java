package com.luckyun.region.param;

import java.util.List;

import com.luckyun.model.user.SysAccount;
import com.luckyun.region.model.SysUserDept;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class SysUserDeptParam extends SysUserDept{

	/**
	 * 管辖的部门列表
	 */
	List<SysUserDept> regionDepts;
	
	/**
	 * 需要被clone的人员
	 */
	List<SysAccount> needCloneMembers;
}
