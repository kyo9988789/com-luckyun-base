/**
 * Project Name: com-luckyun-base
 * File Name: SysModuleService.java
 * Package Name: com.luckyun.base.module.service
 * Date: 2019年8月15日 下午4:10:58
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.module.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.luckyun.base.module.mapper.SysModuleMapper;
import com.luckyun.base.module.mapper.SysModuleOperateMapper;
import com.luckyun.base.module.param.SysModuleParam;
import com.luckyun.base.role.mapper.SysRoleOperateMapper;
import com.luckyun.core.annotation.GlobalLockByRedis;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.cache.annotation.CacheDetail;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.drag.DragHelperService;
import com.luckyun.core.km.keyhelper.IDGenerate;
import com.luckyun.core.tool.CollecterMixUtils;
import com.luckyun.model.module.SysModule;
import com.luckyun.model.module.SysModuleOperate;
import com.luckyun.model.operate.SysOperate;
import com.luckyun.model.role.SysRoleOperate;
import org.springframework.util.StringUtils;

/**
 * @author Jackie
 *
 */
@Service
public class SysModuleService extends BaseService<SysModule> {
    @Autowired
    SysModuleMapper sysModuleMapper;
    
    @Autowired
    SysModuleOperateMapper sysModuleOperateMapper;
    
    @Autowired
    SysRoleOperateMapper sysRoleOperateMapper;
    
    @Autowired
    @Qualifier("dragHelperService")
    private DragHelperService dragHelperService;
   
    @Override
    public BaseMapper<SysModule> getMapper() {
        return sysModuleMapper;
    }
    
    public List<SysModule> findAll(SysModuleParam condition) {
        List<SysModule> list = sysModuleMapper.findAll(condition);
        return list;
    }
    
    public List<SysModule> findAllNotParentId(SysModuleParam condition){
    	List<SysModule> sysModules = sysModuleMapper.findAllNoOtherCondition(condition);
    	return sysModules;
    }
    
    public SysModule findOne(Long indocno) {
        SysModule sysModule = sysModuleMapper.findOne(indocno);
        List<SysOperate> operates = sysModuleMapper.findAllOperates(indocno);
        sysModule.setOperates(operates);
        List<SysModuleOperate> customOperates = sysModuleMapper.findCustomOperates(indocno);
        sysModule.setCustomOperates(customOperates);
        return sysModule;
    }
    
    @TransactionalException
    public SysModule addModule(SysModule sysModule) {
        Long iparentid = sysModule.getIparentid() != null ? sysModule.getIparentid() : 0;
        sysModule.setIparentid(iparentid);
        sysModule.setIndocno(IDGenerate.getKey(sysModule));
        sysModule = this.setSidcc(sysModule);
        this.insert(sysModule);
        // 拥有的通用操作
        List<SysOperate> operates = sysModule.getOperates();
        if(operates!=null) {
            addModuleOperate(sysModule,operates);
        }
        // 自定义操作
        List<SysModuleOperate> costomOperates = sysModule.getCustomOperates();
        if(costomOperates!=null) {
            addCostomOperate(sysModule,costomOperates);
        }
        return sysModule;
    }
    
    @TransactionalException
    public SysModule updateModule(SysModule sysModule) {
        this.update(sysModule);
        // 更新通用操作
        List<SysOperate> operates = sysModule.getOperates();
        if(operates!=null) {
            updateModuleOperate(sysModule,operates);
        }
        // 更新自定义操作
        List<SysModuleOperate> costomOperates = sysModule.getCustomOperates();
        if(costomOperates!=null) {
            updateCustomOperate(sysModule,costomOperates);
        }
        return sysModule;
    }
    
