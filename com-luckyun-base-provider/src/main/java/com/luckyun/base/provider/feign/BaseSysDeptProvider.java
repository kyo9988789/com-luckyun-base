package com.luckyun.base.provider.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.provider.feign.fallback.BaseSysUserServiceFallBack;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.model.dept.SysDept;

@FeignClient(name="${application.servernm.luckyun-base:luckyun-base}",url = "${application.servernm.luckyun-base-url:}",fallback = BaseSysUserServiceFallBack.class,qualifier="baseSysDeptProvide")
public interface BaseSysDeptProvider {
	
	/**
	 * 获取当前登录人的管辖部门
	 * @param authInfo 登录人信息
	 * @return 所有的管辖部门,主要包含子集部门编号
	 */
	@PostMapping("/feign/sys/dept/jusdepts")
	String getJurisDepts(@RequestBody AuthInfo authInfo);
	
	/**
	 * 根据用户编号获取部门列表数据
	 * @param iuserid 用户编号
	 * @return 部门列表
	 */
	@GetMapping("/feign/sys/dept/userJusDepts")
	List<SysDept> findUserJus(@RequestParam("iuserid") Long iuserid);
	
	/**
	 * 查找到所有的子集节点根据当前的节点
	 * @param iparentid 当前节点,主要用作找到所有的子集节点
	 * @return 符合条件的部门数据
	 */
	@GetMapping("/feign/sys/dept/findDeptsByIparentid")
	List<SysDept> findDeptByParenId(@RequestParam("iparentid") Long iparentid);
	
	/**
	 * 根据用户编号获取部门信息
	 * @param iuserid 用户编号
	 * @return 部门对象
	 */
	@GetMapping("/feign/sys/dept/readbyuserid")
	SysDept findByIuserId(@RequestParam("iuserid") Long iuserid);
	
	/**
	 * 根据部门编号获取部门详情
	 * @param indocno 部门编号
	 * @return
	 */
	@GetMapping("/feign/sys/dept/readbyindocno")
	SysDept findByIndocno(@RequestParam("indocno") Long indocno);
	
	/**
	 * 	根据部门编号获取部门信息
	 * @param scode
	 * @return
	 */
	@GetMapping("/feign/sys/dept/readScode")
	public List<SysDept> findByScode(@RequestParam("scode") String scode);
	
	/**
	 * 单个部门添加
	 * @param sysDept 需要添加的部门数据
	 * @return 保存的部门数据
	 */
	@PostMapping("/feign/sys/dept/addSingleDept")
	SysDept addSingleDept(SysDept sysDept);
	
	/**
	 * 批量添加人员的管辖部门
	 * @param sysDepts 管辖的部门列表
	 * @return 批量添加成功还是失败
	 */
	@PostMapping("/feign/sys/dept/batchAddDept")
	int batchAddSingleDept(List<SysDept> sysDepts);
}
