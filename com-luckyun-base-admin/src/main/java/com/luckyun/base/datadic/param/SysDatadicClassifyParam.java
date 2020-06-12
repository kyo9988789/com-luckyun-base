/**
 * Project Name: com-luckyun-base
 * File Name: SysDatadicClassifyParam.java
 * Package Name: com.luckyun.base.datadiccla.param
 * Date: 2019年8月9日 上午10:05:34
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.datadic.param;

import java.util.List;

import com.luckyun.model.datadic.SysDatadicClassify;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 	数据字典分类传输对象
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 上午10:05:34
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDatadicClassifyParam extends SysDatadicClassify{

	/**
	 * 	需要删除的数据字典对象
	 */
	private List<SysDatadicClassify> delList;
	
	/**
	 * 定位节点
	 */
	private Long pindocno;
	
	/**
	 * 拖拽节点编号
	 */
	private Long dindocno;
	
}
