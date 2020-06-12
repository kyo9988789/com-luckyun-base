package com.luckyun.base.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.user.param.SysUserParam;
import com.luckyun.base.user.service.SysUserBatchService;
import com.luckyun.base.user.service.SysUserService;
import com.luckyun.core.annotation.AutoBuildQueries;
import com.luckyun.core.annotation.Pageable;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.model.user.SysAccount;

@UrlByPkgController
public class SysUserController {
	
	@Autowired
	private SysUserBatchService sysUserBatchService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(value = "readAll")
	@Pageable
	public List<SysAccount> findAll(SysUserParam condition){
		if(condition.getIdeptid()!=null && condition.getIdeptid() < 0) {
			condition.setIdeptid(null);
		}
		return sysUserService.findUserByCondition(condition);
	}
	
	@PostMapping(value = "noGetwayReadAll")
	@AutoBuildQueries
	@Pageable
	public List<SysAccount> noGetwayReadAll(SysUserParam condition){
		if(condition.getIdeptid()!=null && condition.getIdeptid() < 0) {
			condition.setIdeptid(null);
		}
		return sysUserService.findUserByCondition(condition);
	}
	
	@GetMapping(value = "readOne")
	public SysAccount findOne(@RequestParam("indocno") Long indocno) {
		SysUserParam condition = new SysUserParam();
		condition.setIndocno(indocno);
		return sysUserService.findUserInfo(condition);
	}
	
	/**
	 * 获取当前登录用户信息
	 * @return 当前登录用户信息
	 */
	@GetMapping(value= "noAuthUser")
	public SysAccount findCuser() {
		AuthInfo authInfo = sysUserService.getAuthInfo();
		SysUserParam condition = new SysUserParam();
		condition.setIndocno(authInfo.getIndocno());
		return sysUserService.findUserInfo(condition);
	}
	
	@PostMapping(value="delete")
	public void delete(@RequestBody SysUserParam condition) {
		sysUserService.deleteLogic(condition);
	}
	
	@PostMapping(value="add")
	public SysAccount add(@RequestBody SysAccount sysUser) throws Exception {
		return sysUserService.add(sysUser, null);
	}
	
	@PostMapping(value="update")
	public SysAccount update(@RequestBody SysAccount sysUser) {
		return sysUserService.update(sysUser, null);
	}
	
	@PostMapping(value="batchUpdPassword")
	public void batchUpdatePass(@RequestBody SysUserParam condition) {
		sysUserBatchService.batchUpdatePass(condition);
	}
	
	@PostMapping(value="batchUpdState")
	public void batchUpdateState(@RequestBody SysUserParam condition) {
		sysUserBatchService.updateBatchUserState(condition);
	}
	
	@PostMapping(value="batchUpdRole")
	public void batchUpdateRole(@RequestBody SysUserParam condition) {
		sysUserBatchService.updateBatchUserRole(condition);
	}
	
	@PostMapping(value="batchUpdDept")
	public void batchUpdateDept(@RequestBody SysUserParam condition) {
		sysUserBatchService.updateBatchUserDept(condition);
	}
	@PostMapping(value="batchUpdPost")
	public void batchUpdatePost(@RequestBody SysUserParam condition) {
		sysUserBatchService.updateBatchUserPost(condition);
	}
	/**  
	 * 用户专业维护
	 * 
	 * @title 用户专业维护
	 * @author chengrui
	 * @date 2019/8/19 8:56
	 * @param condition|参数中文名称|SysUserParam|是否必填
	 * @param condition.sysUsers|参数中文名称|List<SysUser>|是否必填
	 * @param condition.sysMajors|参数中文名称|List<SysMajor>|是否必填
	 * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
	 * @resqParam detail|接口返回数据|Object|必填
	 * @resqParam operates|用户操作权限|String|必填
	 * @resqParam pagination|分页信息|String|必填
	 * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
	 * @requestType Post
	 *
	 */
	@PostMapping("batchUpdMajor")
	public void batchUpdateMajor(@RequestBody SysUserParam condition){
		sysUserBatchService.updateBathUserMajor(condition);
	}
}
