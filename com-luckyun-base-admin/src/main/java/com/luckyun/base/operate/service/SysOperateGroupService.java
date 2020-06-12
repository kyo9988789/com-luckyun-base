package com.luckyun.base.operate.service; 
 
import java.util.List; 
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.luckyun.base.module.mapper.SysModuleOperateMapper;
import com.luckyun.base.operate.mapper.SysOperateGroupMapper;
import com.luckyun.base.operate.param.SysOperateGroupParam;
import com.luckyun.core.annotation.GlobalLockByRedis;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper; 
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.drag.DragHelperService;
import com.luckyun.model.module.SysModuleOperate;
import com.luckyun.model.operate.SysOperateGroup; 
 
@Service 
public class SysOperateGroupService extends BaseService<SysOperateGroup> { 
 
	@Autowired 
	private SysOperateGroupMapper sysOperateGroupMapper; 
	
	@Autowired
    SysModuleOperateMapper sysModuleOperateMapper;
	
	@Autowired
    @Qualifier("dragHelperService")
    private DragHelperService dragHelperService;
	
	/** 
	  *  获取操作数据列表 
	 * @param condition 操作数据参数 
	 * @return 请求返回操作数据信息 
	 */ 
	public List<SysOperateGroup> findOperateGroupsByCondition(SysOperateGroupParam condition) { 
		List<SysOperateGroup> result = sysOperateGroupMapper.findAll(condition); 
		return result; 
	} 
	 
	/** 
	  *  根据编号获取对应的操作组数据 
	 * @param indocno 主键 
	 * @return 指定的操作组信息 
	 */ 
	public SysOperateGroup readOneGroupByIndocno(Long indocno) { 
		SysOperateGroup result = sysOperateGroupMapper.findOne(indocno); 
		return result; 
	} 
	
	/**
	 * 删除操作组
	 * @param sysOperateGroup
	 */
	@TransactionalException
    public void deleteGroup(SysOperateGroup sysOperateGroup) {
        this.delete(sysOperateGroup);
        List<SysModuleOperate> moduleOperates = sysModuleOperateMapper.findByIoperateGroupid(sysOperateGroup.getIndocno());
        for(SysModuleOperate moduleOperate : moduleOperates) {
            // 删除模块对应关系
            sysModuleOperateMapper.delete(moduleOperate);
        }
    }
	 
	/**
     * 拖拽排序
     * @param dIndocno 拖拽对象
     * @param pIndocno 拖拽目标
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @GlobalLockByRedis
    public void drag(Long dIndocno, Long pIndocno) 
            throws InstantiationException, IllegalAccessException {
        SysOperateGroup dObj = sysOperateGroupMapper.findOne(dIndocno);
        SysOperateGroup pObj = sysOperateGroupMapper.findOne(pIndocno);
        dragHelperService.drag(pObj, dObj);
    }
	 
    @Override
	public BaseMapper<SysOperateGroup> getMapper() { 
		return sysOperateGroupMapper; 
	}

} 
