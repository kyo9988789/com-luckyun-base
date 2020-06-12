package com.luckyun.base.analysis.event;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.luckyun.base.analysis.service.UserVisitService;
import com.luckyun.base.module.service.SysModuleService;
import com.luckyun.core.data.param.UserVisit;
import com.luckyun.core.event.EventQueue;
import com.luckyun.model.module.SysModule;
import com.luckyun.model.user.SysUserVisit;

/**
 * User Module Visit Event Listener（用户模块访问事件处理）
 * 
 * @author omai
 *
 */
@Component
public class UserVisitEvent extends EventQueue {

	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	UserVisitService userVisitService;

	@Autowired
	SysModuleService sysModuleService;


	private final String USER_VISIT_KEY = "USER_VISIT_";
	
	public void event(UserVisit userVisitParam) {

		String key = getKey(userVisitParam.getUserid(), userVisitParam.getPath());

		// 30分钟内只处理一次一样用户行为统计
		if (stringRedisTemplate.opsForValue().get(key) != null)
			return;

		// 模块不存在则不处理
		SysModule sysModule = getModule(userVisitParam.getPath());
		if (sysModule == null)
			return;

		SysUserVisit userVisit = new SysUserVisit();
		userVisit.setIuserid(userVisitParam.getUserid());
		userVisit.setImoduleid(sysModule.getIndocno());

		SysUserVisit userVisitFind = userVisitService.find(userVisit);

		try {
			if (userVisitFind != null) {
				userVisitFind.setIcount(userVisitFind.getIcount() + 1);
				userVisitService.update(userVisitFind);
			} else {
				userVisit.setIcount(1);
				userVisitService.insert(userVisit);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		stringRedisTemplate.opsForValue().set(key, "", 60 * 30, TimeUnit.SECONDS);

	}

	
	/**
	 * 
	 * 取消延时重试
	 * 
	 * @return
	 * @see com.luckyun.core.mq.MQBaseQueue#isRetry()
	 */
	@Override
	protected boolean isRetry() {
		return false;
	}

	/**
	 * 获取访问模块
	 * 
	 * @param path
	 * @return
	 */
	private SysModule getModule(String path) {
		SysModule module = sysModuleService.findByPathalias(path);
		return module;
	}

	/**
	 * 获得缓存key
	 * 
	 * @param uid
	 * @param path
	 * @return
	 */
	private String getKey(Long uid, String path) {

		String key = null;

		key = USER_VISIT_KEY + uid + "_" + path;

		return key;
	}

	@Override
	protected void onMessage(String message) throws Exception {
		UserVisit uservisit = JSON.parseObject(message, UserVisit.class);
		if (uservisit != null) {
			event(uservisit);
		}
	}

}
