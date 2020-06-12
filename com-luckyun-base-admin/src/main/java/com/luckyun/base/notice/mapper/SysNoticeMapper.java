package com.luckyun.base.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.notice.param.SysNoticeParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.notice.SysNotice;

@Repository
public interface SysNoticeMapper extends BaseMapper<SysNotice>{
    
	/**
	 * 根据条件查询所有的通知公告
	 * @param condition 条件
	 * @return 所有符合条件的公告数据
	 */
	List<SysNotice> findAll(@Param("condition") SysNoticeParam condition);
	
	/**
	 * 根据id查找通知公告数据
	 * @param indocno 主键编号
	 * @return 通知公告数据
	 */
	SysNotice findOne(@Param("indocno") Long indocno,@Param("iuserid") Long iuserid);
	
	/**
	 * 根据条件查询当前用户可以看到的通知列表
	 * @param condition 条件
	 * @return 当前用户看到的属于自己包含自己发起的通知公告
	 */
	List<SysNotice> findNormalMember(@Param("condition") SysNoticeParam condition);
	
	/**
	 * 根据条件查找自己的公告
	 * @param condition 条件
	 * @return 自己可以查看的公告
	 */
	List<SysNotice> findAllByMyNotice(@Param("condition") SysNoticeParam condition);
	
	/**
	 * 通知公告列表数据
	 * @param iuserid 用户编号
	 * @return 未读的通知公告列表
	 */
	List<SysNotice> findNoticeByNotRead(@Param("condition") SysNoticeParam condition);
}

