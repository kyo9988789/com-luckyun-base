package com.luckyun.base.user.service;

import java.util.List;
import java.util.stream.Collectors;

import com.luckyun.base.major.mapper.SysMajorMapper;
import com.luckyun.model.major.SysMajor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.user.mapper.SysUserMapper;
import com.luckyun.base.user.mapper.SysUserPostMapper;
import com.luckyun.base.user.mapper.SysUserRoleMapper;
import com.luckyun.base.user.param.SysUserParam;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.tool.CollecterMixUtils;
import com.luckyun.model.post.SysPost;
import com.luckyun.model.role.SysRole;
import com.luckyun.model.user.SysAccount;
import com.luckyun.model.user.SysUserPost;
import com.luckyun.model.user.SysUserRole;

@Service
public class SysUserSubAttrService extends SysUserBaseService{
	@Autowired
	private SysUserPostMapper sysUserPostMapper;
	@Autowired
	private SysUserMapper sysUserMapper; 
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	private SysMajorMapper sysMajorMapper;
	
	public List<SysAccount> findByPost(SysUserParam condition){
		List<SysAccount> sysUserList = sysUserMapper.findAll(condition);
		this.createSysUserList(condition,sysUserList);
		return sysUserList;
	}
	public List<SysAccount> findByRole(SysUserParam condition){
		List<SysAccount> sysUserList = sysUserMapper.findAll(condition);
		this.createSysUserList(condition,sysUserList);
		return sysUserList;
	}
	public List<SysAccount> findByMajor(SysUserParam condition){
		List<SysAccount> sysUserList = sysUserMapper.findUserMajor(condition);
		this.createSysUserList(condition,sysUserList);
		return sysUserList;
	}
	private void createSysUserList(SysUserParam condition,List<SysAccount> sysUserList){
		List<Long> iuserids = sysUserList.stream()
				.map(SysAccount::getIndocno).collect(Collectors.toList());
		condition = new SysUserParam();
		condition.setIuserids(iuserids);
		List<SysRole> roleNames = sysUserRoleMapper.findRoleByUserRole(condition);
		List<SysPost> postNames = sysUserPostMapper.findPostByUserPost(condition);
		List<SysMajor> majorNames = sysMajorMapper.findMajorByUserMajor(condition);
		convertUserList(sysUserList,roleNames,"role");
		convertUserList(sysUserList,postNames,"post");
		convertUserList(sysUserList,majorNames,"major");
	}
	
	@TransactionalException
	public void addUserPost(SysUserParam condition) {
		SysUserParam sysUserParam = new SysUserParam();
		sysUserParam.setIpostid(condition.getIpostid());
		List<SysAccount> postUsers = sysUserMapper.findAll(sysUserParam);
		List<SysAccount> needAdd = CollecterMixUtils.fdiffSet(condition.getSysUsers(), postUsers, "indocno");
		for(SysAccount sysUser : needAdd) {
			SysUserPost sysUserPost = new SysUserPost();
			sysUserPost.setIuserid(sysUser.getIndocno());
			sysUserPost.setIpostid(condition.getIpostid());
			sysUserPostMapper.insert(sysUserPost);
		}
	}
	
	@TransactionalException
	public void addUserRole(SysUserParam condition) {
		SysUserParam sysUserParam = new SysUserParam();
		sysUserParam.setIroleid(condition.getIroleid());
		List<SysAccount> roleUsers = sysUserMapper.findAll(sysUserParam);
		List<SysAccount> needAdd = CollecterMixUtils.fdiffSet(condition.getSysUsers(), roleUsers, "indocno");
		for(SysAccount sysUser : needAdd) {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setIuserid(sysUser.getIndocno());
			sysUserRole.setIroleid(condition.getIroleid());
			sysUserRoleMapper.insert(sysUserRole);
		}
	}
	
	@TransactionalException
	public void updateNk(SysAccount account) {
		updateSysUserInfo(account);
	} 
	
	@TransactionalException
	public void deleteUserPost(SysUserParam condition) {
		if(condition.getSysUsers() != null && condition.getSysUsers().size() >= 1) {
			List<Long> iuserids = condition.getSysUsers().stream()
					.map(SysAccount::getIndocno).collect(Collectors.toList());
			condition.setIuserids(iuserids);
			List<SysUserPost> result = sysUserPostMapper.selectPosts(condition);
			for(SysUserPost sysUserPost : result) {
				sysUserPostMapper.delete(sysUserPost);
			}
		}
	}
	@TransactionalException
	public void deleteUserRole(SysUserParam condition) {
		if(condition.getDelList() != null && condition.getDelList().size() >= 1) {
			List<Long> iuserids = condition.getDelList().stream()
					.map(SysAccount::getIndocno).collect(Collectors.toList());
			condition.setIuserids(iuserids);
			List<SysUserRole> result = sysUserRoleMapper.findRoleByCondition(condition);
			for(SysUserRole sysUserRole : result) {
				sysUserRoleMapper.delete(sysUserRole);
			}
		}
	}
}
