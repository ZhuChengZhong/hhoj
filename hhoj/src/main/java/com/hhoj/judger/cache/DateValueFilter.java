package com.hhoj.judger.cache;

import java.util.Date;

import com.alibaba.fastjson.serializer.ValueFilter;

public class DateValueFilter implements ValueFilter{
	
	public static  DateValueFilter instance=new DateValueFilter();
	
	@Override
	public Object process(Object object, String name, Object value) {
		if(value instanceof Date) {
			return ((Date) value).getTime();
		}
		return value;
	}

}
