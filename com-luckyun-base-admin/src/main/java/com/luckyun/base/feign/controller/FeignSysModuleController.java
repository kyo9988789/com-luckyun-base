package com.luckyun.base.feign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckyun.base.module.param.SysModuleParam;
import com.luckyun.base.module.service.SysModuleService;
import com.luckyun.model.module.SysModule;

@RequestMapping("/feign")
@RestController
public class FeignSysModuleController {

	@Autowired
	private SysModuleService sysModuleService;
	
	@GetMapping("/sys/module/readbyspath")
	public SysModule findBySpath(@RequestParam("spath") String spath) {
		SysModuleParam condition = new SysModuleParam();
		condition.setSpath(spath);
		List<SysModule> sysModules = sysModuleService.findAllNotParentId(condition);
		SysModule sysModule = new SysModule();
		if(sysModules != null && sysModules.size() >= 1) {
			sysModule = sysModules.get(0);
		}
		return sysModule;
	}
	
	@PostMapping("/sys/module/readMenuByCondition")
	public List<SysModule> findByMenus(@RequestBody SysModuleParam condition){
		List<SysModule> sysModules = sysModuleService.findAllNotParentId(condition);
		return sysModules;
	}
	
	@GetMapping("/sys/module/readModuleByUeserId")
	public List<SysModule>  readModuleByUeserId(@RequestParam("iuserid") Long iuserid){
		return sysModuleService.findByModuleByUserid(iuserid);
	}
	
}
