package com.luckyun.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.dept.SysDept;
import com.luckyun.model.user.SysUserCompany;

@Repository
public interface SysUserCompanyMapper extends BaseMapper<SysUserCompany>{

	List<SysUserCompany> findDeptsByUid(@Param("uid") Long uid);
	
	/**
	 * 根据用户编号获取部门列表
	 * @param uid 用户编号
	 * @return 部门列表
	 */
	List<SysDept> findDeptObjByUid(@Param("uid") Long uid);
}
