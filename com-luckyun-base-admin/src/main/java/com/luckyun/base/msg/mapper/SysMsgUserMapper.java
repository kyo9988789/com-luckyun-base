/**
 * Project Name: com-luckyun-base
 * File Name: SysMsgUserMapper.java
 * Package Name: com.luckyun.base.msg.mapper
 * Date: 2019年8月9日 下午3:49:33
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.msg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.msg.SysMsgUser;

/**
 *	 内部交流人员Mapper
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月9日 下午3:49:33
 */

@Repository
public interface SysMsgUserMapper extends BaseMapper<SysMsgUser>{

	/**
	 * 
	 * @param ilinkno
	 * @return
	 */
	public List<SysMsgUser> findByIlinknoAndUser(@Param("ilinkno") Long ilinkno,@Param("iuserid") Long iuserid);
	
	/**
	 * 获取接收人
	 * @param ilinkno 消息主键
	 * @param iread 1、已读 2、未读
	 * @return
	 */
	public List<SysMsgUser> findMembers(@Param("ilinkno") Long ilinkno,@Param("iread") Integer iread);
	
}
