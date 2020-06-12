package com.luckyun.region.model;

import java.util.Date;

import com.luckyun.annotation.Describe;
import com.luckyun.model.data.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管辖部门功能
 * @author yangj080
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SysUserDept extends BaseEntity{
	
	public SysUserDept() {}
	
	public SysUserDept(Long ideptid,String deptName) {
		this.ideptid = ideptid;
		this.sdeptName = deptName;
	}

	@Describe("编号")
	private Long indocno;
	
	@Describe("用户编号")
	private Long iuserid;
	
	@Describe("部门编号")
	private Long ideptid;
	
	@Describe("部门名称")
	private String sdeptName;
	
	@Describe("创建时间")
	private Date dregt;
	
	@Describe("创建人编号")
	private Long sregid;
	
	@Describe("创建人名称")
	private String sregnm;
	
	@Override
	public boolean __isTrueDel() {
		return true;
	}

}
