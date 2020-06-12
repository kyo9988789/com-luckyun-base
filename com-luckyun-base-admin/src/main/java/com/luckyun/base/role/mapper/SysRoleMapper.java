package com.luckyun.base.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.role.param.SysRoleParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.role.SysRole;

/**
 * 角色持久化映射文件
 * @author yangj080
 *
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole>{

	List<SysRole> findAll(@Param("condition") SysRoleParam condition);
	
	/**
	 * 查找角色并且带上需要查询的人员数量
	 * @param condition 查询条件
	 * @return 角色列表集合
	 */
	List<SysRole> findAllStatis(@Param("condition") SysRoleParam condition);
	/**
	 * 获取单条角色数据
	 * @param indocno 单据编号
	 * @return 角色数据
	 */
	SysRole findOne(@Param("indocno") Long indocno);
	
	/**
	 * 根据条件查找角色数据
	 * @param condition 条件对象
	 * @return 角色列表数据包含被删除的数据
	 */
	List<SysRole> findAllNotDel(@Param("condition") SysRoleParam condition);
	
	/**
	 * 获取人员的角色列表
	 * @param iuserid 用户编号
	 * @return 角色列表
	 */
	List<SysRole> findRoleByUid(@Param("iuserid") Long iuserid);
	
	/**
	 * 查询当前角色下面的用户数量
	 * @param iroleid 角色编号
	 * @return 用户数量
	 */
	Long findCountUserByRole(@Param("iroleid") Long iroleid);
}
