/**
 * Project Name: com-luckyun-base
 * File Name: SysModuleOperateMapper.java
 * Package Name: com.luckyun.base.module.mapper
 * Date: 2019年8月19日 下午3:22:11
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.module.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.module.SysModuleOperate;

/**
 * @author Jackie
 *
 */
@Repository
public interface SysModuleOperateMapper extends BaseMapper<SysModuleOperate>{
    /**
     * 根据操作id查询所有模块操作关系
     * @param ioperateid
     * @return
     */
    List<SysModuleOperate> findByIoperateid(@Param("ioperateid") Long ioperateid);
    
    /**
     * 根据操作组id查询所有模块操作关系
     * @param igroupid
     * @return
     */
    List<SysModuleOperate> findByIoperateGroupid(@Param("igroupid") Long igroupid);
    
    /**
     * 根据一组模块操作id获取模块
     * @param indocnos
     * @return
     */
    List<SysModuleOperate> findByImoduleOperateIds(@Param("indocnos") List<Long> indocnos);
}
