package com.luckyun.base.notice.param;

import java.util.List;
import java.util.Set;

import com.luckyun.model.dept.SysDept;
import com.luckyun.model.user.SysAccount;

import lombok.Data;

@Data
public class SysNoticeGenerateParam {

	private List<SysDept> sysDepts;
	
	private Set<Long> userHadRevicer;
	
	private Long inoticeid;
	
	private List<SysAccount> sysAccounts;
	
	private Set<Long> userHadRead;
}
