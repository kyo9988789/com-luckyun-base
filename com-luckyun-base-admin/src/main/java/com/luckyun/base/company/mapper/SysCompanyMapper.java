package com.luckyun.base.company.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.company.param.SysCompanyParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.company.SysCompany;

@Repository
public interface SysCompanyMapper extends BaseMapper<SysCompany>{
	List<SysCompany> findAll(@Param("condition") SysCompanyParam condition);
	
	SysCompany findOne(@Param("indocno") Long indocno);
	
	List<SysCompany> findAllByIsort();
	
	List<SysCompany> findByIsort(@Param("username") String username);
	
	/**
	 * 根据用户登录账号查询当前用户所属的公司
	 * @param username 登录账号
	 * @return 查询用户的所属公司
	 */
	List<SysCompany> findCompanyByUserName(@Param("sloginid") String username);
}
