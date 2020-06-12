package com.luckyun.base.module.param;

import java.util.List;

import com.luckyun.model.module.SysModule;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysModuleParam extends SysModule{
    
    /**
     * 需要删除的消息对象
     */
    private List<SysModule> delList;


    /**
     * 需要显示的模块编号列表
     */
    private List<Long> moduleIds;
    
    /**
     * 拖拽的对象
     */
    private Long dindocno;
    
    /**
     * 拖拽的定位对象
     */
    private Long pindocno;

}
