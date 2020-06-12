package com.luckyun.base.notice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.notice.mapper.SysNoticeReplyAttachmentMapper;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.model.notice.SysNoticeReplyAttachment;

@Service
public class SysNoticeReplyAttachmentService extends BaseService<SysNoticeReplyAttachment>{

	@Autowired
	private SysNoticeReplyAttachmentMapper sysNoticeReplyAttachmentMapper;
	
	@Override
	public BaseMapper<SysNoticeReplyAttachment> getMapper() {
		return sysNoticeReplyAttachmentMapper;
	}

	
}
