package com.luckyun.base.other.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.module.SysModule;
import com.luckyun.model.user.SysUserVisit;

/**
 * 用户访问映射文件
 * @author yangj080
 *
 */
@Repository
public interface SysUserVisitMapper extends BaseMapper<SysUserVisit>{

	/**
	 * 根据用户编号获取对应的模块数据
	 * @param iuserid 用户编号
	 * @return 模块列表
	 */
	List<SysModule> findOftenModule(@Param("iuserid") Long iuserid);
}
