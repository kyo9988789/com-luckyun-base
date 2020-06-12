package com.luckyun.region.model.arc;

import com.luckyun.model.data.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class RegionArcEquip extends BaseEntity{
	
	@Override
	public boolean __isTrueDel() {
		return false;
	}

	private Long indocno;
	
	private String sfname;
	
	private String sfcode;
	
	private Long iparentid;
	
	/**
	 * 是否存在子集节点,1->有,0->没有
	 */
	private Integer ichildren;
	
	/**
	 * 节点所在的层级结构
	 */
	private String sidcc;
}
