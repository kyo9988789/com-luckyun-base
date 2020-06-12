package com.luckyun.base.role.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.role.param.SysRoleParam;
import com.luckyun.base.role.service.SysRoleModuleOperateService;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.module.SysModule;
import com.luckyun.model.module.SysModuleOperate;

@UrlByPkgController
public class SysRoleModuleController {
	
	@Autowired
	private SysRoleModuleOperateService sysRoleModuleOperateService;

	@GetMapping("readModuleRelaOperate")
	public List<SysModule> getShowModuleOperate(){
		return sysRoleModuleOperateService.findModuleOperates();
	}
	
	@GetMapping("/readMoByIroleId")
	public List<SysModuleOperate> readModuleOperates(@RequestParam("iroleid") Long iroleid){
		return sysRoleModuleOperateService.findMopByRoleId(iroleid);
	}
	
	@PostMapping("/updateModuleOperate")
	public void updateRoleModule(@RequestBody SysRoleParam condition) {
		sysRoleModuleOperateService.updateRoleModule(condition);
	}
}
