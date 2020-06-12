package com.luckyun.base.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.luckyun.base.user.mapper.SysUserCompanyMapper;
import com.luckyun.base.user.mapper.SysUserMajorMapper;
import com.luckyun.base.user.mapper.SysUserMapper;
import com.luckyun.base.user.mapper.SysUserPostMapper;
import com.luckyun.base.user.mapper.SysUserRoleMapper;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.tool.CollecterMixUtils;
import com.luckyun.core.tool.ReflectHelperUtils;
import com.luckyun.model.dept.SysDept;
import com.luckyun.model.major.SysMajor;
import com.luckyun.model.post.SysPost;
import com.luckyun.model.role.SysRole;
import com.luckyun.model.user.SysAccount;
import com.luckyun.model.user.SysUser;
import com.luckyun.model.user.SysUserCompany;
import com.luckyun.model.user.SysUserMajor;
import com.luckyun.model.user.SysUserPost;
import com.luckyun.model.user.SysUserRole;

/**
 * 用户业务接口层,定义需要操作的用户有关业务
 * @author yangj080
 *
 */
public abstract class SysUserBaseService extends BaseService<SysAccount>{

	@Autowired
	private SysUserMapper sysUserMapper; 
	
	@Autowired
	private SysUserInfoService sysUserInfoService;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Autowired
	private SysUserCompanyMapper sysUserCompanyMapper;
	
	@Autowired
	private SysUserPostMapper sysUserPostMapper;
	
	@Autowired
	private SysUserMajorMapper sysUserMajorMapper;

	protected void addSysUserInfo(SysAccount entity) {
		SysUser sysUserInfo = entity.getSysUserInfo();
		if(sysUserInfo != null) {
			sysUserInfo.setIuserid(entity.getIndocno());
			sysUserInfoService.insert(sysUserInfo);
		}
	}
	
	protected void updateSysUserInfo(SysAccount entity) {
		SysUser sysUserInfo = entity.getSysUserInfo();
		sysUserInfo.setIuserid(entity.getIndocno());
		SysUser sysUser = sysUserMapper.findInfoByIuserid(entity.getIndocno());
		if(sysUser != null) {
			sysUserInfo.setIndocno(sysUser.getIndocno());
			sysUserInfo.setLogArg0(entity.getIndocno());
		}
		sysUserInfoService.update(sysUserInfo);
	}
	/**
	 * 修改所属部门
	 * @param entity 修改的实体对象
	 * @param authInfo 登录用户
	 */
	protected void updateOwnDept(SysAccount entity,AuthInfo authInfo,Long ideptid) {
		SysUser user = sysUserMapper.findInfoByIuserid(entity.getIndocno());
		if(user != null) {
			SysUser sysUser = new SysUser();
			sysUser.setIndocno(user.getIndocno());
			sysUser.setIdeptid(ideptid);
			sysUserInfoService.update(sysUser);
		}
	}
	
	/**
	 * 批量修改的管辖部门
	 * @param entity 修改的对象
	 * @param authInfo 登录用户
	 */
	protected void updateDepts(SysAccount entity,AuthInfo authInfo) {
		List<SysUserCompany> oldDepts = 
				sysUserCompanyMapper.findDeptsByUid(entity.getIndocno());
		
		List<SysUserCompany> nDeps = entity.getSysDepts() != null ? entity.getSysDepts().stream()
				.map(e -> new SysUserCompany(e.getIndocno())).collect(Collectors.toList()) : new ArrayList<>();
		List<SysUserCompany> mix = CollecterMixUtils.tMix(nDeps, oldDepts, "ideptid");
		
		List<SysUserCompany> addDepts = CollecterMixUtils.fdiffSet(nDeps, mix, "ideptid");
		List<SysUserCompany> delDepts = CollecterMixUtils.fdiffSet(oldDepts, mix, "ideptid");
		for(SysUserCompany addDept : addDepts) {
			addDept.setIuserid(entity.getIndocno());
			sysUserCompanyMapper.insert(addDept);
		}
		for(SysUserCompany delDept : delDepts) {
			sysUserCompanyMapper.delete(delDept);
		}
	}
	
	protected void updatePosts(SysAccount entity) {
		List<SysUserPost> oldPosts = sysUserPostMapper.findPostByUid(entity.getIndocno());
		List<SysUserPost> nPost = entity.getSysPosts() != null ? entity.getSysPosts().stream()
				.map(e -> new SysUserPost(e.getIndocno())).collect(Collectors.toList()) : new ArrayList<>();
		List<SysUserPost> mix = CollecterMixUtils.tMix(nPost, oldPosts, "ipostid");
		List<SysUserPost> addPosts = CollecterMixUtils.fdiffSet(nPost, mix, "ipostid");
		List<SysUserPost> delPosts = CollecterMixUtils.fdiffSet(oldPosts, mix, "ipostid");
		for(SysUserPost addPost : addPosts) {
			addPost.setIuserid(entity.getIndocno());
			sysUserPostMapper.insert(addPost);
		}
		for(SysUserPost delPost : delPosts) {
			sysUserPostMapper.delete(delPost);
		}
	}
	
