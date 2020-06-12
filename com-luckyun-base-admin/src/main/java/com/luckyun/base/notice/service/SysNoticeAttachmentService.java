package com.luckyun.base.notice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.notice.mapper.SysNoticeAttachmentMapper;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.model.notice.SysNoticeAttachment;

@Service
public class SysNoticeAttachmentService extends BaseService<SysNoticeAttachment>{

	@Autowired
	private SysNoticeAttachmentMapper sysNoticeAttachmentMapper;
	
	@Override
	public BaseMapper<SysNoticeAttachment> getMapper() {
		return sysNoticeAttachmentMapper;
	}

	
}
