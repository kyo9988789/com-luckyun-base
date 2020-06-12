package com.luckyun.base.appversion.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.appversion.param.SysAppParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.appversion.SysApp;

@Repository
public interface SysAppMapper extends BaseMapper<SysApp>{

	List<SysApp> findAll(@Param("condition") SysAppParam condition);
	
	List<SysApp> findLastVersion(@Param("iversion")Integer iversion
			,@Param("saliasname")String saliasname,@Param("stype") String stype);
}
