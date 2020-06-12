package com.luckyun.base.provider.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.luckyun.base.provider.feign.BaseSysDeptProvider;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.data.filter.PermissionFilter;
import com.luckyun.core.tool.SpringContextHolder;

/**
 * 部门管辖区域通用过滤器
 * @author yangj080
 *
 */
public class JurisDeptFilter extends PermissionFilter{
	
	private AuthInfo authInfo;
	
	private String joinc;
	
	private String[] columnNames;
	
	private String column;
	/**
	 * 部门管辖区域
	 * @param authInfo 登录人session
	 * @param columnNm 表字段名称
	 */
	public JurisDeptFilter(AuthInfo authInfo,String columnNm) {
		this.authInfo = authInfo;
		this.column = columnNm;
	}
	
	/**
	 * 部门管辖区域
	 * @param authInfo 登录人session
	 * @param joinc sql连接字符串 or,and
	 * @param columns 表字段名称
	 */
	public JurisDeptFilter(AuthInfo authInfo,String joinc,String...columns) {
		this.authInfo = authInfo;
		this.joinc = joinc;
		this.columnNames = columns;
	}
	
	@Override
	public String getSql() {
		BaseSysDeptProvider baseSysDeptService = SpringContextHolder.getBean("baseSysDeptProvide");
		String deptIds = baseSysDeptService.getJurisDepts(authInfo);
		if(!StringUtils.isEmpty(deptIds)) {
			if(!StringUtils.isEmpty(joinc) && columnNames!= null && columnNames.length >= 1) {
				List<String> stringList = new ArrayList<String>(columnNames.length); 
				for(String column : columnNames) {
					stringList.add(column + " in ("+ deptIds +") ");
				}
				return String.join(joinc, stringList);
			}else if(column != null){
				return column + " in (" + deptIds + ") ";
			}
		}
		return null;
	}
	
	
}
