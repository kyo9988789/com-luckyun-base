package com.luckyun.base.major.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckyun.base.major.mapper.SysMajorMapper;
import com.luckyun.base.major.param.SysMajorParam;
import com.luckyun.base.user.mapper.SysUserMajorMapper;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.km.keyhelper.IDGenerate;
import com.luckyun.core.tool.CollecterMixUtils;
import com.luckyun.model.major.SysMajor;
import com.luckyun.model.user.SysUserMajor;

/**
 * 用户专业业务层
 * Created by chengrui on 2019/8/16.
 */
@Service
public class SysMajorService extends BaseService<SysMajor> {
    @Autowired
    SysMajorMapper sysMajorMapper;
    @Autowired
    SysUserMajorMapper sysUserMajorMapper;

    @Override
    public BaseMapper<SysMajor> getMapper() {
        return sysMajorMapper;
    }

    public List<SysMajor> findAll(SysMajorParam condition) {
        return sysMajorMapper.findAll(condition);
    }

    public SysMajor findOne(Long indocno) {
        return sysMajorMapper.findOne(indocno);
    }

    public int add(SysMajor sysMajor){
        sysMajor.setDregt(new Date());
        sysMajor.setIdel(0);
        sysMajor.setIndocno(this.getAuthInfo().getIndocno());
        sysMajor.setIndocno(IDGenerate.getKey(sysMajor));
        return super.insert(sysMajor);
    }

    public int modify(SysMajor sysMajor){
        return super.update(sysMajor);
    }

    public int remove(SysMajor sysMajor){
        return super.delete(sysMajor);
    }

//    public List<SysUserMajor> readUserMajorByMid(SysMajorParam condition){
//        return sysUserMajorMapper.findMajorByMid(condition.getIndocno());
//    }
    public void addUserMajor(SysMajorParam sysMajors){
        for (SysUserMajor sysUserMajor: sysMajors.getSysUserMajor()) {
            List<SysUserMajor> majors = sysUserMajorMapper.findMajorByIds(sysUserMajor);
            if(null == majors || majors.size() == 0){
                sysUserMajorMapper.insert(sysUserMajor);
            }
        }
    }
    public void delUserMajor(SysMajorParam sysMajors){
        for (SysUserMajor sysUserMajor: sysMajors.getSysUserMajor()) {
            sysUserMajorMapper.delete(sysUserMajor);
        }
    }

    public void updateBathUserMajor(SysMajorParam sysMajors){
        for(SysMajor sysMajor : sysMajors.getSysMajors()) {
            sysMajor.setSysUsers(sysMajors.getSysUsers());
            this.updateMajor(sysMajor);
        }
    }

    protected void updateMajor(SysMajor sysMajor) {
        List<SysUserMajor> oldUser = sysUserMajorMapper.findMajorByMid(sysMajor.getIndocno());
        List<SysUserMajor> nUser = sysMajor.getSysUsers()!= null ? sysMajor.getSysUsers().stream()
                .map(e -> new SysUserMajor(sysMajor.getIndocno(),e.getIndocno())).collect(Collectors.toList()) : new ArrayList<>();
        List<SysUserMajor> mix = CollecterMixUtils.tMix(nUser, oldUser, "iuserid");
        List<SysUserMajor> addMajors = CollecterMixUtils.fdiffSet(nUser,mix, "iuserid");
        List<SysUserMajor> delMajors = CollecterMixUtils.fdiffSet(oldUser,mix, "iuserid");
        for(SysUserMajor addMajor : addMajors) {
//            addMajor.setIuserid(sysMajor.getIndocno());
            sysUserMajorMapper.insert(addMajor);
        }
        for(SysUserMajor delMajor : delMajors) {
            sysUserMajorMapper.delete(delMajor);
        }
    }
    
    public List<SysMajor> readByIuserid(Long iuserid) {
    	if(null == iuserid) return null;
    	return this.sysMajorMapper.findMajorByIuserid(iuserid);
    }
}
