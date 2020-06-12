/**
 * Project Name: com-luckyun-base
 * File Name: SysUserLoginService.java
 * Package Name: com.luckyun.base.logger.service
 * Date: 2019年10月31日 上午9:46:42
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.logger.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.logger.mapper.SysUserLoginMapper;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.model.log.SysUserLogin;

/**
 * @author Jackie
 *
 */
@Service
public class SysUserLoginService extends BaseService<SysUserLogin>{
    
    @Autowired
    private SysUserLoginMapper sysUserLoginMapper;

    @Override
    public BaseMapper<SysUserLogin> getMapper() {
        return sysUserLoginMapper;
    }
    
    public List<SysUserLogin> findAll(SysUserLogin condition) {
        return sysUserLoginMapper.findByCondition(condition);
    }
}
