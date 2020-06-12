package com.luckyun.base.datadic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.datadic.param.SysDatadicParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.datadic.SysDatadic;

/**
 * 
 * 	数据字典Mapper
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月8日 下午3:34:01
 */

@Repository
public interface SysDatadicMapper extends BaseMapper<SysDatadic>{

	/**
	 * 
	 * 	获取数据字典列表
	 * 
	 * @return 数据字典列表
	 * @author yaoxc
	 * @date 2019年8月8日 下午3:44:33
	 *
	 */
	public List<SysDatadic> findAll(@Param("sysDatadicParam") SysDatadicParam sysDatadicParam);
	
	/**
	 * 
	 * 	查询对应字典分类下是否存在字典数据
	 * 
	 * @param iclassifyid
	 * @return 字典数量
	 * @author yaoxc
	 * @date 2019年8月20日 上午9:49:29
	 *
	 */
	public Integer findByIclassifyCount(@Param("iclassifyid") Long iclassifyid);
	
	/**
	 * 根据编号获取数据字典数据
	 * @param indocno 数据字典编号
	 * @return 数据字典数据
	 */
	SysDatadic findOne(@Param("indocno") Long indocno);
	
	/**
	 * 根据名称或实际值获取字典
	 * @param sname 字典名称
	 * @param sfactvalue 实际值
	 * @param iclassifyid 类别id
	 * @return
	 */
	public List<SysDatadic> findBySnameAndSfactvalue(@Param("sname") String sname,@Param("sfactvalue") String sfactvalue ,@Param("iclassifyid") Long iclassifyid);
	
}
