package com.luckyun.base.appversion.param;

import java.util.List;

import com.luckyun.model.appversion.SysApp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class SysAppParam extends SysApp{

	/**
	 * 需要删除的app对象
	 */
	private List<SysApp> delList;
}
