package com.luckyun.base.preset.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.preset.service.SysUserPresetService;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.preset.SysUserPreset;

@UrlByPkgController
public class SysUserPresetController {
	
	@Autowired
	private SysUserPresetService sysUserPresetService;

	@GetMapping("/readOne")
	public SysUserPreset readOne(@RequestParam("spath") String spath) {
		return sysUserPresetService.findOneBySpath(spath);
	}
	
	@PostMapping("/add")
	public void add(@RequestBody SysUserPreset preset) {
		sysUserPresetService.add(preset);
	}
	
	@PostMapping("/update")
	public void update(@RequestBody SysUserPreset preset) {
		sysUserPresetService.add(preset);
	}
}
