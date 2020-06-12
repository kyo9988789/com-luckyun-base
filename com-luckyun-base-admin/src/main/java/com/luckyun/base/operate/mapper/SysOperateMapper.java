/**
 * Project Name: com-luckyun-base
 * File Name: SysOperateMapper.java
 * Package Name: com.luckyun.base.operate.mapper
 * Date: 2019年8月8日 下午8:29:40
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.operate.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.luckyun.base.operate.param.SysOperateParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.operate.SysOperate;

/**
 * @author Jackie
 *
 */
public interface SysOperateMapper extends BaseMapper<SysOperate>{
    
    List<SysOperate> findAll(@Param("condition") SysOperateParam condition);
    
    SysOperate findOne(@Param("indocno") Long indocno);

}
