/**
 * Project Name: com-luckyun-base
 * File Name: SysNoticeService.java
 * Package Name: com.luckyun.base.notice.service
 * Date: 2019年8月8日 下午5:36:04
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.notice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luckyun.base.notice.helper.SysNoticeHelper;
import com.luckyun.base.notice.mapper.SysNoticeAttachmentMapper;
import com.luckyun.base.notice.mapper.SysNoticeDetailMapper;
import com.luckyun.base.notice.mapper.SysNoticeMapper;
import com.luckyun.base.notice.param.SysNoticeMembers;
import com.luckyun.base.notice.param.SysNoticeParam;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.event.GlobalEvent;
import com.luckyun.core.exception.CustomException;
import com.luckyun.core.tool.CollecterMixUtils;
import com.luckyun.core.tool.OssPathHelperUtils;
import com.luckyun.model.notice.SysNotice;
import com.luckyun.model.notice.SysNoticeAttachment;
import com.luckyun.model.notice.SysNoticeDetail;

/**
 * @author Jackie
 *
 */
@Service
public class SysNoticeService extends BaseService<SysNotice>{
    
    @Autowired
    private SysNoticeMapper sysNoticeMapper;
    
    @Autowired
    private SysNoticeDetailMapper sysNoticeDetailMapper;
    
    @Autowired
    private SysNoticeAttachmentMapper sysNoticeAttachmentMapper;
    
    @Autowired
    private SysNoticeAttachmentService sysNoticeAttachmentService;
    
    @Autowired
    private OssPathHelperUtils ossPathHelperUtils;
    
    @Autowired
    private GlobalEvent globalEvent;
    
    @Autowired
    private SysNoticeHelper sysNoticeHelper;
    
    @Override
    public BaseMapper<SysNotice> getMapper() {
        return sysNoticeMapper;
    }
    
    /**
     * 管理页面公告接口
     * @param condition 条件
     * @return
     */
    public List<SysNotice> findAll(SysNoticeParam condition) {
    	List<SysNotice> list = new ArrayList<SysNotice>();
    	AuthInfo authInfo = getAuthInfo();
    	//管理员可以看到所有
    	if(authInfo.getSloginid().startsWith("admin")) {
    		condition.setNowdate(new Date());
    		list =  sysNoticeMapper.findAll(condition);
    	}else {
    		condition.setIcreater(authInfo.getIndocno());
    		condition.setIuserid(authInfo.getIndocno());
    		condition.setNowdate(new Date());
    		list = sysNoticeMapper.findNormalMember(condition);
    	}
    	for(SysNotice notice : list) {
			sysNoticeHelper.changeState(notice);
			sysNoticeHelper.datdicConvert(notice);
		}
    	if(list != null && list.size() >= 1) {
			//设置公告阅读数量情况
			sysNoticeHelper.setNoticeReadState(list);
		}
        return list;
    }
    
    /**
 	* 普通人员看到的是自己可以看到通知公告
     * @param condition 查询条件
     * @return 通知列表
     */
    public List<SysNotice> findNormalMember(SysNoticeParam condition){
    	AuthInfo authInfo = getAuthInfo();
    	condition.setIuserid(authInfo.getIndocno());
		condition.setNowdate(new Date());
		List<SysNotice> result = sysNoticeMapper.findNormalMember(condition);
		
		sysNoticeHelper.convertSysNoticeReadState(result, authInfo.getIndocno());
    	return result;
    }
    
    public List<SysNotice> findAllMyNotice(SysNoticeParam condition){
    	AuthInfo authInfo = getAuthInfo();
    	condition.setIuserid(authInfo.getIndocno());
		condition.setNowdate(new Date());
		List<SysNotice> result = sysNoticeMapper.findAllByMyNotice(condition);
		
		sysNoticeHelper.convertSysNoticeReadState(result, authInfo.getIndocno());
    	return result;
    }
    
