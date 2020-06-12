/**
 * Project Name: com-luckyun-base
 * File Name: SysNoticeReplyMapper.java
 * Package Name: com.luckyun.base.notice.mapper
 * Date: 2020年1月3日 下午3:03:54
 * Copyright (c) 2020, luckyun All Rights Reserved.
 */
package com.luckyun.base.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.notice.SysNoticeReply;

/**
 * @author Jackie
 *
 */
@Repository
public interface SysNoticeReplyMapper extends BaseMapper<SysNoticeReply>{
    
    SysNoticeReply findOne(@Param("ireplyid") Long ireplyid);
    
    List<SysNoticeReply> findAllMainReply(@Param("ilinkno") Long ilinkno);
    
    List<SysNoticeReply> findAllReply(@Param("ilinkno") Long ilinkno);
}
