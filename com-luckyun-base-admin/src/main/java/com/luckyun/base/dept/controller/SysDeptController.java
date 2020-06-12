package com.luckyun.base.dept.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.dept.param.SysDeptParam;
import com.luckyun.base.dept.service.SysDeptService;
import com.luckyun.core.annotation.SortMaxValue;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.core.data.filter.AutoBuildQueriesHelper;
import com.luckyun.model.dept.SysDept;

@UrlByPkgController
public class SysDeptController {
	
    @Autowired
	private SysDeptService sysDeptService;
	
	@RequestMapping("readAll")
	public List<SysDept> readAll(SysDeptParam condition) {
		List<SysDept> result = sysDeptService.findAllByJurs(condition);
		return result;
	}
	
	@RequestMapping("readAllByCondition")
	public List<SysDept> readAllByCondition(SysDeptParam condition){
		boolean flag = false;
		//有查询条件,拉平数据
		if(AutoBuildQueriesHelper.isExtraConditionIfRemoveKey("__orderby")) {
			flag = true;
		}
		List<SysDept> depts = sysDeptService.findAll(condition);
		if(flag) {
			for(SysDept dept : depts) {
				dept.setSidcc("/" + dept.getIndocno() +"/");
			}
		}
		return depts;
	}
	
	@RequestMapping("readAllByJurs")
	public List<SysDept> readAllByJurs(SysDeptParam condition){
		return sysDeptService.findAllByJurs(condition);
	}
	
	@GetMapping("readOne")
	public SysDept readOne(@RequestParam("indocno") Long indocno) {
	    return sysDeptService.findOne(indocno);
	}
	
	@PostMapping("add")
	@SortMaxValue(iparentidnm="iparentid")
    public SysDept add(@RequestBody SysDept sysDept) {
        sysDeptService.add(sysDept);
        return sysDept;
    }
	
	@PostMapping("update")
    public SysDept update(@RequestBody SysDept sysDept) {
        sysDeptService.updateDept(sysDept);
        return sysDept;
    }
	
	@PostMapping("delete")
    public void delete(@RequestBody SysDeptParam sysDeptParam) {
	    List<SysDept> sysDepts = sysDeptParam.getDelList();
//        for(SysDept sysDept:sysDepts) {
//            sysDeptService.delete(sysDept);
//        }
	    sysDeptService.delDepts(sysDepts);
    }
	
	@PostMapping("updateDrag")
    public void updateDrag(@RequestBody SysDeptParam condition) 
        throws InstantiationException, IllegalAccessException
    {
	    sysDeptService.dragDept(condition.getDindocno(),condition.getPindocno());
    }
}
