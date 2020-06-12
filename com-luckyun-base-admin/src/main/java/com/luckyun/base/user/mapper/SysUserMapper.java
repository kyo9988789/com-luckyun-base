package com.luckyun.base.user.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.user.param.SysUserParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.user.SysAccount;
import com.luckyun.model.user.SysUser;

/**
 * 用户管理主表持久化对象
 * @author yangj080
 *
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysAccount>{

	/**
	 * 获去所有用户列表数据
	 * @param condition 条件
	 * @return 用户列表
	 */
	List<SysAccount> findAll(@Param("condition") SysUserParam condition);
	
	/**
	 * 获取用户详情数据
	 * @param indocno 用户编号
	 * @return 用户详情
	 */
	SysAccount findOne(@Param("indocno") Long indocno);
	
	/**
	 * 根据登录名称获取用户对象
	 * @param sloginid 登录名称
	 * @return 登录用户
	 */
	SysAccount findBySloginid(@Param("sloginid") String sloginid);
	
	/**
	 * 根据用户编号获取用户详情对象
	 * @param uid 用户编号
	 * @return 用户详情对象
	 */
	SysUser findInfoByIuserid(@Param("uid") Long uid);

	/**
	 * 获取已分配专业的用户信息
	 * @param condition
	 * @return
	 */
	List<SysAccount> findUserMajor(@Param("condition") SysUserParam condition);
	
	/**
	 * 为bpm服务提供用户
	 * @param sloginid 登录名称
	 * @return 提供的用户数据
	 */
	List<SysAccount> findUserForBpm(@Param("sloginid") String sloginid);
}
