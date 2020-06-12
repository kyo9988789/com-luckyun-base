package com.luckyun.base.feign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckyun.base.role.mapper.SysRoleMapper;
import com.luckyun.model.role.SysRole;

@RequestMapping("/feign")
@RestController
public class FeignSysRoleController {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@GetMapping("/sys/role/readRoleByIuserid")
	public List<SysRole> findRoleByIuserid(@RequestParam("iuserid") Long iuserid){
		return sysRoleMapper.findRoleByUid(iuserid);
	}
}
