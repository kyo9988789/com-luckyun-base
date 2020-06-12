package com.luckyun.base.post.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.luckyun.base.post.mapper.SysPostMapper;
import com.luckyun.base.post.param.SysPostParam;
import com.luckyun.core.annotation.GlobalLockByRedis;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.drag.DragHelperService;
import com.luckyun.model.post.SysPost;
import com.luckyun.model.user.SysAccount;

@Service
public class SysPostService extends BaseService<SysPost>{

	@Autowired 
	private SysPostMapper sysPostMapper;
	
	@Autowired
	@Qualifier("dragHelperService")
	private DragHelperService dragHelperService;
	
	/**
	 * 根据条件获取对应的岗位数据
	 * @param condition 岗位过滤条件
	 * @return 指定的岗位列表
	 */
	public List<SysPost> findAll(SysPostParam condition) {
		List<SysPost> result = sysPostMapper.findAll(condition);
		return result;
	}

	/**
	 * 根据编号获取对应的岗位数据
	 * @param indocno 主键
	 * @return 指定的岗位信息
	 */
	public SysPost readOneByIndocno(Long indocno) {
		SysPost result = sysPostMapper.findOne(indocno);
		return result;
	}
	@TransactionalException
	public void deleteLogic(SysPostParam condition) {
		List<SysPost> sysPosts = condition.getDelList();
		for(SysPost post : sysPosts) {
			super.delete(post);
		}
	}
	
	@TransactionalException
	public SysPost update(SysPost sysPost,SysPostParam condition) {
		super.update(sysPost);
		return sysPost;
	}
	
	@TransactionalException
	public SysPost add(SysPost sysPost,SysPostParam condition) {
		super.insert(sysPost);
		return sysPost;
	}
	
	/**
	 * @desc 拖拽排序操作数据列表
	 * @param dIndocno 拖拽对象主键
	 * @param pIndocnoa 拖拽位置上方对象主键
	 * @param pIndocnob 拖拽位置下方对象主键
	 */
	@TransactionalException
	@GlobalLockByRedis
	public void dragSysPost(SysPostParam condition) {
		SysPost pSysPost = sysPostMapper.findOne(condition.getPindocno());
		SysPost dSysPost = sysPostMapper.findOne(condition.getDindocno());
		dragHelperService.drag(pSysPost, dSysPost, true, "iclassifyid", dSysPost.getIclassifyid());
	}
	
	/**
	 * 克隆岗位信息
	 * @param indocno 被克隆岗位信息主键
	 * @return 克隆岗位信息
	 * @throws Exception 
	 */
	public Integer clone(Long indocno) {
		return 0;
	}
	
	/**
	 * @describe Description: 根据岗位查询用户  
	 * @param indocno主键
	 * @return 用户信息
	 * @throws Exception 
	 */
	public List<SysAccount> findUserByPost(Long indocno) {
		return null;
	}
	
	@TransactionalException
	public void resetSort(Long iclassifyid) {
		SysPostParam condition = new SysPostParam();
		condition.setIclassifyid(iclassifyid);
		List<SysPost> sysPosts = sysPostMapper.findAllNoIdel(condition);
		for(int i = 1;i<= sysPosts.size();i++) {
			SysPost sysPost = new SysPost();
			sysPost.setIsort(i);
			sysPost.setIndocno(sysPosts.get(i-1).getIndocno());
			sysPostMapper.update(sysPost);
		}
	}
	
	@Override
	public BaseMapper<SysPost> getMapper() {
		return sysPostMapper;
	}
}
