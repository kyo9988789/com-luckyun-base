package com.luckyun.base.collect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.luckyun.base.collect.param.SysCollectParam;
import com.luckyun.base.collect.service.SysCollectService;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.collect.SysCollect;

/**
 * 
 * 	收藏管理接口
 * 
 * @action /sys/collect
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月7日 下午3:23:13
 */

@UrlByPkgController
public class SysCollectController {

	@Autowired
	private SysCollectService sysCollectService;
	
	/**
	 * 
	 * 	获取收藏列表
	 * 
	 * @return 收藏列表集合
	 * @title 获取收藏列表
	 * @author yaoxc
	 * @date 2019年8月7日 下午6:21:05
	 * @param condition|收藏接口参数|SysCollectParam|不必填
	 * @param condition.iuserid|用户编号|Long|不必填
	 * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
	 * @resqParam detail|接口返回数据|Object|必填
	 * @resqParam operates|用户操作权限|String|必填
	 * @resqParam pagination|分页信息|String|必填
	 * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
	 * @requestType get
	 *
	 */
	@GetMapping(value = "/readAll")
	public List<SysCollect> readall(SysCollectParam condition) {
		return this.sysCollectService.findAll(condition);
	}
	
	/**
	 * 
	 * 	添加收藏
	 * 
	 * @return
	 * @title 添加收藏
	 * @author yaoxc
	 * @date 2019年8月7日 下午6:23:20
	 * @param sysCollect|收藏实体|SysCollect|必填
	 * @param sysCollect.iuserid|用户编号|Long|必填
	 * @param sysCollect.imoduleid|模块编号|Long|必填
	 * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
	 * @resqParam detail|接口返回数据|Object|必填
	 * @resqParam operates|用户操作权限|String|必填
	 * @resqParam pagination|分页信息|String|必填
	 * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
	 * @requestType Post
	 *
	 */
	@PostMapping(value = "/add")
	public SysCollect add(@RequestBody SysCollect sysCollect) {
		return this.sysCollectService.addCollect(sysCollect);
	}
	
	/**
	 * 
	 * 	删除收藏
	 * 
	 * @title 删除收藏
	 * @author yaoxc
	 * @date 2019年8月7日 下午6:24:40
	 * @param sysCollect|收藏实体|SysCollect|必填
	 * @param sysCollect.indocno|收藏编号|Long|必填
	 * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
	 * @resqParam detail|接口返回数据|Object|必填
	 * @resqParam operates|用户操作权限|String|必填
	 * @resqParam pagination|分页信息|String|必填
	 * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
	 * @requestType Post
	 *
	 */
	@PostMapping(value = "delete")
	public void delete(@RequestBody SysCollect sysCollect) {
		this.sysCollectService.deleteCollect(sysCollect);
	}
	
}
