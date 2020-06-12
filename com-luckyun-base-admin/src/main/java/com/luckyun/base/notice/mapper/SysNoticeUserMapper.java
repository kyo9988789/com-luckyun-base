package com.luckyun.base.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.notice.param.SysNoticeParam;
import com.luckyun.base.notice.param.SysNoticeStatis;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.notice.SysNotice;
import com.luckyun.model.notice.SysNoticeUser;
import com.luckyun.model.user.SysAccount;

@Repository
public interface SysNoticeUserMapper extends BaseMapper<SysNoticeUser>{
	
	/**
	 * 统计公告阅读情况
	 * @param ilinkno 公告主键编号
	 * @return 公告阅读统计结果
	 */
	SysNoticeStatis findStatisByIlinkno(@Param("ilinkno") Long ilinkno);
	
	/**
	 * 根据编号列表获取公告统计
	 * @param ilinknos 公告编号集合
	 * @return 统计的结果列表
	 */
	List<SysNoticeStatis> findStatisByIlinknoList(@Param("ilinknos") List<Long> ilinknos);
	/**
	 * 查找公告为了处理公告发送人员
	 * @param condition 查找公告的条件
	 * @return 通知公告列表
	 */
	List<SysNotice> findNoticeByTimeForUser(@Param("condition") SysNoticeParam condition);
	
	/**
	 * 根据外键删除对应的用户关系数据
	 * @param ilinkno 外键数据
	 */
	void deleteByIlinkno(@Param("ilinkno") Long ilinkno);
	
	/**
	 * 根据公告主键与已读未读获取对应的用户列表数据
	 * @param condition 过滤条件
	 * @return 用户列表
	 */
	List<SysAccount> findUserByReadDetail(@Param("condition") SysNoticeParam condition);
}

