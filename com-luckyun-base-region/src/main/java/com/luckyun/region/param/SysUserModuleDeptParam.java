package com.luckyun.region.param;

import java.util.List;

import com.luckyun.model.user.SysAccount;
import com.luckyun.region.model.SysUserModuleDept;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class SysUserModuleDeptParam extends SysUserModuleDept{

	private List<SysUserModuleDept> regions;
	
	private List<SysAccount> needCloneMembers;
}
