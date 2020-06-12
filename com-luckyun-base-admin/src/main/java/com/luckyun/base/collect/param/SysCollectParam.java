package com.luckyun.base.collect.param;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.luckyun.model.collect.SysCollect;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * 	收藏管理参数实体
 * 
 * @since JDK 1.8
 * @author yaoxc
 * @Email 1009708459@qq.com
 * @date: 2019年8月7日 下午3:50:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysCollectParam extends SysCollect{

	/**
	 * 	用户编号
	 */
	private Long iuserid;
	
	public static void main(String[] args) {
		List<Field> fileds = new ArrayList<>();
		Class<?> clazz = SysCollectParam.class;
		while(clazz != null) {
			fileds.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		for (Field field : fileds) {
			System.out.println(field.getName());
		}
	}
}
