package com.luckyun.base.dept.event;

import com.alibaba.fastjson.JSON;
import com.luckyun.constants.RedisKeyConts;
import com.luckyun.core.event.DataEventQueue;
import com.luckyun.core.redis.RedisOperationDao;
import com.luckyun.model.datadic.SysDatadic;
import com.luckyun.model.datadic.SysDatadicClassify;
import com.luckyun.model.dept.SysDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by chengrui on 2019/9/11.
 */
@Component
public class SysDeptChangeEvent extends DataEventQueue<SysDept> {
    @Autowired
    RedisOperationDao redisOperationDao;
    @Override
    protected void onUpdate(SysDept older, SysDept newer) {
        this.clearCache(newer);
    }

    @Override
    protected void onInsert(SysDept entity) {
        this.clearCache(entity);
    }

    @Override
    protected void onDelete(SysDept entity) {
        this.clearCache(entity);
    }

    /**
     * 清理部门缓存（key：部门ID）
     * @param entity
     */
    private void clearCache(SysDept entity){
//        redisOperationDao.hdel(RedisKeyConts.SYS_DEPT_KEY,String.valueOf(entity.getIndocno()));
    }
}
