package com.luckyun.base.feign.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.dept.service.SysDeptService;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.model.dept.SysDept;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FeignSysDeptService {
	
	@Autowired
	private SysDeptService sysDeptService;

	@TransactionalException
	public void batchAddDept(List<SysDept> sysDepts) {
		for(SysDept sysDept : sysDepts) {
			if(sysDept.getIcompanyid() == null) {
				log.error("添加的部门所属的公司未找到!");
			}
			if(sysDept.getSregid() == null) {
				log.error("部门的添加人未找到");
			}
			sysDeptService.addNoAuthInfo(sysDept);
		}
	}
}
