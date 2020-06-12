package com.luckyun.base.datadic.param;

import java.util.List;

import com.luckyun.model.datadic.SysDatadic;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 	数据字典传输参数
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月8日 下午3:47:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDatadicParam extends SysDatadic{
	
	/**
	 * 页数
	 */
	private Integer pageno = 1;
	
	/**
	 * 每页记录数
	 */
	private Integer pagesize = 10;
	
	/**
	 * 	需删除数据字典对象
	 */
	private List<SysDatadic> delList;

	/**
	 * 	字典别名
	 */
	private String snamealias;
	
	/**
	 * 定位节点编号
	 */
	private Long pindocno;
	
	/**
	 * 拖拽节点编号
	 */
	private Long dindocno;
	
}
