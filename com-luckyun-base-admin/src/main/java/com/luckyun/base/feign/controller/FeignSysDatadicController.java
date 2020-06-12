package com.luckyun.base.feign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckyun.base.datadic.mapper.SysDatadicClassifyMapper;
import com.luckyun.base.datadic.mapper.SysDatadicMapper;
import com.luckyun.base.datadic.param.SysDatadicParam;
import com.luckyun.model.datadic.SysDatadic;
import com.luckyun.model.datadic.SysDatadicClassify;

@RequestMapping("/feign")
@RestController
public class FeignSysDatadicController {

	@Autowired
	private SysDatadicMapper sysDatadicMapper;
	
	@Autowired
	private SysDatadicClassifyMapper sysDatadicClassifyMapper;
	
	@GetMapping("sys/datadic/namealias")
	public List<SysDatadic> findDataDicByNameAlias(@RequestParam("namealias") String nameAlias){
		SysDatadicParam condition = new SysDatadicParam();
		condition.setSnamealias(nameAlias);
		return sysDatadicMapper.findAll(condition);
	}
	
	@GetMapping("/sys/datadic/classifyone")
	public SysDatadicClassify findOneDatadicClassify(@RequestParam("iclassifyid") Long iclassifyid) {
		return sysDatadicClassifyMapper.findOne(iclassifyid);
	}
}
