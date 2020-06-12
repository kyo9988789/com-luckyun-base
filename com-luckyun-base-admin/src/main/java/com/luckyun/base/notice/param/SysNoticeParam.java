/**
 * Project Name: com-luckyun-base
 * File Name: sysNoticeParam.java
 * Package Name: com.luckyun.base.notice.param
 * Date: 2019年8月8日 下午5:42:07
 * Copyright (c) 2019, luckyun All Rights Reserved.
 */
package com.luckyun.base.notice.param;

import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.luckyun.model.notice.SysNotice;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jackie
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysNoticeParam extends SysNotice{
    /**
     * 需要删除的消息对象
     */
    private List<SysNotice> delList;
    
    private Long iuserid;
    
    private Date nowdate;
    
    private Long icreater;
    
    private String sname;
    
    private Integer iread;
    
    public void setSdstart(String sdstart) {
    	if(!StringUtils.isEmpty(sdstart)) {
	    	Long ldstart = Long.valueOf(sdstart);
	    	super.setDstart(new Date(ldstart));
    	}
    }
    
    public void setSdend(Long sdend) {
    	if(!StringUtils.isEmpty(sdend)) {
    		Long ldend = Long.valueOf(sdend);
	    	super.setDend(new Date(ldend));
    	}
    }
}
