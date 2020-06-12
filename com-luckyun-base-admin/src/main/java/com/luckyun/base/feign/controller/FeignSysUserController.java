package com.luckyun.base.feign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckyun.base.feign.service.FeignSysAccountService;
import com.luckyun.base.user.param.SysUserParam;
import com.luckyun.base.user.service.SysUserService;
import com.luckyun.core.annotation.Pageable;
import com.luckyun.core.response.ApiResult;
import com.luckyun.model.user.SysAccount;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/feign")
@RestController
@Slf4j
public class FeignSysUserController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private FeignSysAccountService feignSysAccountService;
	
	@GetMapping(value="/sys/user/readbyid")
	public SysAccount provideFeiSysUserByIndocno(@RequestParam("indocno") Long indocno) {
		SysUserParam condition = new SysUserParam();
		condition.setIndocno(indocno);
		SysAccount sysUser = sysUserService.findUserInfo(condition);
		return sysUser;
	}
	
	@GetMapping(value = "/sys/user/readbyiroleid")
	public List<SysAccount> provideFeignSysUserByIroleid(@RequestParam("iroleid") Long iroleid){
		SysUserParam condition = new SysUserParam();
		condition.setIroleid(iroleid);
		return sysUserService.findUserByCondition(condition);
	}
	
	@GetMapping(value = "/sys/user/readbyirolety")
	public List<SysAccount> provideFeignSysUserByIroleTyid(@RequestParam("iroletyid") Long iroletyid){
		SysUserParam condition = new SysUserParam();
		condition.setIroletypeid(iroletyid);
		return sysUserService.findUserByCondition(condition);
	}
	
	@GetMapping(value="/sys/user/readbysloginid")
	public SysAccount provideFeignSysUserBySloginid(@RequestParam("sloginid") String sloginid){
		return sysUserService.findBySloginid(sloginid);
	}
	
	@GetMapping(value = "/sys/user/readAccountPwd")
	public SysAccount providerFeignContainerPwd(@RequestParam(value = "sloginid",defaultValue = "") String sloginid,
			@RequestParam(value = "indocno",defaultValue = "0") Long indocno) {
		if(!StringUtils.isEmpty(sloginid)) {
			SysAccount account = sysUserService.findBySloginid(sloginid);
			account.setSpwd(account.getSpassword());
			return account;
		}else if(!indocno.equals(0L)) {
			SysAccount saParam = new SysAccount();
			saParam.setIndocno(indocno);
			SysAccount account = sysUserService.select(saParam);
			account.setSpwd(account.getSpassword());
			return account;
		}
		return new SysAccount();
	}
	
	@GetMapping(value="/sys/user/upuserdatepassword")
	public SysAccount provideUpdatePassword(@RequestParam("indocno") Long indocno,@RequestParam("password") String password){
		return sysUserService.updatePassWord(indocno, password);
	}
	
	@PostMapping(value="/sys/user/asysdaccountd")
	public SysAccount provideAddSysAccunt(@RequestBody SysAccount entity) throws Exception {
		if(StringUtils.isEmpty(entity.getSpassword())) {
			entity.setSpassword("123456");
		}
		if(!StringUtils.isEmpty(entity.getSpwd())) {
		    entity.setSpassword(entity.getSpwd());
		}
		return sysUserService.add(entity, null);
	}
	
	@PostMapping(value="/sys/user/batchSysAccount")
	public int batchAddSysAccount(@RequestBody List<SysAccount> sysAccounts) {
		try {
			feignSysAccountService.batchSysAccount(sysAccounts);
			return 1;
		}catch(Exception e) {
			log.error("批量添加用户出错,错误信息:" + e.getMessage(), e);
			return 0;
		}
	}
	
	@PostMapping(value="/sys/user/readbycomplex")
	public List<SysAccount> provideFeignComplexQuery(@RequestBody SysUserParam condition){
		return sysUserService.findUserByCondition(condition);
	}
	
	@GetMapping(value="/sys/user/readpagebycomplex")
	@Pageable
	public ApiResult provideFeignPageComplexQuery(SysUserParam condition){
		List<SysAccount> sysAccounts = sysUserService.findUserByCondition(condition);
		ApiResult apiResult = ApiResult.valueOf(sysAccounts);
		return apiResult;
	}
}