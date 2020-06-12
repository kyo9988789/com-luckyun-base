package com.luckyun.base.logger.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.luckyun.core.data.entity.LogEntity;

@Mapper()
public interface LoggerProviderMapper {

	@Select("select * from logging_event where formatted_message like 'DBOPT%' and arg1 = #{docid} "
			+ "and arg0 like #{spath} order by timestmp desc")
	public List<LogEntity> findLoggerByCondition(@Param("docid") Long docid,@Param("spath") String spath);
	
	@Select("select * from logging_event where formatted_message like 'DBOPT%' "
			+ "and arg0 like #{spath} order by timestmp desc")
	public List<LogEntity> findMainLoggerByCondition(@Param("spath") String spath);
	
}
