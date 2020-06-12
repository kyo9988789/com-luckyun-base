/**
 * Project Name: com-luckyun-base
 * File Name: SysMsgMapper.java
 * Package Name: com.luckyun.base.msg.mapper
 * Date: 2019年8月9日 下午3:49:00
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.msg.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.msg.param.SysMsgParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.msg.SysMsg;

/**
 * 	内部交流Mapper
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 下午3:49:00
 */

@Repository
public interface SysMsgMapper extends BaseMapper<SysMsg>{

	/**
	 * 获取我的消息
	 * @param condition
	 * @param iuserid 当前用户id
	 * @param nowdate 当前时间
	 * @return
	 */
	public List<SysMsg> findAllMyMsg(@Param("condition") SysMsgParam condition,@Param("iuserid") Long iuserid,@Param("nowdate") Date nowdate);
	
	/**
	 * 获取我发送的消息
	 * @param condition
	 * @param iuserid
	 * @return
	 */
	public List<SysMsg> findAllMySendMsg(@Param("condition") SysMsgParam condition,@Param("iuserid") Long iuserid);
	
	/**
	* 获取我的消息(根据业务聚合)
     * @param condition
     * @param iuserid 当前用户id
     * @param nowdate 当前时间
     * @return
     */
    public List<SysMsg> findAllMyMsgGroup(@Param("condition") SysMsgParam condition,@Param("iuserid") Long iuserid,@Param("nowdate") Date nowdate);
 
	/**
	 * 获取消息详情
	 * @param indocno 消息id
	 * @param iuserid 当前用户id
	 * @param nowdate 当前时间
	 * @return
	 */
	public SysMsg findOne(@Param("indocno") Long indocno,@Param("iuserid") Long iuserid,@Param("nowdate") Date nowdate);
	
	/**
	 * 根据id查找当前用户发送的消息
	 * @param indocno
	 * @return
	 */
	public SysMsg findOneBySendUser(@Param("indocno") Long indocno,@Param("iuserid") Long iuserid);
	
	/**
	 * 当前消息批量阅读
	 * @param ilinkno 消息编号
	 */
	void updateByIlinknoRead(@Param("ilinkno") Long ilinkno);
}