    /**
     * 查找当前用户未读的公告
     * @param condition 当前用户主要条件
     * @return 未读公告
     */
    public List<SysNotice> findNotRead(SysNoticeParam condition){
    	AuthInfo authInfo = getAuthInfo();
    	condition.setIuserid(authInfo.getIndocno());
    	condition.setNowdate(new Date());
    	return sysNoticeMapper.findNoticeByNotRead(condition);
    }
    
    public SysNotice findOne(Long indocno) {
    	AuthInfo authInfo = getAuthInfo();
    	SysNotice result = sysNoticeMapper.findOne(indocno,null);
    	//非管理员
    	if(!authInfo.getSloginid().startsWith("admin")) {
    		//非公告创建人
    		if(!authInfo.getIndocno().equals(result.getSregid())) {
    			result = sysNoticeMapper.findOne(indocno,authInfo.getIndocno());
    		}
    	}
    	if(result != null) {
    		//设置公告阅读数量情况
    		sysNoticeHelper.setNoticeReadState(result);
	    	List<SysNoticeAttachment> attachments = sysNoticeAttachmentMapper.findByIlinkno(indocno);
	    	for(SysNoticeAttachment noticeAttachment : attachments) {
	    		noticeAttachment.setNspath(ossPathHelperUtils.generateDownloadFile(authInfo.getSloginid()
	    				, result.getSprojectno(), noticeAttachment.getSpath(), noticeAttachment.getSname()));
	    		noticeAttachment.setNpreviewPath(ossPathHelperUtils.generatePdfPreview(authInfo.getSloginid()
	    				, result.getSprojectno(), noticeAttachment.getSpath(), noticeAttachment.getSname()));
	    	}
	    	result.setSysNoticeAttachments(attachments);
	    	//字典翻译
	    	sysNoticeHelper.datdicConvert(result);
	    	//设置公告发送的人员
	    	String sendMembers = result.getSsendMembers();
	    	if(!StringUtils.isEmpty(sendMembers)) {
		    	SysNoticeMembers memberContent = JSON.parseObject(sendMembers,SysNoticeMembers.class);
		    	//没有发给所有人
		    	if(memberContent !=null && memberContent.getAll() == null) {
		    		result.setSendDepts(memberContent.getSendDepts());
		    		result.setSendRoles(memberContent.getSendRoles());
		    		result.setSendAccounts(memberContent.getSendAccounts());
		    	}
	    	}
	    	result.setHadRead("已读");
	    	//设置公告阅读情况
	    	sysNoticeHelper.addReadClick(result.getIndocno(), authInfo.getIndocno());
    	}
        return result;
    }
    
    @TransactionalException
    public void add(SysNotice sysNotice) {
    	//未填写公告结束时间,设置为2099年
    	if(sysNotice.getDend() == null) {
    		Date endDate = new Date(4102358400000L);
    		sysNotice.setDend(endDate);
    	}
    	//默认的公告开始时间
    	if(sysNotice.getDstart() == null) {
    		Date publishDate = sysNotice.getDsend();
    		if(publishDate == null) {
    			Date startDate = new Date();
    			sysNotice.setDstart(startDate);
    			sysNotice.setDsend(startDate);
    		}else {
    			sysNotice.setDstart(publishDate);
    		}
    	}
    	if(sysNotice.getIndocno() != null) {
    		throw new CustomException("base.common.indocno.notnull.error");
    	}
    	//添加发送人员
    	sysNotice.setSsendMembers(adjustSendMember(sysNotice));
    	
    	//添加主表数据
    	insert(sysNotice);
    	
    	//添加内容表
    	SysNoticeDetail noticeDetail = sysNotice.getSysNoticeDetail();
    	noticeDetail.setIlinkno(sysNotice.getIndocno());
    	sysNoticeDetailMapper.insert(noticeDetail);
    	//添加附件表数据
    	if(sysNotice.getSysNoticeAttachments() != null && sysNotice.getSysNoticeAttachments().size() >= 1) {
    		for(SysNoticeAttachment attachment : sysNotice.getSysNoticeAttachments()) {
    			attachment.setIlinkno(sysNotice.getIndocno());
    			sysNoticeAttachmentService.insert(attachment);
    		}
    	}
    	//发送公告人员消息
    	globalEvent.publishEvent(sysNotice, "SysNoticeUserEvent");
    }
    
