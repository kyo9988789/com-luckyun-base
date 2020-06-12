package com.luckyun.base.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.luckyun.base.user.param.SysUserParam;
import com.luckyun.base.user.service.SysUserOtherService;
import com.luckyun.bpm.model.pojo.LuckTaskComment;
import com.luckyun.bpm.service.BpmInstanceBaseService;
import com.luckyun.core.annotation.Pageable;
import com.luckyun.core.data.ExcelBatchReceiver;
import com.luckyun.core.data.ExcelBatchSender;
import com.luckyun.core.data.ExcelBatchSender.ExcelBatchSenderError;
import com.luckyun.model.user.SysAccount;

import lombok.extern.slf4j.Slf4j;

/**
 * base系统一些额外的服务提供
 * @author yangj080
 *
 */
@RestController
@RequestMapping("feign/sys/user")
@Slf4j
public class SysUserOtherController {

	@Autowired
	private SysUserOtherService sysUserOtherService;
	
	@Autowired
	private BpmInstanceBaseService bpmInstanceBaseService;
	
	@GetMapping("/noGetwayUserforbpm")
	public JSONObject findUserForBpm(@RequestParam(value="sloginid",defaultValue = "xyz") String sloginid){
		return sysUserOtherService.findUserForBpm(sloginid);
	}
	
	@GetMapping("/noAuthUserDepts")
	@Pageable
	public List<SysAccount> findUserDepts(SysUserParam condition){
		return sysUserOtherService.findUserDepts(condition);
	}
	
	@GetMapping("/findtask")
	public LuckTaskComment finTask(@RequestParam(required = true) String buskey) {
		
		return bpmInstanceBaseService.getBpmActiveTask(null, buskey);
	}
	
	@PostMapping("/receive/file")
	public ExcelBatchSender receiveExcelData(@RequestBody ExcelBatchReceiver<SysAccount> batchReceiver) {
		ExcelBatchSender batchSender = new ExcelBatchSender(1);
		//代表文件的唯一性
		String batchId = batchReceiver.getBatchId();
		//额外的数据.一般用来传递给业务层除excel数据的额外的数据
		String additionals= batchReceiver.getAdditional();
		try {
			JSONObject jsonObject = JSONObject.parseObject(additionals);
		}catch(Exception e) {
			log.error("additionals:'" + additionals + "'不是符合条件的json数据");
		}
		//数据源的处理
		List<SysAccount> contents = batchReceiver.getContent();
		List<ExcelBatchSender.ExcelBatchSenderError> batchSenderErrorIndexs = 
				new ArrayList<ExcelBatchSender.ExcelBatchSenderError>();
		int i = 0;
		for(SysAccount account : contents) {
			try {
				//do something
			}catch(Exception e){
				//
				ExcelBatchSenderError batchSenderError = batchSender.new ExcelBatchSenderError();
				batchSenderError.setIndex(i);
				batchSenderErrorIndexs.add(batchSenderError);
			}
			i++;
		}
		batchSender.setBatchErrorIndex(batchSenderErrorIndexs);
		return batchSender;
	}
}
