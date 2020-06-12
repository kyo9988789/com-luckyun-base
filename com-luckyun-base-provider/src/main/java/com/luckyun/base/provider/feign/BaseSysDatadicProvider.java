package com.luckyun.base.provider.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.provider.feign.fallback.BaseSysDatadicServiceFallBack;
import com.luckyun.model.datadic.SysDatadic;
import com.luckyun.model.datadic.SysDatadicClassify;

@FeignClient(name="${application.servernm.luckyun-base:luckyun-base}",url = "${application.servernm.luckyun-base-url:}",fallback = BaseSysDatadicServiceFallBack.class)
public interface BaseSysDatadicProvider {

	/**
	 * 根据数据字典分类别名获取数据字典列表数据
	 * @param nameAlias 数字字典分类别名
	 * @return 数据字典列表
	 */
	@GetMapping(value="/feign/sys/datadic/namealias")
	List<SysDatadic> findFSysDatadicByNameAlias(@RequestParam("namealias") String nameAlias);
	
	/**
	 * 根据分类编号获取分类对象数据
	 * @param iclassifyid 分类编号
	 * @return 数据字典分类对象数据
	 */
	@GetMapping(value = "/feign/sys/datadic/classifyone")
	SysDatadicClassify findClassifyByIndocno(@RequestParam("iclassifyid") Long iclassifyid);
}
