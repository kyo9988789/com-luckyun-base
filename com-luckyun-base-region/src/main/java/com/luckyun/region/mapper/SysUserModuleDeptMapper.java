package com.luckyun.region.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.region.model.SysUserModuleDept;

/**
 * 用户模块管辖部门mapper映射
 * @author yangj080
 *
 */
@Repository
public interface SysUserModuleDeptMapper extends BaseMapper<SysUserModuleDept>{

	/**
	 * 根据条件获取用户模块管辖
	 * @param condition 条件
	 * @return 用户模块的管辖列表
	 */
	List<SysUserModuleDept> findAll(@Param("iuserid") Long iuserid, @Param("imodid") Long imodid);
	
	/**
	 * 批量添加用户模块管辖部门信息
	 * @param moduleDepts 模块管辖部门
	 */
	void batchInsertSysUserModuleDept(@Param("moduleDeptList") List<SysUserModuleDept> moduleDepts);
	
	/**
	 * oracle批量添加用户模块管辖部门信息
	 * @param moduleDepts
	 */
	void batchInsertSysUserModuleDeptOracle(@Param("moduleDeptList") List<SysUserModuleDept> moduleDepts);
	
	/**
	 * 批量删除数据
	 * @param indocnoList 主键列表
	 */
	void batchDeleteSysUserModuleDept(@Param("indocnoList") List<Long> indocnoList);
}
