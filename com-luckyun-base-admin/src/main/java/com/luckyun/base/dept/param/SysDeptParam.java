package com.luckyun.base.dept.param;

import java.util.List;

import com.luckyun.model.dept.SysDept;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysDeptParam extends SysDept{
    
    /**
     * 部门下协的岗位
     */
    private List<Long> sysPosts;
    /**
     * 需要删除的部门对象
     */
    private List<SysDept> delList;
    
    /**
     * 拖拽的对象
     */
    private Long dindocno;
    
    /**
     * 拖拽的定位对象
     */
    private Long pindocno;
    
    /**
     * 查找管辖部门的部门列表
     */
    private List<SysDept> jurisDepts;
    
    /**
     * 主键列表
     */
    private List<Long> indocnos;

    /**
     * 层级区间
     */
    private Long ilevelstart;
    private Long ilevelend;

}
