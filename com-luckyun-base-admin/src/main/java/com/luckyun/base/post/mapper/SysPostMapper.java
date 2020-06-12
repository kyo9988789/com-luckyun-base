package com.luckyun.base.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.post.param.SysPostParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.post.SysPost;

@Repository
public interface SysPostMapper extends BaseMapper<SysPost>{

	List<SysPost> findAll(@Param("condition")SysPostParam condition);
	
	SysPost findOne(@Param("indocno") Long indocno);
	
	List<SysPost> findAllByIsort();
	
	List<SysPost> findAllNoIdel(@Param("condition")SysPostParam condition);
}
