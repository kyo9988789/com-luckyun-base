package com.luckyun.region.model;

import java.util.Date;

import com.luckyun.annotation.Describe;
import com.luckyun.model.data.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备管辖区域设置
 * @author yangj080
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SysUserEquip extends BaseEntity{
	
	@Describe("编号")
	private Long indocno;
	
	@Describe("用户编号")
	private Long iuserid;
	
	@Describe("设备编号")
	private Long iequipid;
	
	@Describe("设备所在位置/1/2/3")
	private String sequipcc;
	
	@Describe("设备名称")
	private String sname;
	
	@Describe("注册时间")
	private Date dregt;
	
	@Describe("注册人编号")
	private Long sregid;
	
	@Describe("注册人名称")
	private String sregnm;
	
	@Override
	public boolean __isTrueDel() {
		return true;
	}

}
