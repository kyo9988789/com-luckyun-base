package com.luckyun.base.provider.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.model.role.SysRole;

@FeignClient(name="${application.servernm.luckyun-base:luckyun-base}",url = "${application.servernm.luckyun-base-url:}")
public interface BaseSysRoleProvider {

	/**
	 * 根据用户查找所属的角色列表
	 * @param iuserid 用户编号
	 * @return 返回的角色列表
	 */
	@GetMapping(value="/feign/sys/role/readRoleByIuserid")
	List<SysRole> findFRoleByIuserid(@RequestParam("iuserid") Long iuserid);
}
