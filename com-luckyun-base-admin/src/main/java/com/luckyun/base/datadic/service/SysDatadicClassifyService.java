/**
 * Project Name: com-luckyun-base
 * File Name: SysDatadicClassifyService.java
 * Package Name: com.luckyun.base.datadiccla.service
 * Date: 2019年8月9日 上午10:08:21
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.datadic.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.luckyun.base.datadic.mapper.SysDatadicClassifyMapper;
import com.luckyun.base.datadic.mapper.SysDatadicClassifyRoleMapper;
import com.luckyun.base.datadic.mapper.SysDatadicMapper;
import com.luckyun.base.datadic.param.SysDatadicClassifyParam;
import com.luckyun.core.annotation.GlobalLockByRedis;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.drag.DragHelperService;
import com.luckyun.core.exception.CustomException;
import com.luckyun.core.km.keyhelper.IDGenerate;
import com.luckyun.model.datadic.SysDatadicClassify;
import com.luckyun.model.datadic.SysDatadicClassifyRole;

/**
 * 	数据字典分类服务类
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 上午10:08:21
 */

@Service
public class SysDatadicClassifyService extends SysDatadicBaseService{

	@Autowired
	private SysDatadicClassifyMapper sysDatadicClassifyMapper;
	
	@Autowired
	private SysDatadicMapper sysDatadicMapper;
	
	@Autowired
	@Qualifier("dragHelperService")
	private DragHelperService dragHelperService;
	
	@Autowired
	private SysDatadicClassifyRoleMapper sysDatadicClassifyRoleMapper;
	private static LinkedList<SysDatadicClassify> linkClassify = new LinkedList<>();
	
	/**
	 * 
	 * 	获取字典分类列表
	 * 
	 * @param condition
	 * @return 数据字典分类集合
	 * @title TODO(请说明该接口中文名称)
	 * @author yaoxc
	 * @date 2019年8月9日 上午10:19:02
	 *
	 */
	public List<SysDatadicClassify> findAll(SysDatadicClassifyParam condition) {
		List<SysDatadicClassify> sysDatadicClassifys = this.sysDatadicClassifyMapper.findAll(condition);
//		this.recursionClassify(0L, sysDatadicClassifys);
//		linkClassify.get(linkClassify.size()-1).setSelected(true);
		return sysDatadicClassifys;
	}
	
	/**
	 * 
	 * 查找第一个最底层节点，并设置默认展开
	 * 
	 * @param iparentid
	 * @param classify
	 * @author yaoxc
	 * @date 2019年8月19日 下午9:08:27
	 *
	 */
//	private void recursionClassify(Long iparentid, List<SysDatadicClassify> sysDatadicClassifys) {
//		List<SysDatadicClassify> list = new ArrayList<SysDatadicClassify>();
//		for (SysDatadicClassify sysDatadicClassify : sysDatadicClassifys) {
//			if(sysDatadicClassify.getIparentid().equals(iparentid)) {
//				list.add(sysDatadicClassify);
//			}
//		}
//		if(!CollectionUtils.isEmpty(list)) {
//			SysDatadicClassify sysDatadicClassify = list.get(0);
//			linkClassify.add(sysDatadicClassify);
//			this.recursionClassify(sysDatadicClassify.getIndocno(), sysDatadicClassifys);
//			sysDatadicClassify.setExpand(true);
//		}
//	}
	
	/**
	 * 
	 * <p>Title: findDatadicByParent</p>  
	 * <p>Description: 根据当前别名获取子分类</p>  
	 * @param condition
	 * @return
	 */
	public List<SysDatadicClassify> findDatadicByParent(SysDatadicClassifyParam condition) {
		return sysDatadicClassifyMapper.findDatadicByParent(condition);
	}
	
	
	
	/**
	 * 
	 * 	查看数据字典分类详情
	 * 
	 * @param sysDatadicClassify
	 * @return 当前数据字典分类对象
	 * @author yaoxc
	 * @date 2019年8月9日 上午10:51:04
	 *
	 */
	public SysDatadicClassify findOne(SysDatadicClassify sysDatadicClassify) {
		SysDatadicClassify classify = this.select(sysDatadicClassify);
		List<SysDatadicClassifyRole> classifyRoles
				= sysDatadicClassifyRoleMapper.findRoleByIclassifyId(classify.getIndocno());
		if(!CollectionUtils.isEmpty(classifyRoles)) {
			classify.setRoleList(classifyRoles);
		}
		return classify;
	}
	
	/**
	 * 
	 * 	添加数据字典分类
	 * 
	 * @param sysDatadicClassify
	 * @return 添加后的数据字典对象
	 * @author yaoxc
	 * @throws Exception 
	 * @date 2019年8月9日 上午10:24:22
	 *
	 */
	@TransactionalException
	public SysDatadicClassify addClassify(SysDatadicClassify sysDatadicClassify) throws Exception {
		String snamealias = sysDatadicClassify.getSnamealias();
		if(!StringUtils.isEmpty(snamealias)) {
			Integer snamealiasCount = 
					this.sysDatadicClassifyMapper.findNameAliasCount(snamealias);
			if(snamealiasCount > 0) {
				throw new Exception("base.dic.namealias.exist.error");
			}
		}else {
			throw new Exception("base.dic.namealias.none.error");
		}
		// 插入sidcc
		Long iparentid = sysDatadicClassify.getIparentid() != null ? sysDatadicClassify.getIparentid() : 0;
		sysDatadicClassify.setIparentid(iparentid);
		sysDatadicClassify.setIndocno(IDGenerate.getKey(sysDatadicClassify));
		sysDatadicClassify = this.setSidcc(sysDatadicClassify);
		this.insert(sysDatadicClassify);
		//添加分类/角色关系
		addClassifyRole(sysDatadicClassify, "add");
		return sysDatadicClassify;
	}
	
