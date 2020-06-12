package com.luckyun.base.datadic.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luckyun.base.datadic.service.SysDatadicClassifyService;
import com.luckyun.base.datadic.service.SysDatadicService;
import com.luckyun.constants.RedisKeyConts;
import com.luckyun.core.event.DataEventQueue;
import com.luckyun.core.redis.RedisOperationDao;
import com.luckyun.model.datadic.SysDatadic;
import com.luckyun.model.datadic.SysDatadicClassify;

/**
 * Created by chengrui on 2019/9/10.
 */
@Component
public class SysDatadicChangeEvent extends DataEventQueue<SysDatadic> {
    @Autowired
    private SysDatadicClassifyService sysDatadicClassifyService;
    @Autowired
    private SysDatadicService sysDatadicService;
    @Autowired
    RedisOperationDao redisOperationDao;
    @Override
    protected void onUpdate(SysDatadic older, SysDatadic newer) {
        this.clearCache(newer);
    }

    @Override
    protected void onInsert(SysDatadic entity) {
        this.clearCache(entity);
    }

    @Override
    protected void onDelete(SysDatadic entity) {
        this.clearCache(entity);
    }

    private void clearCache(SysDatadic entity){
        SysDatadicClassify sysDatadicClassify = new SysDatadicClassify();
        entity = sysDatadicService.findOne(entity);
        sysDatadicClassify.setIndocno(entity.getIclassifyid());
        sysDatadicClassify = sysDatadicClassifyService.findOne(sysDatadicClassify);
        redisOperationDao.hdel(RedisKeyConts.SYS_DATADIC_KEY,sysDatadicClassify.getSnamealias());
    }
}
