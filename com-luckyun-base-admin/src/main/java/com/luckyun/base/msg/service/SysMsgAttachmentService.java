/**
 * Project Name: com-luckyun-base
 * File Name: SysMsgAttachmentService.java
 * Package Name: com.luckyun.base.msg.service
 * Date: 2019年8月9日 下午4:11:29
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.msg.mapper.SysMsgAttachmentMapper;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.model.msg.SysMsgAttachment;

/**
 * 	内部交流附件服务类
 * 
 * @action TODO(请说明该接口根地址)
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 下午4:11:29
 */

@Service
public class SysMsgAttachmentService extends BaseService<SysMsgAttachment>{

	@Autowired
	private SysMsgAttachmentMapper sysMsgAttachmentMapper;

	@Override
	public BaseMapper<SysMsgAttachment> getMapper() {
		return sysMsgAttachmentMapper;
	}

}
