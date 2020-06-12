package com.luckyun.base.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.company.param.SysCompanyParam;
import com.luckyun.base.company.service.SysCompanyService;
import com.luckyun.core.annotation.SortMaxValue;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.company.SysCompany;

@UrlByPkgController
public class SysCompanyController {

	/**
	 * @desc 获取公司数据列表
	 * @param condition 公司数据参数
	 * @return 请求返回实体信息
	 */
	@Autowired
	private SysCompanyService sysCompanyService;
	
	@GetMapping(value = "readAll")
	public List<SysCompany> readAll(SysCompanyParam condition){
		return sysCompanyService.findCompanyByCondition(condition);
	}
	
	/**
	 * @desc 获取单个公司信息
	 * @param condition 公司数据参数
	 * @return 请求返回实体信息
	 */
	@GetMapping(value = "readOne")
	public SysCompany readOne(SysCompanyParam condition) {
		return sysCompanyService.readOneByIndocno(condition.getIndocno());
	}
	
	/**
	 * @desc 切换公司
	 * @param icompanyid 公司编号
	 */
	@GetMapping(value = "noAuthReadSwitch")
	public void readSwitch(SysCompanyParam sysCompanyParam) {
		sysCompanyService.switchCompany(sysCompanyParam.getIcompanyid());
	}
	
	/**
	 * @desc 添加公司信息
	 * @param sysCompanyparam 公司数据参数
	 * @return 添加公司信息
	 * @throws Exception 
	 */
	@PostMapping(value = "add")
	@SortMaxValue
	public Integer add(@RequestBody SysCompany sysCompany) throws Exception {
		Integer addsuccess = sysCompanyService.insert(sysCompany);
		return addsuccess;
	}
	
	/**
	 * @desc 批量删除公司信息
	 * @param sysCompanyparam 公司数据参数
	 */
	@PostMapping(value = "delete")
	public void delete(@RequestBody SysCompanyParam sysCompanyParam) {
		List<SysCompany> sysCompanies = sysCompanyParam.getDelLists();
		for(SysCompany orgCompany:sysCompanies) {
			SysCompany company = sysCompanyService.readOneByIndocno(orgCompany.getIndocno());
			sysCompanyService.delete(company);
		}
	}
	/**
	 * @desc 按主键删除公司信息
	 * @param indocno主键
	 */
	@PostMapping(value = "deleteOne")
	public void deleteOne(@RequestBody SysCompanyParam sysCompanyParam) {
		SysCompany sysCompany = sysCompanyService.readOneByIndocno(sysCompanyParam.getIndocno());
		if(sysCompany != null) {
			sysCompanyService.delete(sysCompany);
		}
	}
	
	/**
	 * @desc 更新添加公司信息
	 * @param sysCompanyparam 公司数据参数
	 * @return 更新公司信息
	 */
	@PostMapping(value = "update")
	public Integer update(@RequestBody SysCompany sysCompany) {
		int updatesuccess = sysCompanyService.update(sysCompany);
		return updatesuccess;
	}
	
	/**
	 * @desc 拖拽排序公司列表
	 * @param dIndocno 拖拽对象主键
	 * @param pIndocnoa 拖拽位置上方对象主键
	 * @param pIndocnob 拖拽位置下方对象主键
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@PostMapping(value = "updateDrag")
	public void updateDrag(@RequestBody SysCompanyParam condition) 
			throws InstantiationException, IllegalAccessException
	{
		sysCompanyService.dragSysCompany(condition.getDindocno(),condition.getPindocno());
	}
	
	/**
	 * 根据用户名查询公司列表
	 * @param username
	 * @return
	 */
	@GetMapping(value = "noGetwayReadByUser")
	public List<SysCompany> readByUser(@RequestParam("username") String username) {
	    return sysCompanyService.readByUser(username);
	}
}
