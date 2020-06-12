package com.luckyun.base.notice.param;

import com.luckyun.annotation.Describe;

import lombok.Data;

@Data
public class SysNoticeStatis {

	@Describe("总数")
	private Integer total;
	
	@Describe("已读")
	private Integer done;
	
	@Describe("未读")
	private Integer doing;
	
	@Describe("公告编号")
	private Long ilinkno;
}
