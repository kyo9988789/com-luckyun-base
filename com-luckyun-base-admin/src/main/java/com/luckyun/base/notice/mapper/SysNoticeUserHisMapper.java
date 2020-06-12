package com.luckyun.base.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.notice.SysNoticeUserHis;

@Repository
public interface SysNoticeUserHisMapper extends BaseMapper<SysNoticeUserHis>{

	/**
	 * 根据外键查找公告阅读历史
	 * @param ilinkno 外键编号
	 * @return 公告阅读历史
	 */
	List<SysNoticeUserHis> findByIlinkno(@Param("ilinkno") Long ilinkno);
	
	/**
	 * 查找公告阅读情况
	 * @param ilinkno 外键关系主键
	 * @param iuserid 用户编号
	 * @return 公告阅读情况
	 */
	SysNoticeUserHis findByIlinknoAndIuserid(@Param("ilinkno") Long ilinkno,@Param("iuserid") Long iuserid);
	
	/**
	 * 查找用户的公告阅读情况
	 * @param iuserid 用户编号
	 * @return 公告阅读情况列表
	 */
	List<SysNoticeUserHis> findByIuserid(@Param("iuserid") Long iuserid);
}
