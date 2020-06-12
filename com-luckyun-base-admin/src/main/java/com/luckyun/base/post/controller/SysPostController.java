package com.luckyun.base.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.post.param.SysPostParam;
import com.luckyun.base.post.service.SysPostService;
import com.luckyun.core.annotation.Pageable;
import com.luckyun.core.annotation.SortMaxValue;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.post.SysPost;

/**
 *  岗位控制器
 * @author ZT
 */
@UrlByPkgController
public class SysPostController {
	
	@Autowired
	private SysPostService sysPostService;
	
	/**
	 * @describe 获取岗位数据列表
	 * @param condition 岗位数据参数
	 * @return 请求返回岗位信息
	 */
	@GetMapping(value = "readAll")
	@Pageable
	public List<SysPost> readAll(SysPostParam condition){
		return sysPostService.findAll(condition);
	}
	
	/**
	 * 获取单个岗位信息
	 * @param indocno 岗位主键
	 * @return 请求返回岗位信息
	 */
	@RequestMapping(value = "readOne", method = RequestMethod.GET)
	public SysPost readOne(@RequestParam ("indocno") Long indocno) {
		return sysPostService.readOneByIndocno(indocno);
	}
	
	/**
	 * 添加岗位信息
	 * @param sysPostParam 岗位数据参数
	 * @return 添加是否成功
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@SortMaxValue(iparentidnm="iclassifyid")
	public SysPost add(@RequestBody SysPost sysPost) throws Exception{
		return sysPostService.add(sysPost,null);
	}
	
	/**
	 * 修改岗位信息
	 * @param sysPostParam 岗位数据参数
	 * @return 修改是否成功
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public SysPost update(@RequestBody SysPost sysPost) {
		return sysPostService.update(sysPost,null);
	}
	
	/**
	 * @desc 拖拽排序操作数据列表
	 * @param dIndocno 拖拽对象主键
	 * @param pIndocnoa 拖拽位置上方对象主键
	 * @param pIndocnob 拖拽位置下方对象主键
	 */
	@RequestMapping(value = "updateDrag", method = RequestMethod.POST)
	public void updateDrag(@RequestBody SysPostParam condition) {
		sysPostService.dragSysPost(condition);
	}
	
	/**
	 * 删除岗位信息
	 * @param sysPostParam 岗位数据参数
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody SysPostParam sysPostParam) {
		sysPostService.deleteLogic(sysPostParam);
	}
	
	/**
	 * 克隆岗位信息
	 * @param indocno 被克隆数据主键
	 * @return 是否克隆成功
	 */
	@RequestMapping(value = "clone", method = RequestMethod.POST)
	public Integer clone(@RequestParam("indocno") Long indocno){
		Integer cloneSuccess = sysPostService.clone(indocno);
		return cloneSuccess;
	}
	
	@GetMapping(value = "/noAuthReset")
	public void resetSort(@RequestParam("iclassifyid") Long iclassifyid) {
		sysPostService.resetSort(iclassifyid);
	}
}
