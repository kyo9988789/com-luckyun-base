package com.luckyun.base.post.param;

import java.util.List;

import com.luckyun.model.post.SysPostClassify;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysPostClassifyParam extends SysPostClassify{

	private List<SysPostClassify> delList;
	
	/**
	 * 定位节点编号
	 */
	private Long pindocno;
	/**
	 * 拖拽节点编号
	 */
	private Long dindocno;
}
