package com.luckyun.region.model;

import java.util.Date;

import com.luckyun.annotation.Describe;
import com.luckyun.model.data.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户模块管辖部门设置
 * @author yangj080
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SysUserModuleDept extends BaseEntity{

	@Describe("编号")
	private Long indocno;
	
	@Describe("用户编号")
	private Long iuserid;
	
	@Describe("模块编号")
	private Long imodid;
	
	@Describe("部门编号")
	private Long ideptid;
	
	@Describe("部门名称")
	private String sdeptName;
	
	@Describe("注册时间")
	private Date dregt;
	
	@Describe("注册人编号")
	private Long sregid;
	
	@Describe("注册人")
	private String sregnm;
	
	@Override
	public boolean __isTrueDel() {
		return true;
	}

}
