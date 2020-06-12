package com.luckyun.base.user.param;

import java.util.List;

import com.luckyun.model.user.SysAccount;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SysUserParam extends SysAccount{

	/**
	 * 页数
	 */
	private Integer pageno = 1;

	/**
	 * 每页记录数
	 */
	private Integer pagesize = 10;
	
	/**
	 * 部门编号
	 */
	private Long ideptid;
	
	/**
	 * 获取所有当前部门以及包含部门编号
	 */
	private Long ideptidsidcc;
	
	/**
	 * 角色编号
	 */
	private Long iroleid;
	
	/**
	 * 岗位编号
	 */
	private Long ipostid;

	/**
	 * 专业编号
	 */
	private Long majorid;
	
	/**
	 * 角色分类编号
	 */
	private Long iroletypeid;
	
	/**
	 * 当前用户所属部门的下辖部门的所有人员
	 */
	private Long isamedeptsidccuserids;
	
	/**
	 * 用户状态判断
	 */
	private Integer istatec;
	
	/**
	 * 模糊查询工号
	 */
	private String sworknolike;
	
	/**
	 * 公司编号
	 */
	private Long icompanyid;
	
	/**
	 * 	角色编号集合（bpm变量参数使用）
	 */
	private List<Long> roleids;
	
	/**
	 * 角色名称集合
	 */
	private String rolenmList;
	
	/**
	 * 	部门编号集合（bpm变量参数使用）
	 */
	private List<Long> deptids;
	
	/**
	 * 岗位编号集合
	 */
	private List<Long> ipostids;
	
	/**
	 * 用户列表组合
	 */
	private List<Long> iuserids;
	
	/**
	 * 需要删除的角色对象
	 */
	private List<SysAccount> delList;
	
	/**
	 * 需要批量调整的用户数据
	 */
	private List<SysAccount> sysUsers;
	
	/**
	 * 角色名称集合
	 */
	private List<String> sysRolenmList;
	
	/**
	 * 专业名称集合
	 */
	private List<String> sysMajornmList;
	
	/**
	 * 部门名称集合
	 */
	private List<String> sysDeptnmList;
}
