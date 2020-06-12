/**
 * Project Name: com-luckyun-base
 * File Name: SysUserLoginMapper.java
 * Package Name: com.luckyun.base.logger.mapper
 * Date: 2019年10月31日 上午9:48:02
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.logger.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.log.SysUserLogin;

/**
 * @author Jackie
 *
 */
@Repository
public interface SysUserLoginMapper extends BaseMapper<SysUserLogin>{
    public List<SysUserLogin> findByCondition(@Param("condition") SysUserLogin condition);
}
