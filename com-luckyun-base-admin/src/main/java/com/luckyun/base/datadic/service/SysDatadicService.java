package com.luckyun.base.datadic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.luckyun.base.datadic.mapper.SysDatadicMapper;
import com.luckyun.base.datadic.param.SysDatadicParam;
import com.luckyun.core.annotation.GlobalLockByRedis;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.drag.DragHelperService;
import com.luckyun.core.tool.PinYinHelperUtils;
import com.luckyun.model.datadic.SysDatadic;

/**
 * 
 * 	数据字典服务类
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月8日 下午4:09:41
 */

@Service
public class SysDatadicService extends BaseService<SysDatadic>{

	@Autowired
	private SysDatadicMapper sysDatadicMapper;
	
	@Autowired
	@Qualifier("dragHelperService")
	private DragHelperService dragHelperService;
	
	/**
	 * 
	 * 	获取数据字典列表
	 * 
	 * @param sysDatadicParam
	 * @return
	 * @author yaoxc
	 * @date 2019年8月8日 下午4:15:59
	 *
	 */
	public List<SysDatadic> findAll(SysDatadicParam sysDatadicParam) {
		return this.sysDatadicMapper.findAll(sysDatadicParam);
	}
	
	/**
	 * 
	 * 	获取数据字典详情
	 * 
	 * @param sysDatadic
	 * @return 数据字典对象
	 * @author yaoxc
	 * @date 2019年8月8日 下午4:22:26
	 *
	 */
	public SysDatadic findOne(SysDatadic sysDatadic) {
		return this.select(sysDatadic);
	}
	
	/**
	 * 
	 * 	添加数据字典
	 * 
	 * @param sysDatadic
	 * @return 数据字典对象
	 * @author yaoxc
	 * @throws Exception 
	 * @date 2019年8月8日 下午4:24:29
	 *
	 */
	@TransactionalException
	public SysDatadic addDatadic(SysDatadic sysDatadic) throws Exception {
		sysDatadic.setSpinyinsimple(PinYinHelperUtils.getFirstSpell(sysDatadic.getSname()));
		sysDatadic.setSpinyinfull(PinYinHelperUtils.getPingYin(sysDatadic.getSname()));
		List<SysDatadic> sysDatadics = sysDatadicMapper.findBySnameAndSfactvalue(sysDatadic.getSname(), sysDatadic.getSfactvalue(), sysDatadic.getIclassifyid());
		if(!CollectionUtils.isEmpty(sysDatadics)) {
		    throw new Exception("base.dic.sname.sfactvalue.exist.error");
		}
		this.insert(sysDatadic);
		return sysDatadic;
	}
	
	/**
	 * 
	 * 	更新数据字典
	 * 
	 * @param sysDatadic
	 * @return 数据字典对象
	 * @author yaoxc
	 * @throws Exception 
	 * @date 2019年8月8日 下午4:40:16
	 *
	 */
	@TransactionalException
	public SysDatadic updateDatadic(SysDatadic sysDatadic) throws Exception {
		if(!StringUtils.isEmpty(sysDatadic.getSname())) {
			sysDatadic.setSpinyinsimple(PinYinHelperUtils.getFirstSpell(sysDatadic.getSname()));
			sysDatadic.setSpinyinfull(PinYinHelperUtils.getPingYin(sysDatadic.getSname()));
		}
		List<SysDatadic> sysDatadics = sysDatadicMapper.findBySnameAndSfactvalue(sysDatadic.getSname(),sysDatadic.getSfactvalue(), sysDatadic.getIclassifyid());
        if(!CollectionUtils.isEmpty(sysDatadics)) {
            for(SysDatadic datadic:sysDatadics) {
                if(datadic.getIndocno()!=sysDatadic.getIndocno()) {
                    throw new Exception("base.dic.sname.sfactvalue.exist.error");
                }
            }
        }
		this.update(sysDatadic);
		return sysDatadic;
	}
	
	/**
	 * 
	 * 	删除数据字典
	 * 
	 * @param sysDatadic
	 * @author yaoxc
	 * @throws Exception 
	 * @date 2019年8月8日 下午4:42:04
	 *
	 */
	@TransactionalException
	public void deleteDatadic(SysDatadicParam sysDatadicParam) throws Exception {
		List<SysDatadic> delList = sysDatadicParam.getDelList();
		if(CollectionUtils.isEmpty(delList)) {
			throw new Exception("base.dic.del.error");
		}
		for (SysDatadic sysDatadic : delList) {
			this.delete(sysDatadic);
		}
	}
	
	@TransactionalException
	@GlobalLockByRedis
	public void dragSort(SysDatadicParam condition) {
		SysDatadic psDatadic = sysDatadicMapper.findOne(condition.getPindocno());
		SysDatadic dsDatadic = sysDatadicMapper.findOne(condition.getDindocno());
		dragHelperService.drag(psDatadic, dsDatadic, true, "iclassifyid", dsDatadic.getIclassifyid());
	}
	
	@Override
	public BaseMapper<SysDatadic> getMapper() {
		return sysDatadicMapper;
	}

}
