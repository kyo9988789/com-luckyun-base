package com.luckyun.base.provider.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.model.module.SysModule;

@FeignClient(name="${application.servernm.luckyun-base:luckyun-base}",url = "${application.servernm.luckyun-base-url:}")
public interface BaseSysModuleProvider {

	/**
	 * 根据前端路径获取对应的模块详情
	 * @param spath 前端的url路径
	 * @return 对应的模块具体信息
	 */
	@GetMapping("/feign/sys/module/readbyspath")
	SysModule findByHeaderSpath(@RequestParam("spath") String spath);
	
	/**
	 * 根据复杂条件查询模块数据
	 * @param params 参数
	 * @return 模块数据
	 */
	@PostMapping("/feign/sys/module/readMenuByCondition")
	List<SysModule> findModule(Map<String,Object> params);
	
	/**
	 * 查找用户拥有的模块权限
	 * @param iuserid 用户编号 
	 * @return 模块列表
	 */
	@GetMapping("/feign/sys/module/readModuleByUeserId")
	List<SysModule> findModuleCuserId(@RequestParam("iuserid") Long iuserid);
}