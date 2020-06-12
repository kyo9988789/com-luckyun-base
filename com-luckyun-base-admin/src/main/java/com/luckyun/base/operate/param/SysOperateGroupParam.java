package com.luckyun.base.operate.param;

import java.util.List;

import com.luckyun.model.operate.SysOperateGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysOperateGroupParam extends SysOperateGroup{
	/**
	 * 需要删除的模块对象
	 */
	private List<SysOperateGroup> delList;
	
	/**
     * 拖拽的对象
     */
    private Long dindocno;
    
    /**
     * 拖拽的定位对象
     */
    private Long pindocno;
	
}
