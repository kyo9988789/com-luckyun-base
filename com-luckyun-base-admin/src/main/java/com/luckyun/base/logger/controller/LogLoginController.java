/**
 * Project Name: com-luckyun-base
 * File Name: LogLoginController.java
 * Package Name: com.luckyun.base.logger.controller
 * Date: 2019年10月31日 上午9:29:17
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.logger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luckyun.base.logger.service.SysUserLoginService;
import com.luckyun.core.annotation.Pageable;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.log.SysUserLogin;

/**
 * @author Jackie
 *
 */
@UrlByPkgController
public class LogLoginController {
    
    @Autowired
    private SysUserLoginService sysUserLoginService;
    
    @RequestMapping(value = "readAll")
    @Pageable
    public List<SysUserLogin> readall(SysUserLogin condition) {
        return sysUserLoginService.findAll(condition);
    }
}
