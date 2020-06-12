package com.luckyun.sync.event;

import java.util.Map;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class SyncDataEvent {

	@Order(89)
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Map<String, BaseDataSyncEvent> bpmEventQueues = event.getApplicationContext().getBeansOfType(BaseDataSyncEvent.class);
		if (bpmEventQueues.size() > 0) {
			for (String key : bpmEventQueues.keySet()) {
				BaseDataSyncEvent queue = bpmEventQueues.get(key);
				queue.initQueue();
			}
		}
	}
}
