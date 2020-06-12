package com.luckyun.base.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.notice.SysNoticeDetail;

@Repository
public interface SysNoticeDetailMapper extends BaseMapper<SysNoticeDetail>{
	
	/**
	 * 根据主表外键查找详情对象
	 * @param ilinkno 主表外键
	 * @return 详情对象.
	 */
	List<SysNoticeDetail> findByIlinkno(@Param("ilinkno") Long ilinkno);
}

