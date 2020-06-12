package com.luckyun.logger.provider.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckyun.base.provider.feign.BaseSysModuleProvider;
import com.luckyun.core.data.entity.LogEntity;
import com.luckyun.logger.provider.mapper.LoggerProviderMapper;
import com.luckyun.model.module.SysModule;

@RestController
@RequestMapping("/logger/operate")
public class LogOperateRest {
	
	@Autowired
	private BaseSysModuleProvider baseSysModuleProvider;
	
	@Autowired
	private LoggerProviderMapper loggerMapperProvider;

	/**
	 * 根据前端传递的地址获取对应的日志
	 * @param headerSpath 传递的前端地址
	 * @param docid 单据编号
	 * @return 操作日志列表
	 */
	@GetMapping("noAuth")
	public List<LogEntity> getLogOperate(@RequestParam("spath") String headerSpath
			,@RequestParam("docid") Long docid){
		SysModule sysModule = baseSysModuleProvider.findByHeaderSpath(headerSpath);
		return loggerMapperProvider.findLoggerByCondition(docid,sysModule.getSpathalias() + "%");
	}
}
