package com.luckyun.base.collect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.collect.mapper.SysCollectMapper;
import com.luckyun.base.collect.param.SysCollectParam;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.exception.CustomException;
import com.luckyun.model.collect.SysCollect;

/**
 * 
 * 	收藏功能服务层
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月7日 下午5:24:29
 */

@Service
public class SysCollectService extends BaseService<SysCollect>{

	@Autowired
	private SysCollectMapper sysCollectMapper;
	
	/**
	 * 
	 * 	获取收藏列表
	 * 
	 * @param condition
	 * @return
	 * @author yaoxc
	 * @date 2019年8月7日 下午5:24:05
	 *
	 */
	public List<SysCollect> findAll(SysCollectParam condition) {
		condition.setIuserid(getAuthInfo().getIndocno());
		return this.sysCollectMapper.findAll(condition);
	}
	
	/**
	 * 
	 * 	添加收藏
	 * 
	 * @param sysCollect
	 * @return 当前收藏对象
	 * @author yaoxc
	 * @date 2019年8月7日 下午5:24:49
	 *
	 */
	@TransactionalException
	public SysCollect addCollect(SysCollect sysCollect) {
		AuthInfo authInfo = getAuthInfo();
		sysCollect.setIuserid(authInfo.getIndocno());
		SysCollect cSysCollect 
				= this.sysCollectMapper.findByImoduleid(authInfo.getIndocno(), sysCollect.getImoduleid());
		if(null != cSysCollect) {
			throw new CustomException("base.collect.error");
		}
		this.insert(sysCollect);
		return sysCollect;
	}
	
	/**
	 * 
	 * 	删除收藏
	 * 
	 * @param sysCollect
	 * @author yaoxc
	 * @date 2019年8月7日 下午5:25:20
	 *
	 */
	@TransactionalException
	public void deleteCollect(SysCollect sysCollect) {
	    AuthInfo authInfo = getAuthInfo();
	    SysCollect collect = sysCollectMapper.findByImoduleid(authInfo.getIndocno(),sysCollect.getImoduleid());
		this.sysCollectMapper.delete(collect);
	}

	@Override
	public BaseMapper<SysCollect> getMapper() {
		return sysCollectMapper;
	}
	
}
