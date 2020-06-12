package com.luckyun.base.dept.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.dept.param.SysDeptParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.dept.SysDept;

@Repository
public interface SysDeptMapper extends BaseMapper<SysDept>{
    
	List<SysDept> findAll(@Param("condition") SysDeptParam condition);
	
	/**
	 * 对外提供部门数据
	 * @param condition 条件
	 * @return 符合条件的部门列表数据
	 */
	List<SysDept> findFeignAll(@Param("condition") SysDeptParam condition);
	
	SysDept findOne(@Param("indocno") Long indocno);
	
	/**
	 * 根据父级id和用户id查询所有管辖部门
	 * @param iparentid
	 * @param iuserid
	 * @return
	 */
	List<SysDept> findDeptsByIparentid(@Param("iparentid") Long iparentid);
	
	/***
	 * 父级下面的子集部门
	 * @param iparentid 父级节点
	 * @return 子集部门列表
	 */
	List<SysDept> findDeptsByIparentidEquips(@Param("iparentid") Long iparentid);
	
	/**
	 * 获取用户的所有管辖部门(包括子集部门)
	 * @param iuserid 用户编号
	 * @return 部门列表
	 */
	List<SysDept> findDeptJuris(@Param("iuserid") Long iuserid);
	
	List<SysDept> findDeptByOtherCondition(@Param("condition") SysDeptParam condition);
}

