package com.luckyun.base.notice.event;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.luckyun.base.notice.mapper.SysNoticeUserHisMapper;
import com.luckyun.base.notice.mapper.SysNoticeUserMapper;
import com.luckyun.base.notice.param.SysNoticeGenerateParam;
import com.luckyun.base.user.mapper.SysUserMapper;
import com.luckyun.base.user.param.SysUserParam;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.event.EventQueue;
import com.luckyun.model.dept.SysDept;
import com.luckyun.model.notice.SysNotice;
import com.luckyun.model.notice.SysNoticeUser;
import com.luckyun.model.notice.SysNoticeUserHis;
import com.luckyun.model.role.SysRole;
import com.luckyun.model.user.SysAccount;

@Component
public class SysNoticeUserEvent extends EventQueue{
	
	@Autowired
	private SysNoticeUserMapper sysNoticeUserMapper;
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysNoticeUserHisMapper sysNoticeUserHisMapper;
	
	/**
	 * 接收添加通知公告的事件的routeKey
	 * TODO(请说明该方法的实现功能).
	 * 
	 * @return 监听的routeKey
	 * @see com.luckyun.core.mq.MQBaseQueue#getRouteKey()
	 */
	@Override
	protected String getRouteKey() {
		return "SysNoticeUserEvent";
	}
	@Override
	@TransactionalException
	protected void onMessage(String message) throws Exception {
		SysNotice sysNotice = JSONObject.parseObject(message, SysNotice.class);
		addSysNoticeUser(sysNotice);
	}
	/**
	 * 添加公告需要发布的人员
	 */
	private void addSysNoticeUser(SysNotice sysNotice) {
		//删除当前公告的数据
		sysNoticeUserMapper.deleteByIlinkno(sysNotice.getIndocno());
		List<SysNoticeUserHis> sysNoticeUserHis = sysNoticeUserHisMapper.findByIlinkno(sysNotice.getIndocno());
		//查找当前公告阅读情况
		Set<Long> userHadRead = sysNoticeUserHis.stream().map(SysNoticeUserHis::getIuserid).collect(Collectors.toSet());
		if(userHadRead == null) {userHadRead = new HashSet<>();}
		
		SysNoticeGenerateParam sysNoticeGenerateParam = new SysNoticeGenerateParam();
		sysNoticeGenerateParam.setInoticeid(sysNotice.getIndocno());
		sysNoticeGenerateParam.setUserHadRevicer(new HashSet<>());
		sysNoticeGenerateParam.setUserHadRead(userHadRead);
		//发送给所有人
		if(sysNotice.getNoticeAllMember() != null && sysNotice.getNoticeAllMember() == 1) {
			SysUserParam condition = new SysUserParam();
			List<SysAccount> sysAccounts = sysUserMapper.findAll(condition);
			sysNoticeGenerateParam.setSysAccounts(sysAccounts);
			addSysNoticeUserByAccounts(sysNoticeGenerateParam);
		}else {
			addSysNoticeByDepts(sysNotice.getSendDepts(),sysNoticeGenerateParam);
			addSysNoticeByRoles(sysNotice.getSendRoles(),sysNoticeGenerateParam);
			sysNoticeGenerateParam.setSysAccounts(sysNotice.getSendAccounts());
			addSysNoticeUserByAccounts(sysNoticeGenerateParam);
		}
	}
	
	private void addSysNoticeByDepts(List<SysDept> sysDepts,SysNoticeGenerateParam sysNoticeGenerateParam) {
		if(sysDepts != null && sysDepts.size() >= 1) {
			for(SysDept sysDept: sysDepts) {
				addSysNoticeByDept(sysDept,sysNoticeGenerateParam);
			}
		}
	}
	
	private void addSysNoticeByRoles(List<SysRole> sysRoles,SysNoticeGenerateParam sysNoticeGenerateParam) {
		if(sysRoles != null && sysRoles.size() >= 1) {
			for(SysRole sysRole: sysRoles) {
				addSysNoticeByRole(sysRole,sysNoticeGenerateParam);
			}
		}
	}
	
	private void addSysNoticeByDept(SysDept sysDept,SysNoticeGenerateParam sysNoticeGenerateParam) {
		if(sysDept != null) {
			SysUserParam condition = new SysUserParam();
			condition.setIdeptidsidcc(sysDept.getIndocno());
			List<SysAccount> sysAccounts = sysUserMapper.findAll(condition);
			
			sysNoticeGenerateParam.setSysAccounts(sysAccounts);
			addSysNoticeUserByAccounts(sysNoticeGenerateParam);
		}
	}
	
	private void addSysNoticeByRole(SysRole sysRole,SysNoticeGenerateParam sysNoticeGenerateParam) {
		if(sysRole != null) {
			SysUserParam condition = new SysUserParam();
			condition.setIroleid(sysRole.getIndocno());
			List<SysAccount> sysAccounts = sysUserMapper.findAll(condition);
			sysNoticeGenerateParam.setSysAccounts(sysAccounts);
			addSysNoticeUserByAccounts(sysNoticeGenerateParam);
		}
	}
	
	private void addSysNoticeUserByAccounts(SysNoticeGenerateParam sysNoticeGenerateParam) {
		if(sysNoticeGenerateParam.getSysAccounts() != null && sysNoticeGenerateParam.getSysAccounts().size() >= 1) {
			for(SysAccount account : sysNoticeGenerateParam.getSysAccounts()) {
				if(!sysNoticeGenerateParam.getUserHadRevicer().contains(account.getIndocno())) {
					SysNoticeUser noticeUser = new SysNoticeUser();
					noticeUser.setIlinkno(sysNoticeGenerateParam.getInoticeid());
					noticeUser.setIuserid(account.getIndocno());
					sysNoticeUserMapper.insert(noticeUser);
					//添加到已发送列表
					sysNoticeGenerateParam.getUserHadRevicer().add(account.getIndocno());
				}
				if(!sysNoticeGenerateParam.getUserHadRead().contains(account.getIndocno())) {
					SysNoticeUserHis noticeUserHis = new SysNoticeUserHis();
					noticeUserHis.setIlinkno(sysNoticeGenerateParam.getInoticeid());
					noticeUserHis.setIuserid(account.getIndocno());
					noticeUserHis.setIreadCount(0);
					sysNoticeUserHisMapper.insert(noticeUserHis);
					sysNoticeGenerateParam.getUserHadRead().add(account.getIndocno());
				}
			}
		}
	}
	
}
