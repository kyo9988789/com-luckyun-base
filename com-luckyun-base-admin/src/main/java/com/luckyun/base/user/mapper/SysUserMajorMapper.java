package com.luckyun.base.user.mapper;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.user.SysUserMajor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by chengrui on 2019/8/16.
 */
@Repository
public interface SysUserMajorMapper extends BaseMapper<SysUserMajor> {

    public List<SysUserMajor> findMajorByUid(Long uid);

    public List<SysUserMajor> findMajorByMid(Long mid);

    public List<SysUserMajor> findMajorByIds(@Param("condition") SysUserMajor sysUserMajor);

}
