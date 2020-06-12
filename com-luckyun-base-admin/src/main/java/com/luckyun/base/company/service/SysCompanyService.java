package com.luckyun.base.company.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.luckyun.base.company.mapper.SysCompanyMapper;
import com.luckyun.base.company.param.SysCompanyParam;
import com.luckyun.core.annotation.GlobalLockByRedis;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.drag.DragHelperService;
import com.luckyun.core.tool.OssPathHelperUtils;
import com.luckyun.model.company.SysCompany;

@Service
public class SysCompanyService extends BaseService<SysCompany>{
	
	@Autowired
	private SysCompanyMapper sysCompanyMapper;
	
	@Autowired
	@Qualifier("dragHelperService")
	private DragHelperService dragHelperService;
	
	@Autowired
	private OssPathHelperUtils ossPathHelperUtils;
	
	/**
	 * 根据条件获取对应的公司数据
	 * @param condition 公司过滤条件
	 * @return 指定的公司列表
	 */
	@GlobalLockByRedis(comppropty = {"sname"},maxrelease = 10)
	public List<SysCompany> findCompanyByCondition(SysCompanyParam condition) {
		List<SysCompany> result = sysCompanyMapper.findAll(condition);
		return result;
	}
	
	/**
	 * 根据编号获取对应的公司数据
	 * @param indocno 主键
	 * @return 指定的公司信息
	 */
	@GlobalLockByRedis(basicpropty = {"indocno"})
	public SysCompany readOneByIndocno(Long indocno) {
		SysCompany result = sysCompanyMapper.findOne(indocno);
		String sphoto = result.getSlogo() != null ? result.getSlogo() : "";
		if(!StringUtils.isEmpty(sphoto)) {
			result.setNspath(ossPathHelperUtils.generateShowImgNoAuth(getAuthInfo().getSloginid()
					, "base", sphoto));
		}
		return result;
	}
	
	public List<SysCompany> readByUser(String username){
	    List<SysCompany> result = sysCompanyMapper.findCompanyByUserName(username);
	    return result;
	}
	
	/**
	 * 切换公司
	 * @param icompanyid 公司编号
	 */
	public void switchCompany(Long icompanyid) {
		
	}
	
	/**
	 * 拖拽左边侧栏数据
	 * @param dObj 拖拽的对象
	 * @param pObj 拖拽的定位对象
	 * @param direction 拖拽方位
	 * @return 拖拽后对象的isort
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@GlobalLockByRedis
	@TransactionalException
	public void dragSysCompany(Long dIndocno, Long pIndocno){
		SysCompany dObj = sysCompanyMapper.findOne(dIndocno);
		SysCompany pObj = sysCompanyMapper.findOne(pIndocno);
		dragHelperService.drag(pObj, dObj);
	}
	
	/**
	 * @desc 刷新所有数据的isort
	 */
	public void refresh() {
		
	}
	
	@Override
	public BaseMapper<SysCompany> getMapper() {
		return sysCompanyMapper;
	}
}
