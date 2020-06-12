package com.luckyun.region.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckyun.region.model.SysUserDept;
import com.luckyun.region.param.SysUserDeptParam;
import com.luckyun.region.service.SysUserDeptService;

/**
 * 管辖部门功能
 * @author yangj080
 *
 */
@RestController
@RequestMapping("/region/sysUserDept")
public class SysUserDeptController {
	
	@Autowired
	private SysUserDeptService sysUserDeptService;

	@RequestMapping("/readAll")
	public List<SysUserDept> readAll(SysUserDeptParam condition){
		if(condition.getIuserid() != null) {
			return sysUserDeptService.readAllByOne(condition.getIuserid());
		}
		return sysUserDeptService.readAll();
	}
	
	@PostMapping("/update")
	public void updateDeptRegion(@RequestBody SysUserDeptParam sysUserDeptParam) {
		//System.out.println(sysUserDeptParam);
		sysUserDeptService.updateDeptRegion(sysUserDeptParam);
	}
	
	@PostMapping("/clone")
	public void cloneRegion(@RequestBody SysUserDeptParam condition) {
		sysUserDeptService.cloneMembers(condition);
	}
}
