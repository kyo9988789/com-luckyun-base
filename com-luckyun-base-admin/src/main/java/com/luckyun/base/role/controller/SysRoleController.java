package com.luckyun.base.role.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.role.param.SysRoleParam;
import com.luckyun.base.role.param.SysRoleTypeParam;
import com.luckyun.base.role.service.SysRoleService;
import com.luckyun.base.role.service.SysRoleTypeService;
import com.luckyun.core.annotation.SortMaxValue;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.core.exception.CustomException;
import com.luckyun.model.role.SysRole;
import com.luckyun.model.role.SysRoleType;

@UrlByPkgController
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysRoleTypeService sysRoleTypeService;
	
	@GetMapping("/getError")
	public void getError(){
		String[] arrs = new String[1];
		try {
			System.out.println(arrs[1]);
		}catch(Exception e){
			throw new CustomException("base.password.error");
		}
	}
	
	@GetMapping("/readAll")
	public List<SysRole> readAll(SysRoleParam condition){
		return sysRoleService.findAll(condition);
	}
	
	@GetMapping("/readTypeAll")
	public List<SysRoleType> readTyAll(){
		return sysRoleTypeService.findAll();
	}
	
	@GetMapping("/readAllUserNum")
	public List<SysRole> readAllStatis(SysRoleParam condition){
		return sysRoleService.findAllStatis(condition);
	}
	
	@GetMapping("/readOne")
	public SysRole readOne(SysRoleParam condition){
		SysRole entity = new SysRole();
		entity.setIndocno(condition.getIndocno());
		return sysRoleService.select(entity);
	}
	
	@PostMapping("/add")
	@SortMaxValue(iparentidnm = "itypeid")
	public SysRole add(@RequestBody SysRole entity) {
		return sysRoleService.add(entity, null);
	}
	
	@PostMapping("/clone")
	public SysRole clone(@RequestBody SysRole sysRole) {
		return sysRoleService.clone(sysRole);
	}
	
	@PostMapping("/addType")
	@SortMaxValue
	public SysRoleType addType(@RequestBody SysRoleType entity) {
		return sysRoleTypeService.add(entity, null);
	}
	
	@PostMapping("/update")
	public SysRole update(@RequestBody SysRole entity) {
		return sysRoleService.update(entity, null);
	}
	
	@PostMapping("/updateType")
	public SysRoleType update(@RequestBody SysRoleType entity) {
		return sysRoleTypeService.update(entity,null);
	}
	
	@PostMapping("/delete")
	public void delete(@RequestBody SysRoleParam condition) {
		sysRoleService.delete(condition);
	}
	
	@PostMapping("/deleteType")
	public void deleteType(@RequestBody SysRoleTypeParam condition) {
		sysRoleTypeService.delete(condition);
	}
	
	@PostMapping("/updateDrag")
	public void updateDrag(@RequestBody SysRoleParam condition) {
		sysRoleService.dragSort(condition);
	}
	
	@GetMapping("/resetSort")
	public void resetSort(@RequestParam("itypeid") Long itypeid) {
		sysRoleService.resetSort(itypeid);
	}
	
	@PostMapping("/updateTypeDrag")
	public void updateTypeDrag(@RequestBody SysRoleTypeParam condition) {
		sysRoleTypeService.dragSort(condition);
	}
	
	@GetMapping("/resetTypeSort")
	public void resetTypeSort() {
		sysRoleTypeService.resetSort();
	}
	
}
