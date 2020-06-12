/**
 * Project Name: com-luckyun-base
 * File Name: SysNoticeReplyService.java
 * Package Name: com.luckyun.base.notice.service
 * Date: 2020年1月3日 下午2:57:29
 * Copyright (c) 2020, luckyun All Rights Reserved.
 */
package com.luckyun.base.notice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.notice.mapper.SysNoticeReplyAttachmentMapper;
import com.luckyun.base.notice.mapper.SysNoticeReplyMapper;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.exception.CustomException;
import com.luckyun.core.km.keyhelper.IDGenerate;
import com.luckyun.core.tool.OssPathHelperUtils;
import com.luckyun.model.notice.SysNoticeReply;
import com.luckyun.model.notice.SysNoticeReplyAttachment;

/**
 * @author Jackie
 *
 */
@Service
public class SysNoticeReplyService extends BaseService<SysNoticeReply>{
    
    @Autowired
    private SysNoticeReplyMapper sysNoticeReplyMapper;
    
    @Autowired
    private SysNoticeReplyAttachmentMapper sysNoticeReplyAttachmentMapper;
    
    @Autowired
    private SysNoticeReplyAttachmentService sysNoticeReplyAttachmentService;
    
    @Autowired
    private OssPathHelperUtils ossPathHelperUtils;
    
    @Override
    public BaseMapper<SysNoticeReply> getMapper() {
        return sysNoticeReplyMapper;
    }
    
    public List<SysNoticeReply> findAll(Long ilinkno){
        AuthInfo authInfo = getAuthInfo();
        List<SysNoticeReply> list = new ArrayList<SysNoticeReply>();
        if(ilinkno==null || ilinkno<1) {
            throw new CustomException("base.common.indocno.null.error");
        }
        // 获取所有主评论
        list = sysNoticeReplyMapper.findAllMainReply(ilinkno);
        // 获取所有主评论的回复
        List<SysNoticeReply> replyList = sysNoticeReplyMapper.findAllReply(ilinkno);
        // 获取所有附件
        List<SysNoticeReplyAttachment> attachments = sysNoticeReplyAttachmentMapper.findByInoticeid(ilinkno);
        for(SysNoticeReply sysNoticeReply:list) {
            List<SysNoticeReply> replys = new ArrayList<SysNoticeReply>();
            List<SysNoticeReplyAttachment> mainAttachments = new ArrayList<SysNoticeReplyAttachment>();
            for(SysNoticeReply reply:replyList) {
                List<SysNoticeReplyAttachment> replyAttachments = new ArrayList<SysNoticeReplyAttachment>();
                for(SysNoticeReplyAttachment attachment:attachments) {
                    if(attachment.getIlinkno()==reply.getIndocno()) {
                        replyAttachments.add(attachment);
                    }
                }
                reply.setAttachments(replyAttachments);
                if(reply.getImainid()==sysNoticeReply.getIndocno()) {
                    replys.add(reply);
                }
            }
            sysNoticeReply.setReplys(replys);
            for(SysNoticeReplyAttachment attachment:attachments) {
                attachment.setNspath(ossPathHelperUtils.generateDownloadFile(authInfo.getSloginid()
                    , "base", attachment.getSpath(), attachment.getSname()));
                attachment.setNpreviewPath(ossPathHelperUtils.generatePdfPreview(authInfo.getSloginid()
                    , "base", attachment.getSpath(), attachment.getSname()));
                if(attachment.getIlinkno()==sysNoticeReply.getIndocno()) {
                    mainAttachments.add(attachment);
                }
            }
            sysNoticeReply.setAttachments(mainAttachments);
        }
        return list;
    }
    
    @TransactionalException
    public SysNoticeReply add(SysNoticeReply sysNoticeReply) {
        if(sysNoticeReply.getIlinkno()==null && sysNoticeReply.getIreplyid()==null) {
            throw new CustomException("base.common.indocno.null.error");
        }
        if(sysNoticeReply.getIreplyid()!=null) {
            SysNoticeReply reply = sysNoticeReplyMapper.findOne(sysNoticeReply.getIreplyid());
            if(reply!=null) {
                if(reply.getImainid()==null) {
                    sysNoticeReply.setImainid(sysNoticeReply.getIreplyid());
                }else {
                    sysNoticeReply.setImainid(reply.getImainid());
                }
                sysNoticeReply.setIlinkno(reply.getIlinkno());
                sysNoticeReply.setSreplynm(reply.getSregnm());
            }
        }
        
        sysNoticeReply.setIndocno(IDGenerate.getKey(sysNoticeReply));
        super.insert(sysNoticeReply);
        if(sysNoticeReply.getAttachments()!=null) {
            for(SysNoticeReplyAttachment attachment:sysNoticeReply.getAttachments()) {
                if(attachment!=null) {
                    attachment.setIlinkno(sysNoticeReply.getIndocno());
                    sysNoticeReplyAttachmentService.insert(attachment);
                }
            }
        }
        return sysNoticeReply;
    }
    
}
