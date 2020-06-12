package com.luckyun.region.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckyun.core.response.ApiResult;
import com.luckyun.region.model.SysUserEquip;
import com.luckyun.region.param.SysUserEquipParam;
import com.luckyun.region.service.SysUserEquipService;

@RestController
@RequestMapping("/region/sysUserEquip")
public class SysUserEquipController {
	
	@Autowired
	SysUserEquipService sysUserEquipService;

	@RequestMapping("readAll")
	public List<SysUserEquip> readAll(SysUserEquipParam condition){
		if(condition.getIuserid() != null) {
			return sysUserEquipService.readOneByIuserId(condition.getIuserid());
		}
		return sysUserEquipService.readAll();
	}
	
	@PostMapping("update")
	public void update(@RequestBody SysUserEquipParam condition) {
		sysUserEquipService.updateEquip(condition);
	}
	
	@GetMapping("readOne")
	public ApiResult findUserEquip(@RequestParam("iuserid") Long iuserid){
		return sysUserEquipService.findShowParentNodeByIuserid(iuserid);
	}
}
