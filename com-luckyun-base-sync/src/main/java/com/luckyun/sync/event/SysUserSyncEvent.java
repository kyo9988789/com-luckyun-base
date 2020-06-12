package com.luckyun.sync.event;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luckyun.sync.model.SuperMiddleEntity;

@Component
public class SysUserSyncEvent extends BaseDataSyncEvent{
	
	@Autowired
	RabbitAdmin rabbitAdmin;

	@Override
	protected String getRouteKey() {
		return "sys_user_binding";
	}

	@Override
	protected DirectExchange getExchange() {
		DirectExchange ex = new DirectExchange("sys_user_exchange");
		rabbitAdmin.declareExchange(ex);
		return ex;
	}

	@Override
	public void update(SuperMiddleEntity t) {
		System.out.println(t);
	}

	@Override
	public void insert(SuperMiddleEntity t) {
		System.out.println(t);
	}

	@Override
	public int delete(SuperMiddleEntity t) {
		System.out.println(t);
		return 0;
	}

}
