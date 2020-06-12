package com.luckyun.base.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.luckyun.base.hepler.SysDatadicConstantNameAlias;
import com.luckyun.base.hepler.SysDatadicHelper;
import com.luckyun.base.major.mapper.SysMajorMapper;
import com.luckyun.base.user.mapper.SysUserCompanyMapper;
import com.luckyun.base.user.mapper.SysUserMapper;
import com.luckyun.base.user.mapper.SysUserPostMapper;
import com.luckyun.base.user.mapper.SysUserRoleMapper;
import com.luckyun.base.user.param.SysUserParam;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.tool.Md5HelperUtils;
import com.luckyun.core.tool.OssPathHelperUtils;
import com.luckyun.core.tool.PinYinHelperUtils;
import com.luckyun.model.dept.SysDept;
import com.luckyun.model.major.SysMajor;
import com.luckyun.model.post.SysPost;
import com.luckyun.model.role.SysRole;
import com.luckyun.model.user.SysAccount;

@Service
public class SysUserService extends SysUserBaseService{

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysUserCompanyMapper sysUserCompanyMapper;
	
	@Autowired
	private SysUserPostMapper sysUserPostMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Autowired
	private SysMajorMapper sysMajorMapper;
	
	@Autowired
	private SysDatadicHelper sysDatadicHelper;
	
	@Autowired
	private OssPathHelperUtils ossPathHelperUtils;
	
	/**
	 * 根据条件获取用户列表数据
	 * @param condition 查找条件
	 * @return 用户列表数据
	 */
	public List<SysAccount> findUserByCondition(SysUserParam condition) {
		String[] stateArr = {"未启用","已启用","已锁定","已过期"};
		List<SysAccount> result = sysUserMapper.findAll(condition);
		for(SysAccount account : result) {
			account.setSstate(stateArr[account.getIstate()]);
		}
		return result;
	}
	
	/**
	 * 根据登录名称获取用户对象
	 * @param sloginid 登录名称
	 * @return 用户对象
	 */
	public SysAccount findBySloginid(String sloginid) {
		return sysUserMapper.findBySloginid(sloginid);
	}
	/**
	 * 添加用户信息
	 * @param entity 添加的用户实体
	 * @param condition 条件
	 * @return 新增的用对象
	 * @throws Exception 
	 *
	 */
	@TransactionalException
	public SysAccount add(SysAccount entity,SysUserParam condition) throws Exception {
		AuthInfo authInfo = getAuthInfo();
		SysAccount isExists = sysUserMapper.findBySloginid(entity.getSloginid());
		if(isExists == null) {
			entity.setSpinyinsingle(PinYinHelperUtils.getFirstSpell(entity.getSname()));
			entity.setSpinyinfull(PinYinHelperUtils.getPingYin(entity.getSname()));
			if(entity.getIstate() == null) {
				entity.setIstate(1);//账号默认启用
			}
			if(entity.getDovertime() == null) {
				//过期时间为空即永不过期
				entity.setDovertime(new Date(4102358400000L));
			}
			if(!StringUtils.isEmpty(entity.getSpassword())) {
				entity.setSpassword(Md5HelperUtils.md5(entity.getSpassword()));
			}else {
				throw new Exception("密码不能为空");
			}
			super.insert(entity);
			//添加用户详情
			addSysUserInfo(entity);
			//添加用户部门,角色,岗位
			addDepts(entity,entity.getSysDepts(),authInfo);
			addRoles(entity,entity.getSysRoles());
			addPosts(entity);
			addMajors(entity);
			return entity;
		}else {
			throw new Exception("用户已存在");
		}
	}

	/**
	 * 逻辑删除用户对象
	 * @param condition 删除对象的过滤条件
	 */
	@TransactionalException
	public void deleteLogic(SysUserParam condition) {
		List<SysAccount> delLists = condition.getDelList();
		if(delLists != null && delLists.size() >= 1) {
			for(SysAccount delUser : delLists) {
				super.delete(delUser);
			}
		}
	}
	
	@TransactionalException
	public SysAccount update(SysAccount entity,SysUserParam condition) {
		AuthInfo authInfo = getAuthInfo();
		//不允许修改
		entity.setSpassword(null);
		entity.setSloginid(null);
		super.update(entity);
		if(entity.getUpdColumns() != null && 
				entity.getUpdColumns().contains("sysUserInfo")) {
			updateSysUserInfo(entity);
		}
		if(entity.getUpdColumns() != null && 
				entity.getUpdColumns().contains("sysDepts")) {
			updateDepts(entity,authInfo);
		}
		if(entity.getUpdColumns() != null && 
				entity.getUpdColumns().contains("sysPosts")) {
			updatePosts(entity);
		}
		if(entity.getUpdColumns() != null && 
				entity.getUpdColumns().contains("sysRoles")) {
			updateRoles(entity);
		}
		if(entity.getUpdColumns() != null && 
				entity.getUpdColumns().contains("sysMajors")) {
			updateMajor(entity);
		}
		return entity;
	}
	/**
	 *查询用户详情
	 * @param condition 查询条件
	 * @return 用户详情数据
	 *
	 */
	public SysAccount findUserInfo(SysUserParam condition) {
		SysAccount sysAccount = sysUserMapper.findOne(condition.getIndocno());
		if(sysAccount != null) {
			//数据字典转换
			convertDatadic(sysAccount);
			//构建头像完整地址
			String sphoto = sysAccount.getSysUserInfo() != null ? sysAccount.getSysUserInfo().getSphoto() : "";
			if(!StringUtils.isEmpty(sphoto)) {
				sysAccount.getSysUserInfo().setNsphoto(ossPathHelperUtils.generateShowImgNoAuth(sysAccount.getSloginid()
						, "base", sphoto));
			}
			List<SysDept> sysDepts = sysUserCompanyMapper.findDeptObjByUid(sysAccount.getIndocno());
			List<Long> longs = new ArrayList<Long>();
			longs.add(sysAccount.getIndocno());condition = new SysUserParam();
			condition.setIuserids(longs);
			List<SysPost> sysPosts = sysUserPostMapper.findPostByUserPost(condition);
			List<SysRole> sysRoles = sysUserRoleMapper.findRoleByUser(sysAccount.getIndocno());
			List<SysMajor> sysMajors = sysMajorMapper.findMajorByUserMajor(condition);
			sysAccount.setSysDepts(sysDepts);
			sysAccount.setSysPosts(sysPosts);
			sysAccount.setSysRoles(sysRoles);
			sysAccount.setSysMajors(sysMajors);
		}
		return sysAccount;
	}
	
	private void convertDatadic(SysAccount sysAccount) {
		if(sysAccount.getSysUserInfo() != null) {
			sysAccount.getSysUserInfo().setSsex(
					sysDatadicHelper.getDatadicNmBySfact(sysAccount.getSysUserInfo().getIsex(), SysDatadicConstantNameAlias.SSEX).getSname());
			sysAccount.getSysUserInfo().setSmarry(
					sysDatadicHelper.getDatadicNmBySfact(sysAccount.getSysUserInfo().getImarry(), SysDatadicConstantNameAlias.SMARRY).getSname());
		}
	}
	
	public SysAccount updatePassWord(Long indocno,String password) {
		SysAccount account = sysUserMapper.findOne(indocno);
		SysAccount newAccount = new SysAccount();
		newAccount.setIndocno(indocno);
		newAccount.setSpassword(Md5HelperUtils.md5(password));
		sysUserMapper.update(newAccount);
		return account;
	}
}
