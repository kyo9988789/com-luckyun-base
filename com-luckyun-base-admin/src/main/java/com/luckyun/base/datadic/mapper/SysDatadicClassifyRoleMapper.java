/**
 * Project Name: com-luckyun-base
 * File Name: SysDatadicClassifyRoleMapper.java
 * Package Name: com.luckyun.base.datadicrole.mapper
 * Date: 2019年8月9日 下午2:26:52
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.datadic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.datadic.SysDatadicClassifyRole;

/**
 * 	数据字典/角色关系Mapper
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 下午2:26:52
 */
public interface SysDatadicClassifyRoleMapper extends BaseMapper<SysDatadicClassifyRole>{

	/**
	 * 
	 * 根据字典分类获取角色
	 * 
	 * @return
	 * @author yaoxc
	 * @date 2019年8月19日 下午5:23:59
	 *
	 */
	public List<SysDatadicClassifyRole> findRoleByIclassifyId(@Param(value = "iclassifyid") Long iclassifyid);
	
	/**
	 * 
	 * 根据字典分类获取关系表信息
	 * 
	 * @param iclassifyid
	 * @return
	 * @author yaoxc
	 * @date 2019年8月19日 下午7:42:18
	 *
	 */
	public List<SysDatadicClassifyRole> findByIclassifyId(@Param(value = "iclassifyid") Long iclassifyid);
	
	/**
	 * 
	 *	根据字典分类删除关联表数据
	 * 
	 * @param iclassifyid
	 * @return
	 * @author yaoxc
	 * @date 2019年8月19日 下午7:59:27
	 *
	 */
	public void deleteByIclassifyId(@Param(value = "iclassifyid") Long iclassifyid);
	
}
