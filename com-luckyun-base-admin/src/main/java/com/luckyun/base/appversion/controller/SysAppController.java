package com.luckyun.base.appversion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.luckyun.base.appversion.param.SysAppParam;
import com.luckyun.base.appversion.service.SysAppService;
import com.luckyun.base.appversion.service.SysAppVersionService;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.core.exception.CustomException;
import com.luckyun.model.appversion.SysApp;
import com.luckyun.model.appversion.SysAppVersion;

@UrlByPkgController
public class SysAppController {

	@Autowired
	private SysAppService sysAppService;
	
	@Autowired
	private SysAppVersionService sysAppVersionService;
	
	@GetMapping("readAll")
	public List<SysApp> findAll(SysAppParam condition){
		return sysAppService.findAll(condition);
	}
	
	@GetMapping("readOne")
	public void findOne(SysAppParam condition) {
		
	}
	
	@PostMapping("add")
	public void add(SysApp sysApp) {
		sysAppService.insert(sysApp);
	}
	
	@PostMapping("addVersion")
	public void addAppVersion(SysAppVersion sysAppVersion) {
		sysAppVersionService.insert(sysAppVersion);
	}
	
	@PostMapping("update")
	public void update(SysApp sysApp) {
		
	}
	
	@PostMapping("delete")
	public void delete(SysAppParam appParam) {
		sysAppService.deleteApps(appParam.getDelList());
	}
	
	@GetMapping("noGetwayLastVer")
	public SysApp findLastVersion(@RequestParam(required = true) String appnm, @RequestParam(value="v",defaultValue="-1") Integer v,
			@RequestParam(value="type",defaultValue="apk") String typ) {
		 PageHelper.startPage(1, 1);
		 SysApp sysApp = sysAppService.findLastVersion(v, appnm, typ);
		 if(sysApp == null) {
			 throw new CustomException("没有更新的版本数据");
		 }else {
			 return sysApp;
		 }
	}
}
