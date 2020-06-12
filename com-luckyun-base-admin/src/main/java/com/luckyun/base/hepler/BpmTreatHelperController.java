package com.luckyun.base.hepler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.util.StringUtil;
import com.luckyun.base.msg.param.SysMsgParam;
import com.luckyun.base.msg.service.SysMsgService;
import com.luckyun.bpm.model.pojo.BpmTask;
import com.luckyun.bpm.service.BpmBasicService;
import com.luckyun.core.response.ApiResult;
import com.luckyun.core.response.PageInfo;
import com.luckyun.core.tool.Md5HelperUtils;
import com.luckyun.model.msg.SysMsg;

@RestController
public class BpmTreatHelperController {
	
	@Autowired
	private BpmBasicService bpmBasicService;
	
	@Autowired
	private SysMsgService sysMsgService;

	@RequestMapping("/bpm/treat/helper/readTreatTask")
	public ApiResult getTreatTask(@RequestParam(value = "icmsg",defaultValue = "0") Integer icmsg 
			,@RequestParam(value = "ol",defaultValue = "1") Integer ol) throws Exception{
		PageInfo pageInfo = new PageInfo(10, 1, 0);
		List<BpmTask> bpmTasks = bpmBasicService.getTreatTasks();
		if(icmsg == 1) {
			bpmTaskAddMsg(bpmTasks,ol);
		}
		for(int i = 0;i < bpmTasks.size();i++) {
			BpmTask bpmTask = bpmTasks.get(i);
			bpmTask.setKeyIndocno(Long.valueOf(i));
		}
		pageInfo.setTotalCount(bpmTasks.size());
		ApiResult apiResult = ApiResult.valueOf(bpmTasks);
		apiResult.setPagination(pageInfo);
		return apiResult;
	}
	
	private void bpmTaskAddMsg(List<BpmTask> bpmTasks,Integer ol) {
		List<String> unequip = new ArrayList<>();
		if(bpmTasks == null) {
			bpmTasks = new ArrayList<>();
		}
		SysMsgParam condition = new SysMsgParam();
		condition.setIsend(0);
		condition.setIread(0);
		List<SysMsg> sysMsgs = sysMsgService.findAllMyMsg(condition);
		for(SysMsg sysMsg : sysMsgs) {
			//以url地址与id为唯一key
			boolean flag = false;
			String unkey = "";
			if(!StringUtil.isEmpty(sysMsg.getSfrombillurl()) && sysMsg.getIfrombillid() != null) {
				unkey = Md5HelperUtils.md5(sysMsg.getSfrombillurl() + "_" + sysMsg.getIfrombillid());
				flag = unequip.contains(unkey);
			}
			//不重叠
			if(ol == 0) {flag = false;}
			if(!flag) {
				BpmTask bpmTask = new BpmTask();
				bpmTask.setItype(1);
				bpmTask.setSname(sysMsg.getStitle());
				bpmTask.setHeaderurl(sysMsg.getSfrombillurl());
				//代表属于哪个模块
				bpmTask.setSmodulename(sysMsg.getSfrombillnm());
				bpmTasks.add(bpmTask);
				if(!StringUtil.isEmpty(unkey)) {
					unequip.add(unkey);
				}
			}
		}
		unequip.clear();
	}
}
