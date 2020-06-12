/**
 * Project Name: com-luckyun-base
 * File Name: SysDatadicClassifyController.java
 * Package Name: com.luckyun.base.datadiccla.controller
 * Date: 2019年8月9日 上午9:16:39
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.datadic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.luckyun.base.datadic.param.SysDatadicClassifyParam;
import com.luckyun.base.datadic.service.SysDatadicClassifyService;
import com.luckyun.core.annotation.SortMaxValue;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.datadic.SysDatadicClassify;

/**
 * 
 * 	数组字典分类接口
 * 
 * @action /sys/datadic
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 上午9:17:42
 */

@UrlByPkgController
public class SysDatadicClassifyController {

	@Autowired
	private SysDatadicClassifyService sysDatadicClassifyService;
	
	@GetMapping(value = "readAll")
	public List<SysDatadicClassify> readall(SysDatadicClassifyParam condition){
		return this.sysDatadicClassifyService.findAll(condition);
	}
	
	@GetMapping(value = "readOne")
	public SysDatadicClassify readone(SysDatadicClassify sysDatadicClassify) {
		return this.sysDatadicClassifyService.findOne(sysDatadicClassify);
	}
	
	@GetMapping(value = "readDatadicByParent")
	public List<SysDatadicClassify> readDatadicByParent(SysDatadicClassifyParam condition) {
		return this.sysDatadicClassifyService.findDatadicByParent(condition);
	}
	
	@PostMapping(value = "add")
	@SortMaxValue(iparentidnm = "iparentid")
	public SysDatadicClassify add(@RequestBody SysDatadicClassify sysDatadicClassify) throws Exception {
		return this.sysDatadicClassifyService.addClassify(sysDatadicClassify);
	}
	
	@PostMapping(value = "update")
	public SysDatadicClassify update(@RequestBody SysDatadicClassify sysDatadicClassify) {
		return this.sysDatadicClassifyService.updateClassify(sysDatadicClassify);
	}
	
	@PostMapping(value = "delete")
	public void delete(@RequestBody SysDatadicClassifyParam sysDatadicClassifyParam) throws Exception {
		this.sysDatadicClassifyService.deleteClassify(sysDatadicClassifyParam);
	}
	
	@PostMapping(value = "updateDrag")
	public void dragSort(@RequestBody SysDatadicClassifyParam condition) {
		sysDatadicClassifyService.dragSort(condition);
	}
	
	@GetMapping(value = "addExist")
	public boolean addExist(SysDatadicClassifyParam param) {
		return this.sysDatadicClassifyService.addExist(param);
	}
	
	@GetMapping("resetSidcc")
    public void resetSidcc() {
	    sysDatadicClassifyService.resetSidcc();
    }
}
