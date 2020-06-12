package com.luckyun.base.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.luckyun.base.post.param.SysPostClassifyParam;
import com.luckyun.base.post.service.SysPostClassifyService;
import com.luckyun.core.annotation.SortMaxValue;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.post.SysPostClassify;

@UrlByPkgController
public class SysPostClassifyController {

	@Autowired
	private SysPostClassifyService sysPostClassifyService;
	
	@GetMapping("readAll")
	public List<SysPostClassify> readAll(SysPostClassifyParam condition){
		return sysPostClassifyService.findAll(condition);
	}
	
	@PostMapping("/add")
	@SortMaxValue
	public SysPostClassify add(@RequestBody SysPostClassify entity) {
		return sysPostClassifyService.add(entity,null);
	}
	
	@PostMapping("/update")
	public SysPostClassify update(@RequestBody SysPostClassify entity) {
		return sysPostClassifyService.update(entity, null);
	}
	
	@PostMapping("/delete")
	public void delete(@RequestBody SysPostClassifyParam condition) {
		sysPostClassifyService.deleteLogic(condition);
	}
	
	@GetMapping("/readOne")
	public SysPostClassify findOne(SysPostClassifyParam condition) {
		return sysPostClassifyService.findOne(condition);
	}
	
	@PostMapping("/updateDrag")
	public void dragClassify(@RequestBody SysPostClassifyParam condition) {
		sysPostClassifyService.dragPostClassify(condition);
	}
}
