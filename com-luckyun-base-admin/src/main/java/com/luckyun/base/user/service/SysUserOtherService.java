package com.luckyun.base.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.luckyun.base.dept.mapper.SysDeptMapper;
import com.luckyun.base.user.mapper.SysUserMapper;
import com.luckyun.base.user.param.SysUserParam;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.exception.CustomException;
import com.luckyun.core.redis.RedisOperationDao;
import com.luckyun.model.dept.SysDept;
import com.luckyun.model.user.SysAccount;

@Service
public class SysUserOtherService extends SysUserBaseService {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysDeptMapper sysDeptMapper;

	@Autowired
	private RedisOperationDao redisOperationDao;

	public JSONObject findUserForBpm(String sloginid) {
		JSONObject obj = new JSONObject();
		JSONObject result = new JSONObject();
		List<SysAccount> sysAccounts = sysUserMapper.findUserForBpm(sloginid);
		List<JSONObject> content = new ArrayList<JSONObject>(sysAccounts.size());
		for (SysAccount sysAccount : sysAccounts) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", sysAccount.getSloginid());
			jsonObject.put("username", sysAccount.getSname());
			jsonObject.put("dept", sysAccount.getDeptname());
			content.add(jsonObject);
		}
		result.put("content", content);
		obj.put("obj", result);
		return obj;
	}

	/**
	 * 根据条件生成用户的包含部门的信息
	 * 
	 * @return
	 */
	public List<SysAccount> findUserDepts(SysUserParam condition) {
		addCondition(condition);
		List<SysAccount> result = sysUserMapper.findAll(condition);
		for (SysAccount account : result) {
			String sdeptSidcc = account.getSdeptsidcc();
			if (!StringUtils.isEmpty(sdeptSidcc)) {
				String[] depts = sdeptSidcc.split("/");
				List<String> sdeptNm = new ArrayList<>();
				for (String ideptId : depts) {
					if (!StringUtils.isEmpty(ideptId)) {
						SysDept sysDept = getDeptObj(ideptId);
						if (sysDept != null) {
							sdeptNm.add(sysDept.getSname());
						}
					}
				}
				sdeptNm.add(account.getSname());
				account.setSdeptsidccnms(String.join("/", sdeptNm));
			}
		}
		return result;
	}

	private void addCondition(SysUserParam condition) {
		// 根据最顶级节点过滤
		AuthInfo authInfo = getAuthInfo();
		if(!"administrator".equals(authInfo.getSloginid())) {
			if (authInfo.getIdeptid() != null) {
				SysDept ownSysDept = sysDeptMapper.findOne(authInfo.getIdeptid());
				if (!StringUtils.isEmpty(ownSysDept.getSidcc())) {
					String[] sidcc = ownSysDept.getSidcc().split("/");
					Long rootNode = 0L;
					for (String sid : sidcc) {
						if (!StringUtils.isEmpty(sid)) {
							rootNode = Long.valueOf(sid);
							break;
						}
					}
					condition.setIdeptidsidcc(rootNode);
				}
			} else {
				throw new CustomException("未找到当前用户的职责部门!");
			}
		}
	}

	private SysDept getDeptObj(Object ideptId) {
		String sdept = ideptId != null ? ideptId.toString() : "";
		SysDept newDept = new SysDept();
		boolean flag = true;
		if (!StringUtils.isEmpty(sdept)) {
			try {
				newDept = redisOperationDao.hMget("sys_dept_user_level_name", SysDept.class, sdept);
				if (newDept == null) {
					flag = false;
					newDept = sysDeptMapper.findOne(Long.valueOf(sdept));
				}
			} catch (Exception e) {
				flag = false;
				newDept = sysDeptMapper.findOne(Long.valueOf(sdept));
			}

		}
		if (!flag && newDept != null) {
			Map<String, String> datas = new HashMap<String, String>();
			SysDept dept = new SysDept();
			dept.setIndocno(newDept.getIndocno());
			dept.setSname(newDept.getSname());
			dept.setIparentid(newDept.getIparentid());
			dept.setSidcc(newDept.getSidcc());
			datas.put(sdept, JSONObject.toJSONString(dept));
			redisOperationDao.hMset("sys_dept_user_level_name", datas);
			datas.clear();
		}
		return newDept;
	}
}
