package com.luckyun.base.major.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.luckyun.base.major.param.SysMajorParam;
import com.luckyun.base.major.service.SysMajorService;
import com.luckyun.core.annotation.Pageable;
import com.luckyun.core.annotation.UrlByPkgController;
import com.luckyun.model.major.SysMajor;

/**
 * 用户专业控制层
 * 
 * @action (请说明该接口根地址)
 * @since JDK 1.8
 * @author chengrui
 * @Email 
 * @date: 2019/8/16 14:23
 */
@UrlByPkgController
public class SysMajorController {
    @Autowired
    SysMajorService sysMajorService;
    /**
     * 查询所有专业
     *
     * @title
     * @author chengrui
     * @date 2019/8/16 15:52
     * @param condition|参数中文名称|SysMajor|是否必填
     * @param condition.sname|参数中文名称|String|是否必填
     * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
     * @resqParam detail|接口返回数据|Object|必填
     * @resqParam operates|用户操作权限|String|必填
     * @resqParam pagination|分页信息|String|必填
     * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
     * @requestType Get
     *
     */
    @GetMapping("readAll")
    @Pageable
    public List<SysMajor> readAll(SysMajorParam condition){
        return sysMajorService.findAll(condition);
    }
    /**
     * 单条专业查询
     *
     * @title 单挑专业查询
     * @author chengrui
     * @date 2019/8/16 16:03
     * @param condition|参数中文名称|SysMajor|是否必填
     * @param condition.indocno|参数中文名称|Long|是否必填
     * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
     * @resqParam detail|接口返回数据|Object|必填
     * @resqParam operates|用户操作权限|String|必填
     * @resqParam pagination|分页信息|String|必填
     * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
     * @requestType Get
     *
     */
    @GetMapping("readOne")
    public SysMajor readOne(SysMajorParam condition){
        return sysMajorService.findOne(condition.getIndocno());
    }

    /**
     * 新增
     *
     * @title 专业内容新增
     * @author chengrui
     * @date 2019/8/16 15:57
     * @param sysMajor|参数中文名称|SysMajor|是否必填
     * @param sysMajor.sname|参数中文名称|Long|是否必填
     * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
     * @resqParam detail|接口返回数据|Object|必填
     * @resqParam operates|用户操作权限|String|必填
     * @resqParam pagination|分页信息|String|必填
     * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
     * @requestType Post
     *
     */
    @PostMapping("add")
    public SysMajor add(@RequestBody SysMajor sysMajor) {
        System.out.println(sysMajorService.add(sysMajor));
        return sysMajor;
    }
    /**
     * 修改
     *
     * @title 专业内容修改
     * @author chengrui
     * @date 2019/8/16 16:01
     * @param sysMajor|参数中文名称|SysMajor|是否必填
     * @param sysMajor.indocno|参数中文名称|Long|是否必填
     * @param sysMajor.sname|参数中文名称|String|是否必填,
     * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
     * @resqParam detail|接口返回数据|Object|必填
     * @resqParam operates|用户操作权限|String|必填
     * @resqParam pagination|分页信息|String|必填
     * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
     * @requestType Post
     *
     */
    @PostMapping("update")
    public SysMajor modify(@RequestBody SysMajor sysMajor){

        System.out.println(sysMajorService.modify(sysMajor));
        return sysMajor;
    }

    /**
     * 删除
     *
     * @title 专业删除
     * @author chengrui
     * @date 2019/8/16 16:20
     * @param sysMajor|参数中文名称|SysMajor|是否必填
     * @param sysMajor.indocno|参数中文名称|Long|是否必填
     * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
     * @resqParam detail|接口返回数据|Object|必填
     * @resqParam operates|用户操作权限|String|必填
     * @resqParam pagination|分页信息|String|必填
     * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
     * @requestType Post
     *
     */
    @PostMapping("delete")
    public int remove(@RequestBody SysMajor sysMajor){
        return sysMajorService.delete(sysMajor);
    }

    /**
     * 用户专业维护
     *
     * @title 用户专业维护
     * @author chengrui
     * @date 2019/8/19 8:56
     * @param condition|参数中文名称|SysUserParam|是否必填
     * @param condition.sysUsers|参数中文名称|List<SysUser>|是否必填
     * @param condition.sysMajors|参数中文名称|List<SysMajor>|是否必填
     * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
     * @resqParam detail|接口返回数据|Object|必填
     * @resqParam operates|用户操作权限|String|必填
     * @resqParam pagination|分页信息|String|必填
     * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
     * @requestType Post
     *
     */
    @PostMapping("batchUpdUser")
    public void batchUpdateMajor(@RequestBody SysMajorParam condition){
        sysMajorService.updateBathUserMajor(condition);
    }
    @PostMapping("addUserMajor")
    public void addUserMajor(@RequestBody SysMajorParam condition){
        sysMajorService.addUserMajor(condition);
    }
    @PostMapping("deleteUserMajor")
    public void delUserMajor(@RequestBody SysMajorParam condition){
        sysMajorService.delUserMajor(condition);
    }
    
    @GetMapping(value = "/readByIuserid")
    public List<SysMajor> readByIuserid(@RequestParam("iuserid") Long iuserid){
    	return this.sysMajorService.readByIuserid(iuserid);
    }

//    /**
//     * 查询已有的用户专业信息
//     *
//     * @title TODO(请说明该接口中文名称)
//     * @author chengrui
//     * @date 2019/8/21 11:34
//     * @param condition|参数中文名称|SysMajorParam|是否必填
//     * @param condition.indocno|参数中文名称|Long|是否必填
//     * @resqParam code|接口调用状态码(0：失败、1：成功)|String|必填
//     * @resqParam detail|接口返回数据|Object|必填
//     * @resqParam operates|用户操作权限|String|必填
//     * @resqParam pagination|分页信息|String|必填
//     * @respBody {"code":"","detail":"","list":"","operates":"","pagination":""}
//     * @requestType Post
//     *
//     */
//    @GetMapping("readUserMajors")
//    public List<SysUserMajor> readUserMajorByMid(SysMajorParam condition){
//        return sysMajorService.readUserMajorByMid(condition);
//    }
}
