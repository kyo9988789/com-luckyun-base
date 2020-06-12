package com.luckyun.base.collect.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.collect.param.SysCollectParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.collect.SysCollect;

/**
 * 
 * 	收藏管理Mapper
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月7日 下午3:42:57
 */

@Repository
public interface SysCollectMapper extends BaseMapper<SysCollect>{

	/**
	 * 
	 * 	获取收藏列表
	 * 
	 * @return
	 * @author yaoxc
	 * @date 2019年8月7日 下午3:44:15
	 *
	 */
	public List<SysCollect> findAll(@Param("condition") SysCollectParam condition);
	
	/**
	 * 根据用户id和模块id找到收藏
	 * @param iuserid
	 * @param imoduleid
	 * @return
	 */
	public SysCollect findByImoduleid(@Param("iuserid") Long iuserid,@Param("imoduleid") Long imoduleid);
	
}
