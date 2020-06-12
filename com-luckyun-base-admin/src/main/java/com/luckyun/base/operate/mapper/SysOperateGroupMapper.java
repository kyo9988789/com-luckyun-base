package com.luckyun.base.operate.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.operate.param.SysOperateGroupParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.operate.SysOperateGroup;

@Repository
public interface SysOperateGroupMapper extends BaseMapper<SysOperateGroup>{

	List<SysOperateGroup> findAll(@Param("condition") SysOperateGroupParam condition);
	SysOperateGroup findOne(@Param("indocno") Long indocno);
	Long findTotal();
	List<SysOperateGroup> findAllByIsort();

}
