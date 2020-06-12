package com.luckyun.base.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.luckyun.base.user.param.SysUserParam;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.tool.Md5HelperUtils;
import com.luckyun.model.user.SysAccount;

/**
 * 批量操作用户功能
 * @author yangj080
 *
 */
@Service("batchSysUserService")
public class SysUserBatchService extends SysUserBaseService{
	
	@TransactionalException
	public void updateBatchUserState(SysUserParam condition) {
		List<SysAccount> sysUsers = condition.getSysUsers();
		for(SysAccount sysUser : sysUsers) {
			SysAccount user = new SysAccount();
			user.setIstate(condition.getIstate());
			user.setIndocno(sysUser.getIndocno());
			super.update(user);
		}
	}
	
	@TransactionalException
	public void batchUpdatePass(SysUserParam condition) {
		String spassword = condition.getSpassword();
		if(StringUtils.isEmpty(spassword)) {
			//默认为123456
			spassword = "123456";
		}
		spassword = Md5HelperUtils.md5(spassword);
		for(SysAccount sysUser : condition.getSysUsers()) {
			SysAccount user = new SysAccount();
			user.setSpassword(spassword);
			user.setIndocno(sysUser.getIndocno());
			super.update(user);
		}
	}
	
	@TransactionalException
	public void updateBatchUserRole(SysUserParam condition) {
		for(SysAccount sysUser : condition.getSysUsers()) {
			sysUser.setSysRoles(condition.getSysRoles());
			super.updateRoles(sysUser);
		}
	}
	
	@TransactionalException
	public void updateBatchUserDept(SysUserParam condition) {
		AuthInfo authInfo = getAuthInfo();
		for(SysAccount sysUser : condition.getSysUsers()) {
//			sysUser.setSysDepts(condition.getSysDepts());
//			super.updateDepts(sysUser, authInfo);
			super.updateOwnDept(sysUser, authInfo, condition.getIdeptid());
		}
	}

	@TransactionalException
	public void updateBatchUserPost(SysUserParam condition) {
		for(SysAccount sysUser : condition.getSysUsers()) {
			sysUser.setSysPosts(condition.getSysPosts());
			super.updatePosts(sysUser);
		}
	}
	@TransactionalException
	public void updateBathUserMajor(SysUserParam condition){
		for(SysAccount sysUser : condition.getSysUsers()) {
			sysUser.setSysMajors(condition.getSysMajors());
			super.updateMajor(sysUser);
		}

	}
}
