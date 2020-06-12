package com.luckyun.base.logger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.luckyun.base.logger.mapper.LoggerProviderMapper;
import com.luckyun.base.module.param.SysModuleParam;
import com.luckyun.base.module.service.SysModuleService;
import com.luckyun.core.data.entity.LogEntity;
import com.luckyun.model.module.SysModule;

@RestController
@RequestMapping("/logger/operate")
public class LogOperateController {
	
	@Autowired
	private SysModuleService sysModuleService;
	
	@Autowired
	private LoggerProviderMapper loggerProviderMapper;

	/**
	 * 根据前端传递的地址获取对应的日志
	 * @param headerSpath 传递的前端地址
	 * @param spathalias 传递的后端地址，如果有优先使用后端地址
	 * @param docid 单据编号
	 * @return 操作日志列表
	 */
	@GetMapping("noAuth")
	public List<LogEntity> getLogOperate(@RequestParam(value="spath",required = false) String headerSpath
			,@RequestParam(value="spathalias",required = false) String spathalias,@RequestParam("docid") Long docid){
		SysModuleParam condition = new SysModuleParam();
		if(!StringUtils.isEmpty(spathalias)) {
			return loggerProviderMapper.findLoggerByCondition(docid,spathalias + "%");
		}
		condition.setSpath(headerSpath);
		List<SysModule> sysModules = sysModuleService.findAllNotParentId(condition);
		SysModule sysModule = new SysModule();
		if(sysModules != null && sysModules.size() >= 1) {
			sysModule = sysModules.get(0);
		}
		return loggerProviderMapper.findLoggerByCondition(docid,sysModule.getSpathalias() + "%");
	}
	
	@GetMapping("noAuthMain")
	public List<LogEntity> getMainLogOperate(@RequestParam(value="spath",required = false) String headerSpath
			,@RequestParam(value="spathalias",required = false) String spathalias){
		SysModuleParam condition = new SysModuleParam();
		if(!StringUtils.isEmpty(spathalias)) {
			condition.setSpathalias(spathalias);
			return loggerProviderMapper.findMainLoggerByCondition(spathalias + "%");
		}
		condition.setSpath(headerSpath);
		List<SysModule> sysModules = sysModuleService.findAllNotParentId(condition);
		SysModule sysModule = new SysModule();
		if(sysModules != null && sysModules.size() >= 1) {
			sysModule = sysModules.get(0);
		}
		return loggerProviderMapper.findMainLoggerByCondition(sysModule.getSpathalias() + "%");
	}
}
