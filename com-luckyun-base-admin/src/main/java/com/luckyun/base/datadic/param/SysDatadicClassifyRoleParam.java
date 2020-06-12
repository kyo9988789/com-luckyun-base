/**
 * Project Name: com-luckyun-base
 * File Name: SysDatadicClassifyRoleParam.java
 * Package Name: com.luckyun.base.datadicrole.param
 * Date: 2019年8月9日 下午2:35:07
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.datadic.param;

import java.util.List;

import com.luckyun.model.datadic.SysDatadicClassifyRole;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *	 数据字典/角色关系传输对象
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 下午2:35:07
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDatadicClassifyRoleParam extends SysDatadicClassifyRole{

	
	/**
	 * 	需删除的数据字典/角色关系对象
	 */
	private List<SysDatadicClassifyRole> delList;
	
	/**
	 * 	需增加的数据字典/角色关系对象
	 */
	private List<SysDatadicClassifyRole> roleList;
	
}
