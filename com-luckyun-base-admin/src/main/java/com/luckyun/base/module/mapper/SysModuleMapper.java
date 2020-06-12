package com.luckyun.base.module.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.module.param.SysModuleParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.module.SysModule;
import com.luckyun.model.module.SysModuleOperate;
import com.luckyun.model.operate.SysOperate;

@Repository
public interface SysModuleMapper extends BaseMapper<SysModule>{

    /**
     * 根据条件获取模块列表
     * @param condition
     * @return
     */
    List<SysModule> findAll(@Param("condition") SysModuleParam condition);
    
    /**
     * 除了传递的条件没有其它的条件
     * @param condition 条件
     * @return 模块列表
     */
    List<SysModule> findAllNoOtherCondition(@Param("condition") SysModuleParam condition);
    
    /**
     * 获取单个模块
     * @param indocno
     * @return
     */
    SysModule findOne(@Param("indocno") Long indocno);
    
    /**
     * 获取模块拥有的操作
     * @param indocno
     * @return
     */
    List<SysOperate> findAllOperates(@Param("indocno") Long indocno);
    
    /**
     * 获取模块自定义操作
     * @param indocno
     * @return
     */
    List<SysModuleOperate> findCustomOperates(@Param("indocno") Long indocno);
    
    /**
     * 获取模块的通用操作关系
     * @param indocno
     * @return
     */
    List<SysModuleOperate> findModuleOperates(@Param("indocno") Long indocno);
    
    /**
     * 查找父级是iparentid的模块
     * @param iparentid
     * @return
     */
    List<SysModule> findByIparentid(@Param("iparentid") Long iparentid);

    /**
     * 根据访问路径获取模块详情
     * @param spathalias
     * @return
     */
    List<SysModule> findByPathalias(@Param("spathalias") String spathalias);

    /**
     * 用户有权限的菜单
     * @param userid
     * @return
     */
    List<SysModule> findByLoginUser(@Param("userid") Long userid);
    
    /**
     * 用户包含隐藏菜单在内的所有菜单
     * @param userid 用户编号
     * @return 模块列表
     */
    List<SysModule> findAllByLoginUser(@Param("userid") Long userid);

    /**
     * 菜单父级菜单查询
     * @param condition
     * @return
     */
    List<SysModule> findUserModule(@Param("condition") SysModuleParam condition);
}