	protected void updateRoles(SysAccount entity) {
		List<SysUserRole> oldRoles = sysUserRoleMapper.findRoleByUid(entity.getIndocno());
		List<SysUserRole> nRoles = entity.getSysRoles()!= null ? entity.getSysRoles().stream()
				.map(e -> new SysUserRole(e.getIndocno())).collect(Collectors.toList()) : new ArrayList<>();
		List<SysUserRole> mix = CollecterMixUtils.tMix(nRoles, oldRoles, "iroleid");
		List<SysUserRole> addRoles = CollecterMixUtils.fdiffSet(nRoles,mix, "iroleid");
		List<SysUserRole> delRoles = CollecterMixUtils.fdiffSet(oldRoles,mix, "iroleid");
		for(SysUserRole addRole : addRoles) {
			addRole.setIuserid(entity.getIndocno());
			sysUserRoleMapper.insert(addRole);
		}
		for(SysUserRole delRole : delRoles) {
			sysUserRoleMapper.delete(delRole);
		}
	}
	protected void updateMajor(SysAccount entity) {
		List<SysUserMajor> oldMajor = sysUserMajorMapper.findMajorByUid(entity.getIndocno());
		List<SysUserMajor> nMajor = entity.getSysMajors()!= null ? entity.getSysMajors().stream()
				.map(e -> new SysUserMajor(e.getIndocno(),entity.getIndocno())).collect(Collectors.toList()) : new ArrayList<>();
		List<SysUserMajor> mix = CollecterMixUtils.tMix(nMajor, oldMajor, "imajorid");
		List<SysUserMajor> addMajors = CollecterMixUtils.fdiffSet(nMajor,mix, "imajorid");
		List<SysUserMajor> delMajors = CollecterMixUtils.fdiffSet(oldMajor,mix, "imajorid");
		for(SysUserMajor addMajor : addMajors) {
			addMajor.setIuserid(entity.getIndocno());
			sysUserMajorMapper.insert(addMajor);
		}
		for(SysUserMajor delMajor : delMajors) {
			sysUserMajorMapper.delete(delMajor);
		}
	}

	
	protected void addDepts(SysAccount entity,List<SysDept> depts,AuthInfo authInfo) {
		if(depts != null && depts.size() >= 1) {
			for(SysDept sysDept : depts) {
				SysUserCompany sysUserCompany = new SysUserCompany();
				sysUserCompany.setIdeptid(sysDept.getIndocno());
				sysUserCompany.setIuserid(entity.getIndocno());
				sysUserCompanyMapper.insert(sysUserCompany);
			}
		}
	}
	protected void addRoles(SysAccount entity,List<SysRole> roles) {
		if(roles != null && roles.size() >= 1) {
			for(SysRole role : roles) {
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setIuserid(entity.getIndocno());
				sysUserRole.setIroleid(role.getIndocno());
				sysUserRoleMapper.insert(sysUserRole);
			}
		}
	}
	protected void addPosts(SysAccount entity) {
		List<SysPost> sysPosts = entity.getSysPosts();
		if(sysPosts != null && sysPosts.size() >= 1) {
			for(SysPost post : sysPosts) {
				SysUserPost sysUserPost = new SysUserPost();
				sysUserPost.setIpostid(post.getIndocno());
				sysUserPost.setIuserid(entity.getIndocno());
				sysUserPostMapper.insert(sysUserPost);
			}
		}
	}
	protected void addMajors(SysAccount entity) {
		List<SysMajor> sysMajors = entity.getSysMajors();
		if(sysMajors != null && sysMajors.size() >= 1) {
			for(SysMajor major : sysMajors) {
				SysUserMajor sysUserMajor = new SysUserMajor();
				sysUserMajor.setImajorid(major.getIndocno());
				sysUserMajor.setIuserid(entity.getIndocno());
				sysUserMajorMapper.insert(sysUserMajor);
			}
		}
	}

	protected <T> void convertUserList(List<SysAccount> userList
			, List<T> postList,String ctype) {
		Map<Long,List<String>> postKv = new HashMap<Long, List<String>>();
		postList.forEach(e -> {
			Object tkey = ReflectHelperUtils.getVbyKey(e, "iuserid");
			Object tval = ReflectHelperUtils.getVbyKey(e, "sname");
			String sval = tval != null ? tval.toString() : "";
			if (postKv.containsKey(tkey)) {
				postKv.get(tkey).add(sval);
			}else {
				List<String> postNms = new ArrayList<>();
				postNms.add(sval);
				postKv.put((Long)tkey, postNms);
			}
		});
		for(SysAccount sysUser : userList) {
			List<String> postNm = postKv.get(sysUser.getIndocno());
			if(postNm != null && postNm.size() >= 1) {
				if("post".equals(ctype)) {				//岗位
					sysUser.setPostnms(String.join(",", postNm));
				}else if("role".equals(ctype)) {		//角色
					sysUser.setRolenms(String.join(",", postNm));
				}else if("major".equals(ctype)){		//专业
					sysUser.setMajornms(String.join(",", postNm));
				}
			}
		}
		postKv.clear();
	}

	@Override
	public BaseMapper<SysAccount> getMapper() {
		return sysUserMapper;
	}
}
