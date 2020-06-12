package com.luckyun.base.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.role.SysRoleType;

/**
 * 角色分类
 * @author yangj080
 *
 */
@Repository
public interface SysRoleTypeMapper extends BaseMapper<SysRoleType>{

	List<SysRoleType> findAll();
	
	/**
	 * 根据编号获取角色分类数据
	 * @param indocno 编号
	 * @return 角色分类
	 */
	SysRoleType findOne(@Param("indocno") Long indocno);
	
	/**
	 * 获取所有角色类别数据
	 * @return 角色分类数据
	 */
	List<SysRoleType> findAllAndIdel();
}
