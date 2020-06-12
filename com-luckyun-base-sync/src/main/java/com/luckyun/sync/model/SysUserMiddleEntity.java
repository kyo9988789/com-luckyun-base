package com.luckyun.sync.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class SysUserMiddleEntity extends SuperMiddleEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1087863871876218199L;
	
	/**
	 * 登录
	 */
	private String sloginid;
	
	/**
	 * 密码
	 */
	private String spassword;
	
	/**
	 * 主键
	 */
	private Long indocno;

}
