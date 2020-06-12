package com.luckyun.base.post.param;

import java.util.List;

import com.luckyun.model.post.SysPost;
import com.luckyun.model.user.SysAccount;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysPostParam extends SysPost{

	/**
	 * 页数
	 */
	private Integer pageno = 1;
	
	/**
	 * 每页记录数
	 */
	private Integer pagesize = 10;
	
	/**
	 * 需要删除的岗位对象
	 */
	private List<SysPost> delList;
	
	/**
	 * 岗位下用户对象
	 */
	private List<SysAccount> userLists;
	
	/**
	 * 删除类别逻辑 = logic;物理=phy;
	 */
	private String delType = "logic";
	
	/**
	 * 定位节点编号
	 */
	private Long pindocno;
	
	/**
	 * 拖拽节点编号
	 */
	private Long dindocno;
	
}
