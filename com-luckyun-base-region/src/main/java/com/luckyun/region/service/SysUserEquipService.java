package com.luckyun.region.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.response.ApiResult;
import com.luckyun.core.tool.CollecterMixUtils;
import com.luckyun.model.user.SysAccount;
import com.luckyun.region.mapper.SysUserEquipMapper;
import com.luckyun.region.mapper.arc.RegionArcEquipMapper;
import com.luckyun.region.model.SysUserEquip;
import com.luckyun.region.model.arc.RegionArcEquip;
import com.luckyun.region.param.SysUserEquipParam;

@Service
public class SysUserEquipService extends BaseService<SysUserEquip>{

	@Autowired
	private SysUserEquipMapper sysUserEquipMapper;
	
	@Autowired
	SysRegionHelperService sysRegionHelperService;
	
	@Autowired
	@Qualifier("regionArcEquipMapper")
	RegionArcEquipMapper arcEquipMapper;
	
	public List<SysUserEquip> readAll(){
		AuthInfo authInfo = getAuthInfo();
		return sysUserEquipMapper.findAll(authInfo.getIndocno());
	}
	
	public List<SysUserEquip> readOneByIuserId(Long iuserid){
		return sysUserEquipMapper.findAll(iuserid);
	}
	/**
	 * 更新用户的设备管辖区域
	 * @param condition 需要更新的用户以及管辖设备
	 */
	public void updateEquip(SysUserEquipParam condition) {
		AuthInfo authInfo = getAuthInfo();
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				setUserEquipRegion(condition.getRegions(),condition.getNeedCloneMembers(),authInfo);
			}
		});
		thread.start();
	}
	
	/**
	 * 查找出当前人的需要显示的父级节点
	 * @param iuserid 需要查找的用户
	 */
	public ApiResult findShowParentNodeByIuserid(Long iuserid) {
		List<SysUserEquip> sysUserEquips = 
				sysUserEquipMapper.findAll(iuserid);
		List<Long> needQuerySonNode = getNeedQuerySnode(sysUserEquips);
		List<RegionArcEquip> result = new ArrayList<RegionArcEquip>();
		RegionArcEquip condition = new RegionArcEquip();
		condition.setIparentid(0L);
		List<RegionArcEquip> rootNodes = arcEquipMapper.readAll(condition);
		result.addAll(rootNodes);
		if(needQuerySonNode != null && needQuerySonNode.size() >= 1) {
			for(Long iparentid: needQuerySonNode) {
				condition.setIparentid(iparentid);
				result.addAll(arcEquipMapper.readAll(condition));
			}
		}
		ApiResult apiResult = ApiResult.valueOf(result);
		apiResult.setMainInfo(sysUserEquips.stream().map(SysUserEquip::getIequipid).collect(Collectors.toList()));
		return apiResult;
	}
	
	private List<Long> getNeedQuerySnode(List<SysUserEquip> sysUserEquips){
		List<Long> needQuerySonNode = new ArrayList<Long>();
		for(SysUserEquip regionEquip : sysUserEquips) {
			String sidcc = regionEquip.getSequipcc();
			if(!StringUtils.isEmpty(sidcc)) {
				String[] sidccArr = sidcc.split("/");
				if(sidccArr.length >= 1) {
					for(int i = 0;i<sidccArr.length - 1;i++) {
						String sid = sidccArr[i];
						if(!StringUtils.isEmpty(sid)) {
							Long indocno = Long.valueOf(sid);
							if(!needQuerySonNode.contains(indocno)) {
								needQuerySonNode.add(indocno);
							}
						}
					}
				}
			}
		}
		return needQuerySonNode;
	}
	
	private void setUserEquipRegion(List<SysUserEquip> nRegion, List<SysAccount> members,
			AuthInfo authInfo) {
		if(nRegion != null && nRegion.size() >= 1 && members != null && members.size() >= 1) {
			for(SysAccount account : members) {
				List<SysUserEquip> oldRegion = sysUserEquipMapper.findAll(account.getIndocno());
				handleEquipRegion(account,nRegion,oldRegion,authInfo);
			}
		}
	}
	
	private void handleEquipRegion(SysAccount account,List<SysUserEquip> nRegion
			, List<SysUserEquip> oldRegion,AuthInfo authInfo) {
		List<SysUserEquip> mixRegion = CollecterMixUtils.tMix(nRegion, oldRegion, "iequipid");
		List<SysUserEquip> addRegion = CollecterMixUtils.fdiffSet(nRegion, mixRegion, "iequipid");
		List<SysUserEquip> delRegion = CollecterMixUtils.fdiffSet(oldRegion, mixRegion, "iequipid");
		sysRegionHelperService.handleEquipRegionForUser(account
				, addRegion, delRegion, authInfo);
	}
	
	@Override
	public BaseMapper<SysUserEquip> getMapper() {
		return sysUserEquipMapper;
	}

}
