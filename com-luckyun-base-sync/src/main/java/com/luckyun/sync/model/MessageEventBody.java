package com.luckyun.sync.model;

import lombok.Data;

@Data
public class MessageEventBody {

	/**
	 * 消息类型,大致包含update,delete,insert
	 */
	private String operate;
	
	/**
	 * 
	 */
	private SuperMiddleEntity superMiddleEntity;
}
