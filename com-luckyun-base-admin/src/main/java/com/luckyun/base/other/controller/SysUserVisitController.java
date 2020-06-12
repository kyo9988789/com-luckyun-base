package com.luckyun.base.other.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luckyun.base.other.service.SysUserVisitService;
import com.luckyun.core.annotation.Pageable;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.module.SysModule;

@UrlByPkgController
public class SysUserVisitController {

	@Autowired
	private SysUserVisitService sysUserVisitService;
	
	@RequestMapping("/often/noAuthReadCuser")
	@Pageable
	public List<SysModule> findOftenVisit(){
		return sysUserVisitService.findModuleByUserId();
	}
}
