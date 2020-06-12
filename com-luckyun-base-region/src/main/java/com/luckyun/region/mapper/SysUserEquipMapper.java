package com.luckyun.region.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.region.model.SysUserEquip;

/**
 * 设备管辖区域映射
 * @author yangj080
 *
 */
@Repository
public interface SysUserEquipMapper extends BaseMapper<SysUserEquip>{

	/**
	 * 查找当前用户的管辖部门
	 * @param iuserid 用户编号
	 * @return 管辖部门列表
	 */
	List<SysUserEquip> findAll(@Param("iuserid") Long iuserid);
	
	/**
	 * 批量添加用户部门关系数据
	 * @param depts 用户管辖部门数据
	 */
	void batchInsertSysUserEquip(@Param("deptList") List<SysUserEquip> depts);
	
	/**
	 * 批量添加oracle用户部门关系数据
	 * @param depts 用户管辖部门数据
	 */
	void batchInsertSysUserEquipOracle(@Param("deptList") List<SysUserEquip> depts);
	/**
	 * 批量删除用户管辖部门数据
	 * @param indocnoList 用户管辖部门编号
	 */
	void batchDeleteSysUserEquip(@Param("indocnoList") List<Long> indocnoList);
}
