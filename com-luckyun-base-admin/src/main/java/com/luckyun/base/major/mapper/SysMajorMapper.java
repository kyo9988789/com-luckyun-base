package com.luckyun.base.major.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.major.param.SysMajorParam;
import com.luckyun.base.user.param.SysUserParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.major.SysMajor;

/**
 * 用户专业持久层
 * Created by chengrui on 2019/8/16.
 */
@Repository
public interface SysMajorMapper extends BaseMapper<SysMajor> {

    List<SysMajor> findAll(@Param("condition") SysMajorParam condition);

    SysMajor findOne(@Param("indocno") Long indocno);

    /**
     * 根据用户查询用户所属专业
     * @param condition
     * @return
     */
    List<SysMajor> findMajorByUserMajor(@Param("condition") SysUserParam condition);
    
    /**
     * 	根据用户主键获取所属专业
     * @param iuserid
     * @return
     */
    List<SysMajor> findMajorByIuserid(@Param("iuserid") Long iuserid);
}
