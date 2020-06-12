/**
 * Project Name: com-luckyun-base
 * File Name: FeignSysMsgController.java
 * Package Name: com.luckyun.base.feign.controller
 * Date: 2019年11月1日 下午5:01:03
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.feign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckyun.base.msg.param.SysMsgParam;
import com.luckyun.base.msg.service.SysMsgService;
import com.luckyun.core.annotation.Pageable;
import com.luckyun.model.msg.SysMsg;

/**
 * @author Jackie
 *
 */
@RequestMapping("/feign/sys/msg")
@RestController
public class FeignSysMsgController {
    
    @Autowired
    private SysMsgService sysMsgService;
    
    @PostMapping("add")
    public SysMsg add(@RequestBody SysMsgParam sysMsgParam) {
        sysMsgService.add(sysMsgParam);
        return sysMsgParam;
    }
    
    @GetMapping(value = "readMsg")
    @Pageable
    public void readMsg(@RequestParam("indocno") Long indocno,
    		@RequestParam("iuserid") Long iuserid,@RequestParam("itype") Integer itype) {
    	if(itype == 1) {
    		sysMsgService.batchRead(indocno);
    	}else {
    		sysMsgService.readFeignMsg(indocno,iuserid);
        }
    }
}
