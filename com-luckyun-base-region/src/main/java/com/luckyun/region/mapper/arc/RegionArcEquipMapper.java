package com.luckyun.region.mapper.arc;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.region.model.arc.RegionArcEquip;

@Repository("regionArcEquipMapper")
public interface RegionArcEquipMapper extends BaseMapper<RegionArcEquip>{

	List<RegionArcEquip> readAll(@Param("condition") RegionArcEquip condition);
}
