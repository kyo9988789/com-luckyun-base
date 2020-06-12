package com.luckyun.base.datadic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.luckyun.base.datadic.param.SysDatadicParam;
import com.luckyun.base.datadic.service.SysDatadicService;
import com.luckyun.core.annotation.Pageable;
import com.luckyun.core.annotation.SortMaxValue;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.core.api.annotation.Api;
import com.luckyun.model.datadic.SysDatadic;

/**
 * 
 * 	数据字典接口
 * 
 * @action /sys/datadic
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月8日 下午4:43:04
 */

@Api(name = "数据字典接口", author = "姚小川", regdate = "2019-08-25")
@UrlByPkgController
public class SysDatadicController {

	@Autowired
	private SysDatadicService sysDatadicService;
	
	/**
	 * 
	 * 	获取数据字典列表
	 * 
	 * @return List<SysDatadic>
	 * @title 获取数据字典列表
	 * @author yaoxc
	 * @date 2019年8月8日 下午5:46:49
	 * @param sysDatadicParam|数据字典参数|SysDatadicParam|不必填
	 * @param sysDatadicParam.snamealias|类型别名|String|不必填
	 * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
	 * @resqParam detail|接口返回数据|Object|必填
	 * @resqParam operates|用户操作权限|String|必填
	 * @resqParam pagination|分页信息|String|必填
	 * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
	 * @requestType get
	 *
	 */
	@Pageable
	@GetMapping(value = "/readAll")
	public List<SysDatadic> readall(SysDatadicParam sysDatadicParam) {
		return this.sysDatadicService.findAll(sysDatadicParam);
	}
	
	/**
	 * 
	 * 	获取数据字典详情
	 * 
	 * @return SysDatadic
	 * @title 获取数据字典详情
	 * @author yaoxc
	 * @date 2019年8月8日 下午5:46:49
	 * @param sysDatadicParam|数据字典参数|SysDatadicParam|不必填
	 * @param sysDatadicParam.indocno|数据字典编号|Long|必填
	 * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
	 * @resqParam detail|接口返回数据|Object|必填
	 * @resqParam operates|用户操作权限|String|必填
	 * @resqParam pagination|分页信息|String|必填
	 * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
	 * @requestType get
	 *
	 */
	@GetMapping(value = "/readOne")
	public SysDatadic readone(SysDatadic sysDatadic) {
		return this.sysDatadicService.findOne(sysDatadic);
	}
	
	/**
	 * 
	 * 	添加数据字典
	 * 
	 * @return SysDatadic
	 * @title 添加数据字典
	 * @author yaoxc
	 * @date 2019年8月8日 下午5:46:49
	 * @param sysDatadicParam|数据字典参数|SysDatadicParam|不必填
	 * @param sysDatadicParam.sname|字典名称|String|必填
	 * @param sysDatadicParam.sfactvalue|实际值|String|必填
	 * @param sysDatadicParam.istate|状态|Integer|必填
	 * @throws Exception 
	 * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
	 * @resqParam detail|接口返回数据|Object|必填
	 * @resqParam operates|用户操作权限|String|必填
	 * @resqParam pagination|分页信息|String|必填
	 * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
	 * @requestType Post
	 *
	 */
	@PostMapping(value = "/add")
	@SortMaxValue(iparentidnm = "iclassifyid")
	public SysDatadic add(@RequestBody SysDatadic sysDatadic) throws Exception {
		return this.sysDatadicService.addDatadic(sysDatadic);
	}
	
	/**
	 * 
	 * 	更新数据字典
	 * 
	 * @return SysDatadic
	 * @title 更新数据字典
	 * @author yaoxc
	 * @date 2019年8月8日 下午5:46:49
	 * @param sysDatadicParam|数据字典参数|SysDatadicParam|不必填
	 * @param sysDatadicParam.sname|字典名称|String|必填
	 * @param sysDatadicParam.sfactvalue|实际值|String|必填
	 * @param sysDatadicParam.istate|状态|Integer|必填
	 * @throws Exception 
	 * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
	 * @resqParam detail|接口返回数据|Object|必填
	 * @resqParam operates|用户操作权限|String|必填
	 * @resqParam pagination|分页信息|String|必填
	 * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
	 * @requestType Post
	 *
	 */
	@PostMapping(value = "/update")
	public SysDatadic update(@RequestBody SysDatadic sysDatadic) throws Exception {
		return this.sysDatadicService.updateDatadic(sysDatadic);
	}
	
	/**
	 * 
	 * 	删除数据字典
	 * 
	 * @return SysDatadic
	 * @title 删除数据字典
	 * @author yaoxc
	 * @date 2019年8月8日 下午5:46:49
	 * @param sysDatadicParam|数据字典参数|SysDatadicParam|不必填
	 * @param sysDatadicParam.indocno|字典编号|Long|必填
	 * @throws Exception 
	 * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
	 * @resqParam detail|接口返回数据|Object|必填
	 * @resqParam operates|用户操作权限|String|必填
	 * @resqParam pagination|分页信息|String|必填
	 * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
	 * @requestType Post
	 *
	 */
	@PostMapping(value = "/delete")
	public void delete(@RequestBody SysDatadicParam sysDatadicParam) throws Exception {
		this.sysDatadicService.deleteDatadic(sysDatadicParam);
	}
	
	@PostMapping("updateDrag")
	public void dragSort(@RequestBody SysDatadicParam sysDatadicParam) {
		sysDatadicService.dragSort(sysDatadicParam);
	}
}
