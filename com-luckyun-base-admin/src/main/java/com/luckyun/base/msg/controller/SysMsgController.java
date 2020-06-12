/**
 * Project Name: com-luckyun-base
 * File Name: SysMsgController.java
 * Package Name: com.luckyun.base.msg.controller
 * Date: 2019年8月9日 下午6:31:56
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.msg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.msg.param.SysMsgParam;
import com.luckyun.base.msg.service.SysMsgService;
import com.luckyun.core.annotation.Pageable;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.msg.SysMsg;
import com.luckyun.model.msg.SysMsgUser;

/**
 * 	内部交流接口
 * 
 * @action /sys/msg
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 下午6:31:56
 */

@UrlByPkgController
public class SysMsgController {

	@Autowired
	private SysMsgService sysMsgService;
	
	@RequestMapping(value = "readAllMyMsg")
	@Pageable
	public List<SysMsg> readall(SysMsgParam condition) {
		return sysMsgService.findAllMyMsg(condition);
	}
	
	@GetMapping(value = "readAllMyMsgGroup")
    @Pageable
    public List<SysMsg> readAllGroup(SysMsgParam condition) {
        return sysMsgService.findAllMyMsgGroup(condition);
    }
	
	@GetMapping("readOne")
    public SysMsg readOne(@RequestParam("indocno") Long indocno) {
        return sysMsgService.findOne(indocno);
    }
	
	@GetMapping("readMembers")
    public List<SysMsgUser> readMembers(@RequestParam("ilinkno") Long ilinkno,@RequestParam("iread") Integer iread) {
        return sysMsgService.findMembers(ilinkno,iread);
    }
	
	@PostMapping("add")
    public SysMsg add(@RequestBody SysMsgParam sysMsgParam) {
	    sysMsgService.add(sysMsgParam);
        return sysMsgParam;
    }
	
	@PostMapping(value = "delete")
	public void delete(@RequestBody SysMsgParam sysMsgParam) {
		this.sysMsgService.deleteMsg(sysMsgParam);
	}
}
