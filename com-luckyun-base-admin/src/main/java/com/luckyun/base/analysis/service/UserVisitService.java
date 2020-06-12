package com.luckyun.base.analysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luckyun.base.analysis.mapper.UserVisitMapper;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.model.user.SysUserVisit;

/**
 * 用户模块访问统计
 * @author omai
 *
 */
@Component
public class UserVisitService extends BaseService<SysUserVisit> {
	
	@Autowired
	UserVisitMapper userVisitMapper;
	
	
	
	public UserVisitService() {
		super();
		this.setLogable(false);
	}

	/**
	 * 查找用户模块访问记录
	 * @param entity
	 * @return
	 */
	public SysUserVisit find(SysUserVisit entity) {
		return userVisitMapper.find(entity);
	}

	@Override
	public BaseMapper<SysUserVisit> getMapper() {
		// TODO Auto-generated method stub
		return userVisitMapper;
	}
	

}
