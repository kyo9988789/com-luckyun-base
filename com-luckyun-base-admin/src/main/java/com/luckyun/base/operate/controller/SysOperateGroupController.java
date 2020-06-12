package com.luckyun.base.operate.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.luckyun.base.operate.param.SysOperateGroupParam;
import com.luckyun.base.operate.service.SysOperateGroupService;
import com.luckyun.core.annotation.SortMaxValue;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.operate.SysOperateGroup;

/**
 *  操作控制器
* @author zt
*/
@UrlByPkgController
public class SysOperateGroupController {
	
	@Autowired
	private SysOperateGroupService sysOperateGroupService;
	/**
	  *  获取操作数据列表
	 * @param condition 操作数据参数
	 * @return 请求返回操作数据信息
	 */
	@GetMapping("readAll")
	public List<SysOperateGroup> readAll(SysOperateGroupParam condition) {
		return sysOperateGroupService.findOperateGroupsByCondition(condition);
	}
	
	/**
	  *  获取单个操作信息
	 * @param indocno 操作主键
	 * @return 请求返回操作信息
	 */
	@GetMapping("readOne")
	public SysOperateGroup readOne(@Param("indocno") Long indocno) {
		return sysOperateGroupService.readOneGroupByIndocno(indocno);
	}
	
	/**
	 * @desc 添加操作数据信息
	 * @param sysOperateGroupParam 操作数据参数
	 * @return 添加是否成功
	 * @throws Exception 
	 */
	@PostMapping("add")
	@SortMaxValue
	public Integer add(@RequestBody SysOperateGroup sysOperateGroup) throws Exception {
		SysOperateGroup sysOperateGroup2= sysOperateGroup;
		Integer addsuccess = sysOperateGroupService.insert(sysOperateGroup2);
		return addsuccess;
	}
	
	/**
	 * @desc 批量删除操作数据信息
	 * @param sysOperateGroupParam 操作数据参数
	 */
	@PostMapping("delete")
	public void delete(@RequestBody SysOperateGroupParam sysOperateGroupParam) {
		List<SysOperateGroup> sysOperateGroups = sysOperateGroupParam.getDelList();
		for(SysOperateGroup orgoOperateGroup:sysOperateGroups) {
			SysOperateGroup operateGroup = sysOperateGroupService.readOneGroupByIndocno(orgoOperateGroup.getIndocno());
			sysOperateGroupService.deleteGroup(operateGroup);
		}
	}
	
	/**
	 * @desc 修改添加操作数据信息
	 * @param sysOperateGroupParam 操作数据参数
	 * @return 修改是否成功
	 */
	@PostMapping("update")
	public Integer update(@RequestBody SysOperateGroup sysOperateGroup) {
		int updatesuccess = sysOperateGroupService.update(sysOperateGroup);
		return updatesuccess;
	}
	
	@PostMapping("updateDrag")
    public void updateDrag(@RequestBody SysOperateGroupParam condition) 
        throws InstantiationException, IllegalAccessException
    {
	    sysOperateGroupService.drag(condition.getDindocno(),condition.getPindocno());
    }
	
}
