package com.luckyun.base.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.luckyun.base.user.param.SysUserParam;
import com.luckyun.base.user.service.SysUserSubAttrService;
import com.luckyun.core.annotation.Pageable;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.user.SysAccount;

@UrlByPkgController
public class SysUserSubAttrController {
	
	@Autowired
	private SysUserSubAttrService sysUserSubAttrService;

	@GetMapping("/readByPost")
	@Pageable
	public List<SysAccount> findUserByPostId(SysUserParam condition){
		return sysUserSubAttrService.findByPost(condition);
	}
	
	@PostMapping("/addUserPost")
	public void addUserPost(@RequestBody SysUserParam condition) {
		sysUserSubAttrService.addUserPost(condition);
	}
	
	@PostMapping("/deleteUserPost")
	public void deleteUserPost(@RequestBody SysUserParam condition) {
		sysUserSubAttrService.deleteUserPost(condition);
	}
	
	@GetMapping("/readByRole")
	@Pageable
	public List<SysAccount> findUserByRoleId(SysUserParam condition){
		return sysUserSubAttrService.findByRole(condition);
	}
	@GetMapping("/readByMajor")
	@Pageable
	public List<SysAccount> findUserByMajor(SysUserParam condition){
		return sysUserSubAttrService.findByMajor(condition);
	}
	
	@PostMapping("/addUserRole")
	public void addUserRole(@RequestBody SysUserParam condition) {
		sysUserSubAttrService.addUserRole(condition);
	}
	
	@PostMapping("/deleteUserRole")
	public void deleteUserRole(@RequestBody SysUserParam condition) {
		sysUserSubAttrService.deleteUserRole(condition);
	}
	
	@PostMapping("/updateNk")
	public void updateNickname(@RequestBody SysAccount account) {
		sysUserSubAttrService.updateNk(account);
	}
}
