package com.luckyun.base.feign.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luckyun.base.post.mapper.SysPostMapper;
import com.luckyun.base.post.param.SysPostClassifyParam;
import com.luckyun.base.post.param.SysPostParam;
import com.luckyun.base.post.service.SysPostClassifyService;
import com.luckyun.base.post.service.SysPostService;
import com.luckyun.model.post.SysPost;
import com.luckyun.model.post.SysPostClassify;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/feign/sys/post")
@Slf4j
public class FeignSysPostController {
	
	@Autowired
	ObjectFactory<HttpSession> httpSessionFactory;

	@Autowired
	private SysPostService sysPostService;
	
	@Autowired
	private SysPostMapper sysPostMapper;
	
	@Autowired
	private SysPostClassifyService sysPostClassifyService;
	
	@RequestMapping("readById")
	public SysPost findObjById(@RequestParam("indocno") Long indocno) {
		return sysPostService.readOneByIndocno(indocno);
	}
	
	@PostMapping("add")
	public SysPost addSysPost(@RequestBody SysPost sysPost) {
		SysPostClassify dclassify = checkedClassify();
		int maxSort = getMaxIsort(dclassify);
		if(sysPost.getIclassifyid() == null) {
			sysPost.setIclassifyid(dclassify.getIndocno());
		}
		sysPost.setIsort(maxSort);
		sysPost.setDregt(new Date());
		return sysPostService.add(sysPost, null);
	}
	
	@PostMapping("batchAdd")
	public int batchSysPost(@RequestBody List<SysPost> sysPostList) {
		try {
			SysPostClassify dclassify = checkedClassify();
			int maxSort = getMaxIsort(dclassify);
			for(SysPost post: sysPostList) {
				if(post.getSregid() == null) {
					log.error("创建人不能为空!");
					return 0;
				}
				if(post.getIclassifyid() == null) {
					post.setIclassifyid(dclassify.getIndocno());
				}
				post.setDregt(new Date());
				post.setIsort(maxSort);
				maxSort++;
			}
			sysPostMapper.batchInsert(sysPostList);
			return 1;
		}catch (Exception e) {
			log.error("批量添加出错,错误信息:" + e.getMessage(),e);
			return 0;
		}
	}
	
	private SysPostClassify checkedClassify() {
		SysPostClassify dclassify = new SysPostClassify();
		List<SysPostClassify> classifies = sysPostClassifyService.findAll(new SysPostClassifyParam());
		if(classifies != null && classifies.size() >= 1) {
			dclassify = classifies.get(0);
		}else {
			dclassify.setDregt(new Date());
			dclassify.setSdescribe("批量导入的岗位分类");
			dclassify.setSname("批量导入的岗位分类");
			sysPostClassifyService.add(dclassify, null);
		}
		return dclassify;
	}
	
	private int getMaxIsort(SysPostClassify postClassify) {
		SysPostParam condition = new SysPostParam();
		condition.setIclassifyid(postClassify.getIndocno());
		List<SysPost> sysPosts = sysPostService.findAll(condition);
		if(sysPosts != null && sysPosts.size()>=1) {
			return sysPosts.get(0).getIsort() != null ? (sysPosts.get(0).getIsort() + 1): 1;
		}
		return 1;
	}
}
