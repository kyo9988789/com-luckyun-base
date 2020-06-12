package com.luckyun.sync.event;

import com.alibaba.fastjson.JSONObject;
import com.luckyun.core.mq.MQBaseQueue;
import com.luckyun.sync.model.MessageEventBody;
import com.luckyun.sync.model.SuperMiddleEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据导入队列
 * @author yangj080
 *
 */
@Slf4j
public abstract class BaseDataSyncEvent extends MQBaseQueue {

	public abstract void update(SuperMiddleEntity t);

	public abstract void insert(SuperMiddleEntity t);

	public abstract int delete(SuperMiddleEntity t);

	@Override
	protected void onMessage(String message) throws Exception {
		try {
			MessageEventBody messageEventBody = JSONObject.parseObject(message, MessageEventBody.class);
			if ("insert".equals(messageEventBody.getOperate())) {
				insert(messageEventBody.getSuperMiddleEntity());
			} else if ("update".equals(messageEventBody.getOperate())) {
				update(messageEventBody.getSuperMiddleEntity());
			} else if ("delete".equals(messageEventBody.getOperate())) {
				delete(messageEventBody.getSuperMiddleEntity());
			} else {
				log.error("未找到当前的对数据的操作类型,请检查消息数据," + message);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
