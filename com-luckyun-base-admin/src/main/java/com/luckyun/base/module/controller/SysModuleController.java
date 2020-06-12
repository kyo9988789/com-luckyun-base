package com.luckyun.base.module.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.module.param.SysModuleParam;
import com.luckyun.base.module.service.SysModuleService;
import com.luckyun.core.annotation.SortMaxValue;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.module.SysModule;

@UrlByPkgController
public class SysModuleController {
    @Autowired
    private SysModuleService sysModuleService;
    
    
    @GetMapping("readAll")
    public List<SysModule> readAll(SysModuleParam condition) {
        return sysModuleService.findAll(condition);
    }
    
    @GetMapping("noAuthReadAll")
    public List<SysModule> readAllNoParentId(SysModuleParam condition){
    	return sysModuleService.findAllNotParentId(condition);
    }
    
    @GetMapping("readOne")
    public SysModule readOne(@RequestParam("indocno") Long indocno) {
        return sysModuleService.findOne(indocno);
    }
    
    @PostMapping("add")
    @SortMaxValue(iparentidnm="iparentid")
    public SysModule add(@RequestBody SysModule sysModule) {
        sysModuleService.addModule(sysModule);
        return sysModule;
    }
    
    @PostMapping("update")
    public SysModule update(@RequestBody SysModule sysModule) {
        sysModuleService.updateModule(sysModule);
        return sysModule;
    }
    
    @PostMapping("delete")
    public void delete(@RequestBody SysModuleParam sysModuleParam) {
        List<SysModule> sysModules = sysModuleParam.getDelList();
        for(SysModule sysModule:sysModules) {
            sysModuleService.delete(sysModule);
        }
    }
    
    @PostMapping("updateDrag")
    public void updateDrag(@RequestBody SysModuleParam condition) 
        throws InstantiationException, IllegalAccessException
    {
        sysModuleService.dragModule(condition.getDindocno(),condition.getPindocno());
    }
    
    @GetMapping("resetSidcc")
    public void resetSidcc() {
        sysModuleService.resetSidcc();
    }

    @GetMapping("noAuthUserModule")
    public List<SysModule> readUserModule(){
        return sysModuleService.findByLoginUser();
    }
    
    @GetMapping("noAuthUserAllModule")
    public List<SysModule> readUserAllModule(){
        return sysModuleService.findAllByLoginUser();
    }
}
