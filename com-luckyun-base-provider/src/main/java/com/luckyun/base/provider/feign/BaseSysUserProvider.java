package com.luckyun.base.provider.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.provider.feign.fallback.BaseSysUserServiceFallBack;
import com.luckyun.core.response.ApiResult;
import com.luckyun.model.user.SysAccount;

/**
 * 获取base的用户接口数据
 * @author yangj080
 *
 */
@FeignClient(name="${application.servernm.luckyun-base:luckyun-base}",url = "${application.servernm.luckyun-base-url:}",fallback = BaseSysUserServiceFallBack.class)
public interface BaseSysUserProvider {

	/**
	 * 根据角色分类查找用户列表
	 * @param iroletyid 角色分类
	 * @return 用户列表
	 */
	@GetMapping(value="/feign/sys/user/readbyirolety")
	List<SysAccount> findFSysUserByIrolety(Long iroletyid);

	/**
	 * 根据角色编号获取用户列表
	 * @param iroleid 角色编号
	 * @return 用户列表数据
	 */
	@GetMapping(value="/feign/sys/user/readbyiroleid")
	List<SysAccount> findFSysUserByIroleId(Long iroleid);

	/**
	 * 根据用户编号获取用户数据
	 * @param indocno 用户编号
	 * @return 用户数据
	 */
	@GetMapping(value="/feign/sys/user/readbyid")
	SysAccount findFSysUserByIndocno(@RequestParam("indocno") Long indocno);

	/**
	 * 根据登录名称获取账号信息
	 * @param sloginid 登录名称
	 * @return 用户信息
	 */
	@GetMapping("/feign/sys/user/readbysloginid")
	SysAccount findFSysUserBySloginid(@RequestParam("sloginid") String sloginid);
	
	/**
	 * 根据复杂条件查询用户列表数据
	 * @param condition 过滤条件
	 * @return 用户列表
	 */
	@PostMapping("/feign/sys/user/readbycomplex")
	List<SysAccount> findFUserByComplexCondition(Map<String,Object> condition);
	
	/**
	 * 查询用户信息分页数据根据复杂条件
	 * @param condition 查询条件
	 * @return 用户列表
	 */
	@GetMapping("/feign/sys/user/readpagebycomplex")
	ApiResult findFPageUserByComplexCondition(@RequestParam Map<String,Object> condition);
	
	/**
	 * 修改当前用户的密码
	 * @param indocno 用户编号
	 * @param password 密码
	 * @return 当前修改的用户
	 */
	@GetMapping("/feign/sys/user/upuserdatepassword")
	SysAccount updFPassword(@RequestParam("indocno") Long indocno,@RequestParam("password") String password);
	
	/**
	 * 添加当前新用户
	 * @param sysAccount 新增的用户对象
	 * @return 新增的用户对象
	 */
	@PostMapping("/feign/sys/user/asysdaccountd")
	SysAccount addFsysAccount(SysAccount sysAccount);
	
	/**
	 * 根据登录名称或者用户主键获取用户数据
	 * @param sloginid 登录名称
	 * @param indocno 用户主键
	 * @return 登录用户详情
	 */
	@GetMapping("/feign/sys/user/readAccountPwd")
	SysAccount findAccoutContainerPwd(@RequestParam("sloginid") String sloginid,@RequestParam("indocno") Long indocno);
	
	/**
	 * 批量添加用户数据
	 * @param sysAccount 需要新增的用户数据
	 * @return 新增的用户数据
	 */
	@PostMapping("/feign/sys/user/batchSysAccount")
	int batchAddSysAccount(List<SysAccount> sysAccountList);
}