package com.luckyun.base.provider.cache;

public interface CacheHelper <T>{

	T getObjByField(Object field);
	
	T addObj(Object field);
	
	T updateObj(Object field);
}