	/**
	 * 
	 * 	更新数据字典
	 * 
	 * @param sysDatadicClassify
	 * @return 更新后的数据字典对象
	 * @author yaoxc
	 * @date 2019年8月9日 上午10:47:28
	 *
	 */
	@TransactionalException
	public SysDatadicClassify updateClassify(SysDatadicClassify sysDatadicClassify) {
		try {
			this.update(sysDatadicClassify);
			//添加分类/角色关系
			addClassifyRole(sysDatadicClassify, "update");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysDatadicClassify;
	}
	
	/**
	 * 
	 * 	删除数据字典分类
	 * 
	 * @param sysDatadicClassify
	 * @author yaoxc
	 * @throws Exception 
	 * @date 2019年8月9日 上午10:49:34
	 *
	 */
	@TransactionalException
	public void deleteClassify(SysDatadicClassifyParam sysDatadicClassifyParam) throws Exception {
		List<SysDatadicClassify> delList = sysDatadicClassifyParam.getDelList();
		if(CollectionUtils.isEmpty(delList)) {
			throw new Exception("base.dic.delclaify.error");
		}
		//判断是否存在未删除的字典数据
		for (SysDatadicClassify sysDatadicClassify : delList) {
			Integer datadicCount 
					= this.sysDatadicMapper.findByIclassifyCount(sysDatadicClassify.getIndocno());
			if(datadicCount != 0) {
				throw new Exception("base.dic.datadic.exist.error");
			}
			this.delete(sysDatadicClassify);
			sysDatadicClassifyRoleMapper.deleteByIclassifyId(sysDatadicClassify.getIndocno());
		}
	}
	
	/**
	 * 判断对应分类下是否存在字典
	 * @param param
	 */
	public boolean addExist(SysDatadicClassifyParam param) {
		boolean flag = false;
		Long indocno = param.getIndocno();
		if(null == indocno) {
			throw new CustomException("base.dic.exist.error");
		}
		Integer dicCount = this.sysDatadicMapper.findByIclassifyCount(indocno);
		if(dicCount == 0) {
			flag = true;
		}
		return flag;
	}
	
	// 插入sidcc
    private SysDatadicClassify setSidcc(SysDatadicClassify sysDatadicClassify) {
        if(sysDatadicClassify.getIparentid() != null && sysDatadicClassify.getIparentid() != 0) {
            SysDatadicClassify pSysDatadicClassify = new SysDatadicClassify();
            pSysDatadicClassify.setIndocno(sysDatadicClassify.getIparentid());
            pSysDatadicClassify = this.select(pSysDatadicClassify);
            sysDatadicClassify.setSidcc(pSysDatadicClassify.getSidcc()  + sysDatadicClassify.getIndocno() + "/");
        }else {
            sysDatadicClassify.setSidcc("/" + sysDatadicClassify.getIndocno() + "/");
        }
        return sysDatadicClassify;
    }
    // 重置所有sidcc
    @TransactionalException
    public void resetSidcc() {
        SysDatadicClassifyParam param = new SysDatadicClassifyParam();
        param.setIparentid(0L);
        List<SysDatadicClassify> sysDatadicClassifys = sysDatadicClassifyMapper.findAll(param);
        for(SysDatadicClassify sysDatadicClassify : sysDatadicClassifys) {
            sysDatadicClassify.setSidcc("/"+sysDatadicClassify.getIndocno()+"/");
            sysDatadicClassifyMapper.update(sysDatadicClassify);
            resetSidcc(sysDatadicClassify);
        }
    }
    private void resetSidcc(SysDatadicClassify pSysDatadicClassify) {
        SysDatadicClassifyParam param = new SysDatadicClassifyParam();
        param.setIparentid(pSysDatadicClassify.getIndocno());
        List<SysDatadicClassify> sysDatadicClassifys = sysDatadicClassifyMapper.findAll(param);
        for(SysDatadicClassify sysDatadicClassify : sysDatadicClassifys) {
            sysDatadicClassify.setSidcc(pSysDatadicClassify.getSidcc()+sysDatadicClassify.getIndocno()+"/");
            sysDatadicClassifyMapper.update(sysDatadicClassify);
            resetSidcc(sysDatadicClassify);
        }
    }
	
	@TransactionalException
	@GlobalLockByRedis
	public void dragSort(SysDatadicClassifyParam condition) {
		SysDatadicClassify pDatadicClassify = sysDatadicClassifyMapper.findOne(condition.getPindocno());
		SysDatadicClassify dDatadicClassify = sysDatadicClassifyMapper.findOne(condition.getDindocno());
		dragHelperService.drag(pDatadicClassify, dDatadicClassify, true, "iparentid", dDatadicClassify.getIparentid());
	}
	
	/**
	 * TODO(请说明该方法的实现功能).
	 * 
	 * @return
	 * @see com.luckyun.core.data.BaseService#getMapper()
	 */
	@Override
	public BaseMapper<SysDatadicClassify> getMapper() {
		return sysDatadicClassifyMapper;
	}

}
