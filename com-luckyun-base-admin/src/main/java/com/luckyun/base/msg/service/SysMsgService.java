/**
 * Project Name: com-luckyun-base
 * File Name: SysMsgService.java
 * Package Name: com.luckyun.base.msg.service
 * Date: 2019年8月9日 下午4:11:14
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.msg.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.luckyun.base.msg.mapper.SysMsgAttachmentMapper;
import com.luckyun.base.msg.mapper.SysMsgDetailMapper;
import com.luckyun.base.msg.mapper.SysMsgMapper;
import com.luckyun.base.msg.mapper.SysMsgUserMapper;
import com.luckyun.base.msg.param.SysMsgParam;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.event.GlobalEvent;
import com.luckyun.core.exception.CustomException;
import com.luckyun.core.tool.OssPathHelperUtils;
import com.luckyun.model.msg.SysMsg;
import com.luckyun.model.msg.SysMsgAttachment;
import com.luckyun.model.msg.SysMsgDetail;
import com.luckyun.model.msg.SysMsgUser;
import com.luckyun.model.user.SysAccount;

/**
 * 	内部交流服务类
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 下午4:11:14
 */

@Service
public class SysMsgService extends BaseService<SysMsg>{

	@Autowired
	private SysMsgMapper sysMsgMapper;
	
	@Autowired
	private SysMsgDetailMapper sysMsgDetailMapper;
	
	@Autowired
	private  SysMsgUserMapper sysMsgUserMapper;
	
	@Autowired
	private SysMsgAttachmentMapper sysMsgAttachmentMapper;
	
	@Autowired
	private SysMsgAttachmentService sysMsgAttachmentService;
	
	@Autowired
    private OssPathHelperUtils ossPathHelperUtils;
	
	@Autowired
    private GlobalEvent globalEvent;
	
	@Override
    public BaseMapper<SysMsg> getMapper() {
        return sysMsgMapper;
    }
	
