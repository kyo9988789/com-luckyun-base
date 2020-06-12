package com.luckyun.base.company.param;

import java.util.List;

import com.luckyun.model.company.SysCompany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysCompanyParam extends SysCompany{
	/**
	 * 页数
	 */
	private Integer pageno = 1;
	
	/**
	 * 每页记录数
	 */
	private Integer pagesize = 10;
	
	/**
	 * 需要删除的公司对象
	 */
	private List<SysCompany> delLists;
	
	/**
	 * 删除类别逻辑 = logic;物理=phy;
	 */
	private String delType = "logic";
	
	/**
	 * 拖拽的对象
	 */
	private Long dindocno;
	
	/**
	 * 拖拽的定位对象
	 */
	private Long pindocno;
	
	/**
	 * 拖拽的方位
	 */
	private String direction;
	
	/**
	 * 用于公司切换
	 */
	private Long icompanyid;
}
