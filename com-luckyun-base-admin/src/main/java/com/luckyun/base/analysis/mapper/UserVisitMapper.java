package com.luckyun.base.analysis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.user.SysUserVisit;

/**
 * User Visit Mapper
 * @author omai
 *
 */
@Mapper()
public interface UserVisitMapper extends BaseMapper<SysUserVisit> {
	@Select("select * from sys_user_visit where iuserid=#{iuserid} and imoduleid=#{imoduleid}")
	public SysUserVisit find(SysUserVisit entity);
}
