package com.luckyun.base.feign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckyun.base.major.service.SysMajorService;
import com.luckyun.model.major.SysMajor;

@RequestMapping("/feign")
@RestController
public class FeignSysMajorController {
	
	@Autowired
	private SysMajorService sysMajorService;
	
	@GetMapping(value="/sys/major/readbyid")
	public SysMajor provideFeiSysMajorByIndocno(@RequestParam("indocno") Long indocno) {
		SysMajor sysMajor = sysMajorService.findOne(indocno);
		return sysMajor;
	}
	
	@GetMapping(value = "/sys/major/readByIuserid")
	public List<SysMajor> provideFeiSysMajorByIuserid(@RequestParam("iuserid") Long iuserid) {
		return this.sysMajorService.readByIuserid(iuserid);
	}
}