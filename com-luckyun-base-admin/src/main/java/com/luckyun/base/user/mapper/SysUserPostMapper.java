package com.luckyun.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.luckyun.base.user.param.SysUserParam;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.model.post.SysPost;
import com.luckyun.model.user.SysUserPost;

@Repository
public interface SysUserPostMapper extends BaseMapper<SysUserPost>{

	List<SysUserPost> findPostByUid(@Param("uid") Long uid);

	List<SysUserPost> findPostByIpostid(@Param("pid") Long pid);
	
	List<SysPost> findPostByUserPost(@Param("condition") SysUserParam condition);
	
	List<SysUserPost> selectPosts(@Param("condition") SysUserParam condition);
}
