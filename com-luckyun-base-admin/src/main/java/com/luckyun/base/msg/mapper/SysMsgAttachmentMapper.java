/**
 * Project Name: com-luckyun-base
 * File Name: SysMsgAttachmentMapper.java
 * Package Name: com.luckyun.base.msg.mapper
 * Date: 2019年8月9日 下午3:49:21
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.msg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.msg.SysMsgAttachment;

/**
 * 	内部交流附件Mapper
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 下午3:49:21
 */

@Repository
public interface SysMsgAttachmentMapper extends BaseMapper<SysMsgAttachment>{

	/**
	 * 根据消息主键查找附件
	 * @param ilinkno
	 * @return
	 */
	public List<SysMsgAttachment> findByIlinkno(@Param("ilinkno") Long ilinkno);
	
}
