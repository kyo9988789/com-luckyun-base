/**
 * Project Name: com-luckyun-base
 * File Name: SysOperateService.java
 * Package Name: com.luckyun.base.operate.service
 * Date: 2019年8月8日 下午8:31:38
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.operate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.luckyun.base.module.mapper.SysModuleOperateMapper;
import com.luckyun.base.operate.mapper.SysOperateGroupMapper;
import com.luckyun.base.operate.mapper.SysOperateMapper;
import com.luckyun.base.operate.param.SysOperateGroupParam;
import com.luckyun.base.operate.param.SysOperateParam;
import com.luckyun.core.annotation.GlobalLockByRedis;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.drag.DragHelperService;
import com.luckyun.model.module.SysModuleOperate;
import com.luckyun.model.operate.SysOperate;
import com.luckyun.model.operate.SysOperateGroup;

/**
 * @author Jackie
 *
 */
@Service
public class SysOperateService extends BaseService<SysOperate>{

    @Autowired
    SysOperateMapper sysOperateMapper;
    
    @Autowired
    SysOperateGroupMapper sysOperateGroupMapper;
    
    @Autowired
    SysModuleOperateMapper sysModuleOperateMapper;
    
    @Autowired
    @Qualifier("dragHelperService")
    private DragHelperService dragHelperService;

    @Override
    public BaseMapper<SysOperate> getMapper() {
        return sysOperateMapper;
    }
    
    public List<SysOperate> findAll(SysOperateParam condition) {
        List<SysOperate> list = sysOperateMapper.findAll(condition);
        return list;
    }
    
    public SysOperate findOne(Long indocno) {
        return sysOperateMapper.findOne(indocno);
    }
    
    public List<SysOperateGroup> findGroupOperate(){
        List<SysOperate> operates = sysOperateMapper.findAll(new SysOperateParam());
        List<SysOperateGroup> groups = sysOperateGroupMapper.findAll(new SysOperateGroupParam());
        if(groups!=null && groups.size() > 0 && operates!=null && operates.size() > 0) {
            for(SysOperateGroup group : groups ) {
                for(SysOperate operate : operates) {
                    if(group.getIndocno().equals(operate.getIgroupid())) {
                        if(group.getOperates() != null) {
                            group.getOperates().add(operate);
                        }else {
                            List<SysOperate> sysOperates = new ArrayList<>();
                            sysOperates.add(operate);
                            group.setOperates(sysOperates);
                        }
                    }
                }
            }
        }
        return groups;
    }
    
    /**
     * 删除操作
     * @param sysOperate
     */
    @TransactionalException
    public void deleteOperate(SysOperate sysOperate) {
        this.delete(sysOperate);
        List<SysModuleOperate> moduleOperates = sysModuleOperateMapper.findByIoperateid(sysOperate.getIndocno());
        for(SysModuleOperate moduleOperate : moduleOperates) {
            // 删除模块对应关系
            sysModuleOperateMapper.delete(moduleOperate);
        }
    }
    
    /**
     * 拖拽排序
     * @param dIndocno 拖拽对象
     * @param pIndocno 拖拽目标
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @GlobalLockByRedis
    public void drag(Long dIndocno, Long pIndocno) 
            throws InstantiationException, IllegalAccessException {
        SysOperate dObj = sysOperateMapper.findOne(dIndocno);
        SysOperate pObj = sysOperateMapper.findOne(pIndocno);
        dragHelperService.drag(pObj, dObj);
    }
}
