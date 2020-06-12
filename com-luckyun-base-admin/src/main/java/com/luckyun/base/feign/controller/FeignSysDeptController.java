package com.luckyun.base.feign.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckyun.base.dept.mapper.SysDeptMapper;
import com.luckyun.base.dept.param.SysDeptParam;
import com.luckyun.base.dept.service.SysDeptService;
import com.luckyun.base.feign.service.FeignSysDeptService;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.model.dept.SysDept;

import lombok.extern.slf4j.Slf4j;


@RequestMapping("/feign")
@RestController
@Slf4j
public class FeignSysDeptController {
	
	@Autowired
	private SysDeptService sysDeptService;
	
	@Autowired
	private SysDeptMapper sysDeptMapper;
	
	@Autowired
	private FeignSysDeptService feignSysDeptService;

	@PostMapping("/sys/dept/jusdepts")
	public String getJusDeptList(@RequestBody AuthInfo authInfo) {
		List<SysDept> cSysDepts = authInfo.getDeptList();
		if(cSysDepts != null && cSysDepts.size() >= 1) {
			SysDeptParam condition = new SysDeptParam();
			condition.setJurisDepts(cSysDepts);
			List<SysDept> sysDepts = sysDeptService.findFeignAll(condition);
			List<String> deptIds = sysDepts.stream().map(SysDept::getIndocno).map(e -> e.toString())
					.collect(Collectors.toList());
			return String.join(",", deptIds);
		}
		return null;
	}
	
	@GetMapping("/sys/dept/userJusDepts")
	public List<SysDept> findUserJus(@RequestParam("iuserid") Long iuserid){
		//查找父级部门节点
		List<SysDept> pdepts = sysDeptMapper.findDeptJuris(iuserid);
		List<SysDept> result = new ArrayList<SysDept>();
		for(SysDept sysDept : pdepts) {
			result.addAll(sysDeptMapper.findDeptsByIparentid(sysDept.getIndocno()));
		}
		List<Long> deptIds = new ArrayList<>();
		List<SysDept> uniqueSysDepts = new ArrayList<>();
		for(SysDept sysDept : result) {
			if(!deptIds.contains(sysDept.getIndocno())) {
				uniqueSysDepts.add(sysDept);
				deptIds.add(sysDept.getIndocno());
			}
		}
		return uniqueSysDepts;
	}
	
	@GetMapping("/sys/dept/readbyindocno")
	public SysDept getDeptByIndocno(@RequestParam("indocno") Long indocno) {
		return sysDeptService.findOne(indocno);
	}
	
	/**
	 * 根据父级id查询当前用户所有管辖部门
	 * @param iparentid
	 * @return
	 */
	@GetMapping("/sys/dept/findDeptsByIparentid")
    public List<SysDept> getDeptByIparentid(@RequestParam("iparentid") Long iparentid) {
        return sysDeptService.findUserDeptByIparentid(iparentid);
    }
	
	/**
	 * 多条件查询部门
	 * @param SysDeptParam
	 * @return
	 */
	@PostMapping("/sys/dept/list")
	public List<SysDept> getDetpList(@RequestBody SysDeptParam SysDeptParam){
		return sysDeptService.findFeignAll(SysDeptParam);
	}
	
	/**
	 * 	根据部门编号获取部门信息
	 * @param scode
	 * @return
	 */
	@GetMapping("/sys/dept/readScode")
	public List<SysDept> findByScode(@RequestParam("scode") String scode) {
		SysDeptParam param = new SysDeptParam();
		param.setScode(scode);
		return this.sysDeptService.findFeignAll(param);
	}
	
	@PostMapping("/sys/dept/addSingleDept")
	public SysDept addSingleDept(@RequestBody SysDept sysDept) {
		if(sysDept.getIcompanyid() == null) {
			log.error("添加的部门所属的公司未找到!");
		}
		if(sysDept.getSregid() == null) {
			log.error("部门的添加人未找到");
		}
		sysDept.setDregt(new Date());
		sysDept.setIsort(getMaxSortByParentId(sysDept.getIparentid() != null ? sysDept.getIparentid() : 0L));
		return sysDeptService.addNoAuthInfo(sysDept);
	}
	
	@PostMapping("/sys/dept/batchAddDept")
	public int batchSingleDept(@RequestBody List<SysDept> sysDepts) {
		try {
			Long[] iparentIdList = new Long[sysDepts.size()] ;
			Integer[] isort = new Integer[sysDepts.size()];
			int j = 0;
			for(SysDept dept : sysDepts) {
				dept.setDregt(new Date());
				Long iparentId = dept.getIparentid() != null ? dept.getIparentid() : 0L;
				int parentIdIndex = listIndex(iparentIdList, iparentId);
				iparentIdList[j] = iparentId;
				if(parentIdIndex == -1) {
					isort[j] = getMaxSortByParentId(iparentId);
					dept.setIsort(getMaxSortByParentId(iparentId));
				}else {
					int sort = isort[parentIdIndex] + 1;
					isort[j] = sort;
					dept.setIsort(sort);
				}
				j++;
			}
			feignSysDeptService.batchAddDept(sysDepts);
			return 1;
		}catch (Exception e) {
			return 0;
		}
	}
	
	private int listIndex(Long[] list, Long iparentId) {
		int lastIndex = -1;
		for(int i = 0;i<list.length; i++) {
			if(iparentId.equals(list[i])) {
				lastIndex = i;
			}
		}
		return lastIndex;
	}
	
	private int getMaxSortByParentId(Long iparentId) {
		List<SysDept> depts = sysDeptMapper.findDeptsByIparentidEquips(iparentId);
		if(depts != null && depts.size() >= 1) {
			SysDept dept = depts.stream().max((a,b) -> a.getIsort() > b.getIsort() ? 1 : -1).get();
			return (dept.getIsort() + 1);
		}
		return 1;
	}
}
