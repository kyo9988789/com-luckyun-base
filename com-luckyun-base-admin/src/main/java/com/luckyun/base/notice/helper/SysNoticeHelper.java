package com.luckyun.base.notice.helper;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luckyun.base.hepler.SysDatadicConstantNameAlias;
import com.luckyun.base.hepler.SysDatadicHelper;
import com.luckyun.base.notice.mapper.SysNoticeUserHisMapper;
import com.luckyun.base.notice.mapper.SysNoticeUserMapper;
import com.luckyun.base.notice.param.SysNoticeMembers;
import com.luckyun.base.notice.param.SysNoticeStatis;
import com.luckyun.model.dept.SysDept;
import com.luckyun.model.notice.SysNotice;
import com.luckyun.model.notice.SysNoticeUserHis;
import com.luckyun.model.role.SysRole;
import com.luckyun.model.user.SysAccount;

@Component
public class SysNoticeHelper {

    @Autowired
    private SysDatadicHelper sysDatadicHelper;
    
    @Autowired
    private SysNoticeUserHisMapper sysNoticeUserHisMapper;
    
    @Autowired
    private SysNoticeUserMapper sysNoticeUserMapper;
    
	public SysNoticeMembers handleSendMember(SysNotice sysNotice) {
		SysNoticeMembers jsonObject = new SysNoticeMembers();
    	List<SysDept> sysDepts = sysNotice.getSendDepts(); 
    	if(sysDepts != null && sysDepts.size() >= 1) {
    		List<SysDept> newDepts = 
    				sysDepts.stream().sorted(Comparator.comparing(SysDept::getIndocno)).collect(Collectors.toList());
    		jsonObject.setSendDepts(newDepts);
    	}
    	List<SysRole> sysRoles = sysNotice.getSendRoles();
    	if(sysRoles != null && sysRoles.size() >= 1) {
    		List<SysRole> newRoles = 
    				sysRoles.stream().sorted(Comparator.comparing(SysRole::getIndocno)).collect(Collectors.toList());
    		jsonObject.setSendRoles(newRoles);
    	}
    	List<SysAccount> sysAccounts = sysNotice.getSendAccounts();
    	if(sysAccounts != null && sysAccounts.size() >= 1) {
    		List<SysAccount> newSysAccounts = 
    				sysAccounts.stream().sorted(Comparator.comparing(SysAccount::getIndocno)).collect(Collectors.toList());
    		jsonObject.setSendAccounts(newSysAccounts);
    	}
    	return jsonObject;
    }
    
    public void changeState(SysNotice sysNotice) {
    	Date nowDate = new Date();
    	if(sysNotice.getDstart().after(nowDate)) {
    		sysNotice.setIstate(3);
    		sysNotice.setSstate("未发送");
    	}
    	if(sysNotice.getDstart().compareTo(nowDate) <= 0
    			&& sysNotice.getDend().compareTo(nowDate) >= 0) {
    		sysNotice.setIstate(1);
    		sysNotice.setSstate("已发送");
    	}
    	if(sysNotice.getDend().before(nowDate)) {
    		sysNotice.setIstate(2);
    		sysNotice.setSstate("已过期");
    	}
    }
    
    public void datdicConvert(SysNotice sysNotice) {
    	sysNotice.setStype(sysDatadicHelper
    			.getDatadicNmBySfact(sysNotice.getItype(),SysDatadicConstantNameAlias.BASENOTICETYPE).getSname());
    }
    
    public void addReadClick(Long ilinkno,Long iuserid) {
    	SysNoticeUserHis noticeUserHis =
    			sysNoticeUserHisMapper.findByIlinknoAndIuserid(ilinkno, iuserid);
    	if(noticeUserHis != null) {
	    	SysNoticeUserHis updSysNoticeUserHis = new SysNoticeUserHis();
	    	updSysNoticeUserHis.setIndocno(noticeUserHis.getIndocno());
	    	if(noticeUserHis.getIreadCount() == null || noticeUserHis.getIreadCount() == 0) {
	    		updSysNoticeUserHis.setIreadTime(new Date());
	    	}
	    	Integer newReadCount = noticeUserHis.getIreadCount() != null ? noticeUserHis.getIreadCount() + 1: 1;
	    	updSysNoticeUserHis.setIreadCount(newReadCount);
	    	sysNoticeUserHisMapper.update(updSysNoticeUserHis);
    	}
    }
    
    public SysNoticeUserHis findReadHis(Long ilinkno,Long iuserid) {
    	return sysNoticeUserHisMapper.findByIlinknoAndIuserid(ilinkno, iuserid);
    }
    
    public void setNoticeReadState(SysNotice notice) {
    	SysNoticeStatis noticeStatis = sysNoticeUserMapper.findStatisByIlinkno(notice.getIndocno());
		if(noticeStatis != null) {
			notice.setDone(noticeStatis.getDone() != null ? noticeStatis.getDone() : 0);
			notice.setDoing(noticeStatis.getDoing() != null ? noticeStatis.getDoing() : 0);
			notice.setTotal(noticeStatis.getTotal() != null ? noticeStatis.getTotal() : 0);
		}else {
			notice.setDone(0);notice.setDoing(0);notice.setTotal(0);
		}
    }
    
    public void setNoticeReadState(List<SysNotice> notices) {
    	List<Long> sysNoticeIds = notices.stream().map(SysNotice::getIndocno).collect(Collectors.toList());
    	List<SysNoticeStatis> noticeStatisList = sysNoticeUserMapper.findStatisByIlinknoList(sysNoticeIds);
		if(noticeStatisList != null && noticeStatisList.size() >= 1) {
			for(SysNotice notice : notices) {
				Optional<SysNoticeStatis> pNoticeStatis = 
						noticeStatisList.stream().filter(e -> e.getIlinkno().equals(notice.getIndocno())).findFirst();
				if(pNoticeStatis.isPresent()) {
					SysNoticeStatis noticeStatis = pNoticeStatis.get();
					notice.setDone(noticeStatis.getDone() != null ? noticeStatis.getDone() : 0);
					notice.setDoing(noticeStatis.getDoing() != null ? noticeStatis.getDoing() : 0);
					notice.setTotal(noticeStatis.getTotal() != null ? noticeStatis.getTotal() : 0);
				}else {
					notice.setDone(0);notice.setDoing(0);notice.setTotal(0);
				}
			}
		}else {
			for(SysNotice notice : notices) {
				notice.setDone(0);notice.setDoing(0);notice.setTotal(0);
			}
		}
    }
    
    public void convertSysNoticeReadState(List<SysNotice> result,Long iuserid){
    	List<SysNoticeUserHis> noticeUserHis = sysNoticeUserHisMapper.findByIuserid(iuserid);
		for(SysNoticeUserHis sysNoticeUserHis : noticeUserHis ) {
			for(SysNotice sysNotice : result) {
				if(sysNoticeUserHis.getIlinkno().equals(sysNotice.getIndocno())) {
					if(sysNoticeUserHis.getIreadCount() >= 1) {
						sysNotice.setHadRead("已读");
					}
				}
			}
		}
		for(SysNotice sysNotice : result) {
			if(sysNotice.getHadRead() == null) {
				sysNotice.setHadRead("未读");
			}
		}
    }
}
