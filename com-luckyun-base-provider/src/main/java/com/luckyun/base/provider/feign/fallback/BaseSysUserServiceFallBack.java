package com.luckyun.base.provider.feign.fallback;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.luckyun.base.provider.feign.BaseSysUserProvider;
import com.luckyun.core.response.ApiResult;
import com.luckyun.model.user.SysAccount;

/**
 * 当服务无法请求到目标接口的返回,服务降级
 * @author yangj080
 *
 */
@Component
public class BaseSysUserServiceFallBack implements BaseSysUserProvider{

	@Override
	public List<SysAccount> findFSysUserByIrolety(Long irolety) {
		return null;
	}

	@Override
	public List<SysAccount> findFSysUserByIroleId(Long iroleid) {
		return null;
	}

	@Override
	public SysAccount findFSysUserByIndocno(Long indocno) {
		return null;
	}

	@Override
	public SysAccount findFSysUserBySloginid(String sloginid) {
		return null;
	}

	@Override
	public List<SysAccount> findFUserByComplexCondition(Map<String, Object> condition) {
		return null;
	}

	@Override
	public ApiResult findFPageUserByComplexCondition(Map<String, Object> condition) {
		return null;
	}

	@Override
	public SysAccount updFPassword(Long indocno, String password) {
		return null;
	}

	@Override
	public SysAccount addFsysAccount(SysAccount sysAccount) {
		return null;
	}

	@Override
	public SysAccount findAccoutContainerPwd(String sloginid, Long indocno) {
		return null;
	}

	@Override
	public int batchAddSysAccount(List<SysAccount> sysAccountList) {
		return 0;
	}

}
