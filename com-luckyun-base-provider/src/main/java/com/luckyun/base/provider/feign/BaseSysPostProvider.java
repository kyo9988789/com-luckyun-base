package com.luckyun.base.provider.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.model.post.SysPost;

/**
 * 获取post数据接口
 * @author yangj080
 *
 */
@FeignClient(name="${application.servernm.luckyun-base:luckyun-base}",url = "${application.servernm.luckyun-base-url:}")
public interface BaseSysPostProvider {

	/**
	 * 根据编号获取岗位对象
	 * @param indocno 岗位编号
	 * @return 岗位对象
	 */
	@GetMapping("/feign/sys/post/readById")
	SysPost findObjById(@RequestParam("indocno") Long indocno);
	
	/**
	 * 添加岗位对象
	 * @param sysPost需要添加的岗位对象
	 * @return 岗位对象
	 */
	@PostMapping("/feign/sys/post/add")
	SysPost addSingleSysPost(SysPost sysPost);
	
	/**
	 * 批量添加岗位数据
	 * @param sysPostList
	 */
	@PostMapping("/feign/sys/post/batchAdd")
	int batchAddSysPost(List<SysPost> sysPostList);
}
