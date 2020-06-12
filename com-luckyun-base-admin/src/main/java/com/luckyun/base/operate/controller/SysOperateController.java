package com.luckyun.base.operate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.operate.param.SysOperateParam;
import com.luckyun.base.operate.service.SysOperateService;
import com.luckyun.core.annotation.Pageable;
import com.luckyun.core.annotation.SortMaxValue;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.core.cache.annotation.CacheList;
import com.luckyun.model.operate.SysOperate;
import com.luckyun.model.operate.SysOperateGroup;

@UrlByPkgController
public class SysOperateController {
    
    @Autowired
    private SysOperateService sysOperateService;
    
    
    @GetMapping("readAll")
    @Pageable
    public List<SysOperate> readAll(SysOperateParam condition) {
        return sysOperateService.findAll(condition);
    }
    
    @GetMapping("readOne")
    public SysOperate readOne(@RequestParam("indocno") Long indocno) {
        return sysOperateService.findOne(indocno);
    }
    
    @GetMapping("readGroupOperate")
    @CacheList(list= {SysOperate.class,SysOperateGroup.class})
    public List<SysOperateGroup> readGroupOperate() {
        return sysOperateService.findGroupOperate();
    }
    
    @PostMapping("add")
    @SortMaxValue
    public SysOperate add(@RequestBody SysOperate sysOperate) {
        sysOperateService.insert(sysOperate);
        return sysOperate;
    }
    
    @PostMapping("update")
    public SysOperate update(@RequestBody SysOperate sysOperate) {
        sysOperateService.update(sysOperate);
        return sysOperate;
    }
    
    @PostMapping("delete")
    public void delete(@RequestBody SysOperateParam sysDeptParam) {
        List<SysOperate> sysOperates = sysDeptParam.getDelList();
        for(SysOperate sysOperate:sysOperates) {
            sysOperateService.deleteOperate(sysOperate);
        }
    }
    
    @PostMapping("updateDrag")
    public void updateDrag(@RequestBody SysOperateParam condition) 
        throws InstantiationException, IllegalAccessException
    {
        sysOperateService.drag(condition.getDindocno(),condition.getPindocno());
    }
}
