package com.luckyun.region.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckyun.region.model.SysUserModuleDept;
import com.luckyun.region.param.SysUserModuleDeptParam;
import com.luckyun.region.service.SysUserModuleDeptService;

@RestController
@RequestMapping("/region/sysUserModuleDept")
public class SysUserModuleDeptController {

	@Autowired
	SysUserModuleDeptService sysUserModuleDeptService;
	
	@RequestMapping("readAll")
	public List<SysUserModuleDept> readAll(SysUserModuleDeptParam condition) {
		return sysUserModuleDeptService.readAll(condition);
	}
	
	@RequestMapping("update")
	public void add(@RequestBody SysUserModuleDeptParam condition) {
		sysUserModuleDeptService.handleModuleDept(condition);
	}
}