    @TransactionalException
    public void updateNotice(SysNotice sysNotice) {
    	
    	String nssendmembers = adjustSendMember(sysNotice);
    	SysNotice oldSysNotices = select(sysNotice);
    	String oldSendMembers = oldSysNotices.getSsendMembers();
    	if(!nssendmembers.equals(oldSendMembers)) {
    		//发送人员发生了变化
    		sysNotice.setSsendMembers(nssendmembers);
    	}
    	//更新公告发布时间,调整公告开始时间
    	if(sysNotice.getDsend() != null) {
    		sysNotice.setDstart(sysNotice.getDsend());
    	}
    	update(sysNotice);
    	SysNoticeDetail noticeDetail = sysNotice.getSysNoticeDetail();
    	if(noticeDetail != null) {
	    	if(noticeDetail.getIlinkno() == null) {
	    		throw new CustomException("base.notice.detail.update.error");
	    	}
	    	if(noticeDetail.getIndocno() == null) {
	    		throw new CustomException("base.notice.detail.update.error");
	    	}
	    	List<SysNoticeDetail> sysNoticeDetails = sysNoticeDetailMapper.findByIlinkno(sysNotice.getIndocno());
	    	if(sysNoticeDetails != null && sysNoticeDetails.size() >= 1) {
	    		SysNoticeDetail sysNoticeDetail = sysNoticeDetails.get(0);
	    		if(!noticeDetail.getIndocno().equals(sysNoticeDetail.getIndocno())) {
	    			throw new CustomException("base.notice.detail.update.error");
	    		}
	    	}
	    	sysNoticeDetailMapper.update(noticeDetail);
    	}
    	//修改附件列表
    	List<SysNoticeAttachment> oldSysNoticeAttachments = sysNoticeAttachmentMapper.findByIlinkno(sysNotice.getIndocno());
    	updateAttachment(sysNotice.getSysNoticeAttachments(),oldSysNoticeAttachments,sysNotice);
    	
    	if(!nssendmembers.equals(oldSendMembers)) {
    		//发送人员发生了变化
    		globalEvent.publishEvent(sysNotice, "SysNoticeUserEvent");
    	}
    }
    
    private void updateAttachment(List<SysNoticeAttachment> newSysNoticeAttachments
    		,List<SysNoticeAttachment> oldSysNoticeAttachments,SysNotice sysNotice) {
    	if(newSysNoticeAttachments != null && newSysNoticeAttachments.size()>=1) {
	    	List<SysNoticeAttachment> mixAttach = CollecterMixUtils.tMix(newSysNoticeAttachments, oldSysNoticeAttachments
	    			, "spath");
	    	List<SysNoticeAttachment> delAttech = CollecterMixUtils.fdiffSet(oldSysNoticeAttachments, mixAttach, "spath");
	    	List<SysNoticeAttachment> addAttech = CollecterMixUtils.fdiffSet(newSysNoticeAttachments, mixAttach, "spath");
	    	for(SysNoticeAttachment sysNoticeAttachment : delAttech) {
	    		sysNoticeAttachmentService.delete(sysNoticeAttachment);
	    	}
	    	for(SysNoticeAttachment sysNoticeAttachment : addAttech) {
	    		sysNoticeAttachment.setIlinkno(sysNotice.getIndocno());
	    		sysNoticeAttachmentService.insert(sysNoticeAttachment);
	    	}
    	}
    }
    
    private String adjustSendMember(SysNotice sysNotice) {
    	SysNoticeMembers result = new SysNoticeMembers();
    	//不发送给所有人
    	if(sysNotice.getNoticeAllMember()  == null || sysNotice.getNoticeAllMember() != 1) {
	    	result = sysNoticeHelper.handleSendMember(sysNotice);
    	}else if(sysNotice.getNoticeAllMember()!= null && sysNotice.getNoticeAllMember() == 1) {
    		result.setAll(1);
    	}
    	return JSONObject.toJSONString(result);
    }
}
