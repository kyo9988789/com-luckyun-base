package com.luckyun.base.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.notice.SysNoticeAttachment;

@Repository
public interface SysNoticeAttachmentMapper extends BaseMapper<SysNoticeAttachment>{
	
	/**
	 * 根据外键关系查找通知公告的相关附件
	 * @param ilinkno 主表外键
	 * @return 通知公告附件列表
	 */
	List<SysNoticeAttachment> findByIlinkno(@Param("ilinkno") Long ilinkno);
}

