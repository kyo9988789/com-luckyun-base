package com.luckyun.base.preset.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.preset.param.SysUserPresetParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.preset.SysUserPreset;

@Repository
public interface SysUserPresetMapper extends BaseMapper<SysUserPreset>{

	/**
	 * 根据条件查找配置
	 * @param condition 条件信息
	 * @return 配置列表
	 */
	List<SysUserPreset> findAll(@Param("condition") SysUserPresetParam condition);
}
