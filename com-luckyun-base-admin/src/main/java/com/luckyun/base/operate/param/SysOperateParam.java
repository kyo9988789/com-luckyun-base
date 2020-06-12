package com.luckyun.base.operate.param;

import java.util.List;

import com.luckyun.model.operate.SysOperate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysOperateParam extends SysOperate{
	/**
	 * 需要删除的操作对象
	 */
	private List<SysOperate> delList;
	
	/**
     * 拖拽的对象
     */
    private Long dindocno;
    
    /**
     * 拖拽的定位对象
     */
    private Long pindocno;

}
