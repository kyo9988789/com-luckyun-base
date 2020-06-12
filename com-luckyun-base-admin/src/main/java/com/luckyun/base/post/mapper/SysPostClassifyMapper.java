package com.luckyun.base.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.post.param.SysPostClassifyParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.post.SysPostClassify;

@Repository
public interface SysPostClassifyMapper extends BaseMapper<SysPostClassify>{

	/**
	 * 根据条件获取岗位分类列表
	 * @param condition 岗位分类条件
	 * @return 岗位分类列表
	 */
	List<SysPostClassify> findAll(@Param("condition") SysPostClassifyParam condition);
	
	/**
	 * 根据id查找岗位分类
	 * @param indocno 岗位编号
	 * @return 岗位分类对象
	 */
	SysPostClassify findOne(@RequestParam("indocno") Long indocno);
}
