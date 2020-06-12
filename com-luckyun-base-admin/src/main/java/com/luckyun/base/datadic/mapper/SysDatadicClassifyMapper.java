/**
 * Project Name: com-luckyun-base
 * File Name: SysDatadicClassifyMapper.java
 * Package Name: com.luckyun.base.datadiccla.mapper
 * Date: 2019年8月9日 上午9:20:27
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.datadic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.datadic.param.SysDatadicClassifyParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.datadic.SysDatadicClassify;

/**
 * 	数据字典分类Mapper
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 上午9:20:27
 */

@Repository
public interface SysDatadicClassifyMapper extends BaseMapper<SysDatadicClassify>{

	/**
	 * 
	 * 	获取数据字典分类列表
	 * 
	 * @param condition
	 * @return 数据字典分类集合
	 * @author yaoxc
	 * @date 2019年8月9日 上午10:06:54
	 *
	 */
	public List<SysDatadicClassify> findAll(@Param("condition") SysDatadicClassifyParam condition);
	
	/**
	 * 
	 * <p>Title: findDatadicByParent</p>  
	 * <p>Description: 根据当前别名获取子分类</p>  
	 * @param condition
	 * @return
	 */
	public List<SysDatadicClassify> findDatadicByParent(@Param("condition") SysDatadicClassifyParam condition);
	
	/**
	 * 
	 * 	查询是否存在相同分类别名
	 * 
	 * @param snamealias 分类别名
	 * @return 相同别名的数量
	 * @title TODO(请说明该接口中文名称)
	 * @author yaoxc
	 * @date 2019年8月9日 下午2:06:32
	 *
	 */
	public Integer findNameAliasCount(@Param("snamealias") String snamealias);
	
	/**
	 * 根据id获取字典分类
	 * @param indocno 编号
	 * @return 数据字典分类
	 */
	public SysDatadicClassify findOne(@Param("indocno") Long indocno) ;
	
}
