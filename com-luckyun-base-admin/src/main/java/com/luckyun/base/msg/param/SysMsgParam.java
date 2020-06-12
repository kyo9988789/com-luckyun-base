/**
 * Project Name: com-luckyun-base
 * File Name: SysMsgParam.java
 * Package Name: com.luckyun.base.msg.param
 * Date: 2019年8月9日 下午4:06:17
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.msg.param;

import java.util.List;

import com.luckyun.annotation.Describe;
import com.luckyun.annotation.Virtual;
import com.luckyun.model.msg.SysMsg;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 	内部交流传输对象
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 下午4:06:17
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysMsgParam extends SysMsg{

	/**
	 * 	需删除的内部交流传输对象
	 */
	private List<SysMsg> delList;
	
	@Describe("已发送")
	@Virtual
	private Integer isend;
	
	@Describe("已读次数")
    @Virtual
    private Integer iread;
	
	@Describe("是否聚合 1聚合 0不聚合")
	@Virtual
	private Integer ol;
	
}
