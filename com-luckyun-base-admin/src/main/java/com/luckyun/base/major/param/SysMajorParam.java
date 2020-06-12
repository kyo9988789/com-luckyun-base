package com.luckyun.base.major.param;

import java.util.List;

import com.luckyun.model.major.SysMajor;
import com.luckyun.model.user.SysUserMajor;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by chengrui on 2019/8/16.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysMajorParam extends SysMajor {

    /**
     * 用户添加所属专业
     */
    private List<SysMajor> sysMajors;

    /**
     * 用户分配专业
     */
    private List<SysUserMajor> sysUserMajor;
}
