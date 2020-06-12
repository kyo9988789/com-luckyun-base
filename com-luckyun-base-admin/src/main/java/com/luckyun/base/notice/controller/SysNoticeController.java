package com.luckyun.base.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.notice.param.SysNoticeParam;
import com.luckyun.base.notice.service.SysNoticeReplyService;
import com.luckyun.base.notice.service.SysNoticeService;
import com.luckyun.base.notice.service.SysNoticeUserService;
import com.luckyun.core.annotation.Pageable;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.notice.SysNotice;
import com.luckyun.model.notice.SysNoticeReply;
import com.luckyun.model.user.SysAccount;

@UrlByPkgController
public class SysNoticeController {

    @Autowired
    private SysNoticeService sysNoticeService;
    
    @Autowired
    private SysNoticeUserService sysNoticeUserService;
    
    @Autowired
    private SysNoticeReplyService sysNoticeReplyService;
    
    @RequestMapping("readAll")
    @Pageable
    public List<SysNotice> readAll(SysNoticeParam condition) {
        return sysNoticeService.findAll(condition);
    }
    
    @RequestMapping("readAllMembers")
    @Pageable
    public List<SysNotice> readAllMember(SysNoticeParam condition) {
        return sysNoticeService.findNormalMember(condition);
    }
    
    @RequestMapping("readAllMyNotice")
    @Pageable
    public List<SysNotice> readAllMyNotice(SysNoticeParam condition) {
        return sysNoticeService.findAllMyNotice(condition);
    }
    
    @GetMapping("readAllNotRead")
    @Pageable
    public List<SysNotice> readAllNotRead(SysNoticeParam condition){
    	return sysNoticeService.findNotRead(condition);
    }
    
    @GetMapping("readOne")
    public SysNotice readOne(@RequestParam("indocno") Long indocno) {
        return sysNoticeService.findOne(indocno);
    }
    
    @GetMapping("readNoticeUserRead")
    @Pageable
    public List<SysAccount> findUserReadState(SysNoticeParam condition){
    	return sysNoticeUserService.findNoticeUserReadState(condition);
    }
    
    @PostMapping("add")
    public SysNotice add(@RequestBody SysNotice sysNotice) {
        sysNoticeService.add(sysNotice);
        return sysNotice;
    }
    
    @PostMapping("update")
    public SysNotice update(@RequestBody SysNotice sysNotice) {
        sysNoticeService.updateNotice(sysNotice);
        return sysNotice;
    }
    
    @PostMapping("delete")
    public void delete(@RequestBody SysNoticeParam sysNoticeParam) {
        List<SysNotice> sysNotices = sysNoticeParam.getDelList();
        for(SysNotice sysNotice:sysNotices) {
            sysNoticeService.delete(sysNotice);
        }
    }
    
    @GetMapping("readAllReply")
    public List<SysNoticeReply> findAllReply(@RequestParam("indocno") Long indocno){
        return sysNoticeReplyService.findAll(indocno);
    }
    
    @PostMapping("addReply")
    public SysNoticeReply addReply(@RequestBody SysNoticeReply sysNoticeReply) {
        return sysNoticeReplyService.add(sysNoticeReply);
    }
}