    /**
     * 拖拽排序
     * @param dIndocno 拖拽对象
     * @param pIndocno 拖拽目标
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @GlobalLockByRedis
    public void dragModule(Long dIndocno, Long pIndocno) 
            throws InstantiationException, IllegalAccessException {
        SysModule dObj = sysModuleMapper.findOne(dIndocno);
        SysModule pObj = sysModuleMapper.findOne(pIndocno);
        dragHelperService.drag(pObj, dObj,true,"iparentid",dObj.getIparentid());
    }
    
    // 插入sidcc
    private SysModule setSidcc(SysModule sysModule) {
        if(sysModule.getIparentid() != null && sysModule.getIparentid() != 0) {
            SysModule pSysDept = new SysModule();
            pSysDept.setIndocno(sysModule.getIparentid());
            pSysDept = this.select(pSysDept);
            sysModule.setSidcc(pSysDept.getSidcc()  + sysModule.getIndocno() + "/");
        }else {
            sysModule.setSidcc("/" + sysModule.getIndocno() + "/");
        }
        return sysModule;
    }
    // 重置所有sidcc
    @TransactionalException
    public void resetSidcc() {
        List<SysModule> modules = sysModuleMapper.findByIparentid(0L);
        for(SysModule module : modules) {
            module.setSidcc("/"+module.getIndocno()+"/");
            sysModuleMapper.update(module);
            resetSidcc(module);
        }
    }
    private void resetSidcc(SysModule pModule) {
        List<SysModule> modules = sysModuleMapper.findByIparentid(pModule.getIndocno());
        for(SysModule module : modules) {
            module.setSidcc(pModule.getSidcc()+module.getIndocno()+"/");
            sysModuleMapper.update(module);
            resetSidcc(module);
        }
    }
    
    // 添加通用操作
    private void addModuleOperate(SysModule module,List<SysOperate> operates) {
        for(SysOperate operate:operates) {
            SysModuleOperate moduleOperate = new SysModuleOperate();
            moduleOperate.setImoduleid(module.getIndocno());
            moduleOperate.setIoperateid(operate.getIndocno());
            sysModuleOperateMapper.insert(moduleOperate);
        }
    }
    
    // 添加自定义操作
    private void addCostomOperate(SysModule module,List<SysModuleOperate> operates) {
        for(SysModuleOperate operate:operates) {
            operate.setImoduleid(module.getIndocno());
            sysModuleOperateMapper.insert(operate);
        }
    }
    
    // 更新通用操作
    private void updateModuleOperate(SysModule module,List<SysOperate> operates) {
        List<SysModuleOperate> oldModuleOperates = sysModuleMapper.findModuleOperates(module.getIndocno());
        List<SysModuleOperate> newModuleOperates = new ArrayList<SysModuleOperate>();
        for(SysOperate operate:operates) {
            SysModuleOperate moduleOperate = new SysModuleOperate();
            moduleOperate.setImoduleid(module.getIndocno());
            moduleOperate.setIoperateid(operate.getIndocno());
            newModuleOperates.add(moduleOperate);
        }
        List<SysModuleOperate> mix = CollecterMixUtils.tMix(newModuleOperates, oldModuleOperates, "ioperateid");
    
        List<SysModuleOperate> addModuleOperates = CollecterMixUtils.fdiffSet(newModuleOperates, mix, "ioperateid");
        List<SysModuleOperate> delModuleOperates = CollecterMixUtils.fdiffSet(oldModuleOperates, mix, "ioperateid");
        
        for(SysModuleOperate moduleOperate:addModuleOperates) {
            sysModuleOperateMapper.insert(moduleOperate);
        }
        for(SysModuleOperate moduleOperate:delModuleOperates) {
            sysModuleOperateMapper.delete(moduleOperate);
            List<SysRoleOperate> roleOperates = sysRoleOperateMapper.findByIoperateid(moduleOperate.getIndocno());
            for(SysRoleOperate roleOperate:roleOperates) {
                sysRoleOperateMapper.delete(roleOperate);
            }
        }
    }
    
    // 更新自定义操作
    private void updateCustomOperate(SysModule module,List<SysModuleOperate> operates) {
        List<SysModuleOperate> oldCustomOperates = sysModuleMapper.findCustomOperates(module.getIndocno());
        List<SysModuleOperate> mix = CollecterMixUtils.tMix(operates, oldCustomOperates, "spath");
    
        List<SysModuleOperate> addCustomOperates = CollecterMixUtils.fdiffSet(operates, mix, "spath");
        List<SysModuleOperate> delModuleOperates = CollecterMixUtils.fdiffSet(oldCustomOperates, mix, "spath");
        
        for(SysModuleOperate moduleOperate:addCustomOperates) {
            moduleOperate.setImoduleid(module.getIndocno());
            sysModuleOperateMapper.insert(moduleOperate);
        }
        for(SysModuleOperate moduleOperate:delModuleOperates) {
            sysModuleOperateMapper.delete(moduleOperate);
            List<SysRoleOperate> roleOperates = sysRoleOperateMapper.findByIoperateid(moduleOperate.getIndocno());
            for(SysRoleOperate roleOperate:roleOperates) {
                sysRoleOperateMapper.delete(roleOperate);
            }
        }
    }

    @CacheDetail(list={SysModule.class},expire=-1)
    public SysModule findByPathalias(String spathalias){
        List<SysModule> modules = sysModuleMapper.findByPathalias(spathalias);
        if(null!= modules && modules.size()>0){
            return modules.get(0);
        }
        return null;
    }

    /**
     * 用户所拥有菜单
     * @return
     */
//    @CacheList(list={SysModule.class, SysUserRole.class,SysRoleOperate.class,SysModuleOperate.class},expire=-1)
    public List<SysModule> findByLoginUser(){
        Long userid = super.getAuthInfo().getIndocno();
        return findByModuleByUserid(userid);
    }
    
    public List<SysModule> findAllByLoginUser(){
        Long userid = super.getAuthInfo().getIndocno();
        return findAllByModuleByUserid(userid);
    }
    /**
     * 根据用户编号获取用户拥有的模块列表
     * @param iuserid 用户编号
     * @return 模块列表
     */
    public List<SysModule> findByModuleByUserid(Long iuserid){
    	Set<Long> moduleIds = new HashSet<>();
        if(null != iuserid && iuserid!=0){
            List<SysModule> sysModules = sysModuleMapper.findByLoginUser(iuserid);
            for (SysModule sysModule: sysModules) {
                this.moduleids(moduleIds,sysModule.getSidcc());
            }
            if(moduleIds.size()==0){    //没有菜单则直接返回
                return null;
            }
            SysModuleParam sysModuleParam = new SysModuleParam();
            sysModuleParam.setModuleIds(new ArrayList<>(moduleIds));
            return sysModuleMapper.findUserModule(sysModuleParam);
        }
        return null;
    }
    
    public List<SysModule> findAllByModuleByUserid(Long iuserid){
    	Set<Long> moduleIds = new HashSet<>();
        if(null != iuserid && iuserid!=0){
            List<SysModule> sysModules = sysModuleMapper.findAllByLoginUser(iuserid);
            for (SysModule sysModule: sysModules) {
                this.moduleids(moduleIds,sysModule.getSidcc());
            }
            if(moduleIds.size()==0){    //没有菜单则直接返回
                return null;
            }
            SysModuleParam sysModuleParam = new SysModuleParam();
            sysModuleParam.setModuleIds(new ArrayList<>(moduleIds));
            return sysModuleMapper.findUserModule(sysModuleParam);
        }
        return null;
    }

    private void moduleids(Set<Long> moduleIds,String sidcc){
        for(String sid : sidcc.split("/")) {
            if(!StringUtils.isEmpty(sid)) {
                moduleIds.add(Long.valueOf(sid));
            }
        }
    }
}
