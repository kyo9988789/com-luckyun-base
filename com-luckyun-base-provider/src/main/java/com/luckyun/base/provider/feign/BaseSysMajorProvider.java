package com.luckyun.base.provider.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.model.major.SysMajor;

/**
 * sysMajor专业feign接口
 * @author yangj080
 *
 */
@FeignClient(name="${application.servernm.luckyun-base:luckyun-base}",url = "${application.servernm.luckyun-base-url:}")
public interface BaseSysMajorProvider {

	/**
	 * 根据编号获取对应的专业对象
	 * @param indocno 专业主键编号
	 * @return 专业对象
	 */
	@GetMapping("/feign/sys/major/readbyid")
	SysMajor findFMajorById(@RequestParam("indocno") Long indocno);
	
	/**
	 *	 根据用户编号获取对应的专业对象
	 * @param iuserid 用户主键
	 * @return
	 */
	@GetMapping(value = "/feign/sys/major/readByIuserid")
	public List<SysMajor> findFMajorByIuserid(@RequestParam("iuserid") Long iuserid);
}