	/**
	 * 
	 * 	获取所有内部交流数据
	 * 
	 * @param condition
	 * @return 内部交流集合
	 * @title TODO(请说明该接口中文名称)
	 * @author yaoxc
	 * @date 2019年8月9日 下午4:54:08
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<SysMsg> findAllMyMsg(SysMsgParam condition) {
	    List<SysMsg> list = new ArrayList<SysMsg>();
        AuthInfo authInfo = getAuthInfo();
        if(condition.getIsend()==1) {
        	list = sysMsgMapper.findAllMySendMsg(condition,authInfo.getIndocno());
        }else {
        	list = sysMsgMapper.findAllMyMsg(
        			condition,authInfo.getIndocno(),new Date());
        	if(list instanceof Page) {
        		setMsgReadState(((Page<SysMsg>)list).getContent());
        	}else {
        		setMsgReadState(list);
        	}
        }
		return list;
	}
	
	private void setMsgReadState(List<SysMsg> sysMsgs) {
		for(SysMsg msg : sysMsgs) {
    		if(msg.getIread() != null && msg.getIread() >= 1) {
    			msg.setIread(1);msg.setSread("已读");
    		}else {
    			msg.setIread(0);msg.setSread("未读");
    		}
    	}
	}
	
	public List<SysMsg> findAllMyMsgGroup(SysMsgParam condition){
	    List<SysMsg> list = new ArrayList<SysMsg>();
        AuthInfo authInfo = getAuthInfo();
        list = sysMsgMapper.findAllMyMsgGroup(condition,authInfo.getIndocno(),new Date());
        return list;
	}
	
	public SysMsg findOne(Long indocno) {
	    AuthInfo authInfo = getAuthInfo();
	    Date nowDate = new Date();
        SysMsg result = sysMsgMapper.findOne(indocno,authInfo.getIndocno(),nowDate);
        List<SysMsgAttachment> attachments = sysMsgAttachmentMapper.findByIlinkno(indocno);
        for(SysMsgAttachment attachment : attachments) {
            attachment.setSpath(ossPathHelperUtils.generateDownloadFile(authInfo.getSloginid()
                    , result.getSprojectno(), attachment.getSpath(), attachment.getSname()));
            attachment.setNpreviewPath(ossPathHelperUtils.generatePdfPreview(authInfo.getSloginid()
                    , result.getSprojectno(), attachment.getSpath(), attachment.getSname()));
        }
        result.setSysMsgAttachments(attachments);
        List<SysMsgUser> sysMsgUserList = sysMsgUserMapper.findByIlinknoAndUser(indocno, authInfo.getIndocno());
        for(SysMsgUser sysMsgUser:sysMsgUserList) {
            if(sysMsgUser.getDread()==null) {
                sysMsgUser.setDread(nowDate);
            }
            sysMsgUser.setIread(sysMsgUser.getIread()+1);
            sysMsgUserMapper.update(sysMsgUser);
        }
        return result;
	}
	
	@TransactionalException
    public void add(SysMsgParam sysMsgParam) {
	    if(sysMsgParam.getIndocno() != null) {
	        throw new CustomException("base.common.indocno.notnull.error");
	    }
        if(sysMsgParam.getDend() == null) {
            Date endDate = new Date(4102358400000L);
            sysMsgParam.setDend(endDate);
        }
        if(sysMsgParam.getDstart() == null) {
            Date startDate = new Date();
            sysMsgParam.setDstart(startDate);
            sysMsgParam.setDsend(startDate);
        }
        if(!StringUtils.isEmpty(sysMsgParam.getScontent())) {
            sysMsgParam.setImsgType(1);
        }
        //添加主表数据
        insert(sysMsgParam);
        //添加内容表
        SysMsgDetail msgDetail = new SysMsgDetail();
        msgDetail.setIlinkno(sysMsgParam.getIndocno());
        msgDetail.setScontent(sysMsgParam.getScontent());
        sysMsgDetailMapper.insert(msgDetail);
        //添加附件表数据
        if(sysMsgParam.getSysMsgAttachments() != null && sysMsgParam.getSysMsgAttachments().size() > 0) {
            for(SysMsgAttachment attachment : sysMsgParam.getSysMsgAttachments()) {
                attachment.setIlinkno(sysMsgParam.getIndocno());
                sysMsgAttachmentService.insert(attachment);
            }
        }
        for(SysAccount sysAccount:sysMsgParam.getSendAccounts()) {
            SysMsgUser sysMsgUser = new SysMsgUser();
            sysMsgUser.setIlinkno(sysMsgParam.getIndocno());
            sysMsgUser.setIuserid(sysAccount.getIndocno());
            sysMsgUserMapper.insert(sysMsgUser);
        }
        globalEvent.publishEvent(sysMsgParam, "SysMsgEvent");
    }
	
	@TransactionalException
	public void deleteMsg(SysMsgParam sysMsgParam) {
	    if(sysMsgParam.getDelList()!=null && sysMsgParam.getDelList().size()>0) {
	        AuthInfo authInfo = getAuthInfo();
	        for(SysMsg sysMsg:sysMsgParam.getDelList()) {
	            SysMsg message = sysMsgMapper.findOneBySendUser(sysMsg.getIndocno(),authInfo.getIndocno());
	            if(message!=null) {
	                delete(message);
	            }else {
	                List<SysMsgUser> sysMsgUserList = sysMsgUserMapper.findByIlinknoAndUser(sysMsg.getIndocno(),authInfo.getIndocno());
	                for(SysMsgUser msgUser: sysMsgUserList) {
	                    if(msgUser!=null) {
	                        msgUser.setIdel(1);
	                        sysMsgUserMapper.update(msgUser);
	                    }
	                }
	            }
	        }
	    }
	}
	
	@TransactionalException
	public void readMsg(Long indocno) {
	    AuthInfo authInfo = getAuthInfo();
        Date nowDate = new Date();
        List<SysMsgUser> sysMsgUserList = sysMsgUserMapper.findByIlinknoAndUser(indocno, authInfo.getIndocno());
        for(SysMsgUser sysMsgUser:sysMsgUserList) {
            if(sysMsgUser.getDread()==null) {
                sysMsgUser.setDread(nowDate);
            }
            sysMsgUser.setIread(sysMsgUser.getIread()+1);
            sysMsgUserMapper.update(sysMsgUser);
        }
	}
	
	@TransactionalException
	public void readFeignMsg(Long indocno,Long iuserid) {
        Date nowDate = new Date();
        List<SysMsgUser> sysMsgUserList = sysMsgUserMapper.findByIlinknoAndUser(indocno,iuserid);
        for(SysMsgUser sysMsgUser:sysMsgUserList) {
            if(sysMsgUser.getDread()==null) {
                sysMsgUser.setDread(nowDate);
            }
            sysMsgUser.setIread(sysMsgUser.getIread()+1);
            sysMsgUserMapper.update(sysMsgUser);
        }
	}
	
	public List<SysMsgUser> findMembers(Long ilinkno,Integer iread){
	    return sysMsgUserMapper.findMembers(ilinkno,iread);
	}
	
	/**
	 * 批量阅读消息
	 * @param indocno 消息编号
	 */
	public void batchRead(Long indocno) {
		sysMsgMapper.updateByIlinknoRead(indocno);
	}
}
