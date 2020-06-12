package com.luckyun.base.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.notice.mapper.SysNoticeUserMapper;
import com.luckyun.base.notice.param.SysNoticeParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.model.notice.SysNoticeUser;
import com.luckyun.model.user.SysAccount;

@Service
public class SysNoticeUserService extends BaseService<SysNoticeUser>{

	@Autowired
	private SysNoticeUserMapper sysNoticeUserMapper;
	
	public List<SysAccount> findNoticeUserReadState(SysNoticeParam sysNoticeParam){
		return sysNoticeUserMapper.findUserByReadDetail(sysNoticeParam);
	}
	
	@Override
	public BaseMapper<SysNoticeUser> getMapper() {
		
		return sysNoticeUserMapper;
	}

	
}
