package com.luckyun.base.provider.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.model.msg.SysMsg;

@FeignClient(name="${application.servernm.luckyun-base:luckyun-base}",url = "${application.servernm.luckyun-base-url:}")
public interface BaseSysMsgProvider {
	
	/**
	 * 阅读消息
	 * @param indocno 消息主键
	 * @param iuserid 用户主键
	 * @param itype 消息处理的类型,1->批量阅读消息
	 */
	@GetMapping(value="/feign/sys/msg/readMsg")
	void readFSysMsg(@RequestParam("indocno") Long indocno,
			@RequestParam("iuserid") Long iuserid,@RequestParam("itype") Integer itype);
	
	
	/**
	 * 添加消息对象
	 * @param sysMsg 新增的消息对象
	 * @return 新增的对象
	 */
	@PostMapping(value = "/feign/sys/msg/add")
	SysMsg addFSysMsg(SysMsg sysMsg);
}
