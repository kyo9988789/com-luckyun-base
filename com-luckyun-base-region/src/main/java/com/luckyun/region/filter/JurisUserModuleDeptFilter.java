package com.luckyun.region.filter;

import com.luckyun.core.data.filter.PermissionFilter;

public class JurisUserModuleDeptFilter extends PermissionFilter{
	
	private Long iuserid;
	
	private Long imoduleid;
	
	private String sdeptColumn;
	
	/**
	 * 传递用户编号值,模块编号值,部门编号在本次查询中属于的字段,例如`a.ideptid`
	 * @param iuserid 本次查询中iuserid的值; 例如: `1`
	 * @param imoduleid 本次查询中imoduleid的值; 例如: `1`
	 * @param sdeptColumnName 本次查询中idept字段在其它表的显示字段
	 */
	public JurisUserModuleDeptFilter(Long iuserid,Long imoduleid,String sdeptColumnName) {
		this.iuserid = iuserid;
		this.imoduleid = imoduleid;
		this.sdeptColumn = sdeptColumnName;
	}

	@Override
	public String getSql() {
		return "exists ("
				+ "select indocno from sys_user_module_dept jursumdept where jursumdept.imodid = " + this.imoduleid + ""
						+ "	and jursumdept.iuserid= "+ this.iuserid + " and jursumdept.ideptid = " + this.sdeptColumn
				+ ")";
	}

}
