package com.luckyun.base.hepler;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luckyun.model.datadic.SysDatadic;

@Component
public class SysDatadicHelper {

	@Autowired
	private SysDatadicHelperSource sysDatadicHelperSource;
	
	public SysDatadic getDatadicNmBySfact(Object ofact,String namealias) {
		String sfact = ofact != null ? ofact.toString() : "";
		List<SysDatadic> content = sysDatadicHelperSource.getDatadicBySnameAlias(namealias);
		if(content == null || content.size() <= 0) {
			content = sysDatadicHelperSource.updDatadicBySnameAlias(namealias);
		}
		Optional<SysDatadic> optional =
				content.stream().filter(e -> e.getSfactvalue().equals(sfact)).findFirst();
		if(optional.isPresent()) {
			return optional.get();
		}else {
			content = sysDatadicHelperSource.updDatadicBySnameAlias(namealias);
			optional =
					content.stream().filter(e -> e.getSfactvalue().equals(sfact)).findFirst();
			if(optional.isPresent()) {
				return optional.get();
			}
		}
		return new SysDatadic();
	}
}
