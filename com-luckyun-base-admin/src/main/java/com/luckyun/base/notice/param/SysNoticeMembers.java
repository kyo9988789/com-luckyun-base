package com.luckyun.base.notice.param;

import java.util.List;

import com.luckyun.annotation.Describe;
import com.luckyun.model.dept.SysDept;
import com.luckyun.model.role.SysRole;
import com.luckyun.model.user.SysAccount;

import lombok.Data;

@Data
public class SysNoticeMembers {

	@Describe("发送的部门")
	private List<SysDept> sendDepts;
	
	@Describe("发送的角色")
	private List<SysRole> sendRoles;
	
	@Describe("发送人员")
	private List<SysAccount> sendAccounts;
	
	@Describe("是否为全部成员")
	private Integer all;
}
