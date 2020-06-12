package com.luckyun.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.user.param.SysUserParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.role.SysRole;
import com.luckyun.model.user.SysUserRole;

@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole>{

	List<SysUserRole> findRoleByUid(@Param("uid") Long uid);
	
	List<SysRole> findRoleByUser(@Param("uid") Long uid);
	
	List<SysRole> findRoleByUserRole(@Param("condition") SysUserParam condition);
	
	List<SysUserRole> findRoleByCondition(@Param("condition") SysUserParam condition);
}
