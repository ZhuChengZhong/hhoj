package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hhoj.judger.cache.RedisCache;
import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.mapper.ProblemTypeMapper;
import com.hhoj.judger.service.ProblemTypeService;

@Service("problemTypeService")
public class ProblemTypeServiceImpl implements ProblemTypeService{
	
	@Autowired
	private ProblemTypeMapper problemTypeMapper;
	
	@Override
	public ProblemType findProblemTypeById(Integer typeId) {
		String key="problem:type:"+typeId;
		String json=RedisCache.get(key);
		if(json!=null) {
			return JSON.parseObject(json, ProblemType.class);
		}
		ProblemType type=problemTypeMapper.findProblemTypeById(typeId);
		String value=JSON.toJSONString(type);
		RedisCache.set(key, value, 60*60);
		return type;
		
	}

	@Override
	public Integer findCount() {
		String key="problem:type:count";
		String count=RedisCache.get(key);
		if(count!=null) {
			return Integer.parseInt(count);
		}
		int c=problemTypeMapper.findCount();
		RedisCache.set(key, c+"", 60*60);
		return c;
	}

	@Override
	public List<ProblemType> findProblemTypes() {
		String key="problem:type:list";
		String json=RedisCache.get(key);
		if(json!=null) {
			return JSON.parseArray(json, ProblemType.class);
		}
		List<ProblemType>list=problemTypeMapper.findProblemTypes();
		String value=JSON.toJSONString(list);
		RedisCache.set(key, value, 60*60);
		return list;
	}

	@Override
	public Integer updateProblemType(ProblemType problemType) {
		String key="problem:type:"+problemType.getTypeId();
		RedisCache.del(key);
		RedisCache.del("problem:type:list");
		return problemTypeMapper.updateProblemType(problemType);
	}

	@Override
	public Integer removeProblemType(Integer typeId) {
		RedisCache.del("problem:type:"+typeId);
		RedisCache.del("problem:type:list");
		RedisCache.del("problem:type:count");
		return problemTypeMapper.removeProblemType(typeId);
	}

	@Override
	public Integer addProblemType(ProblemType problemType) {
		RedisCache.del("problem:type:"+problemType.getTypeId());
		RedisCache.del("problem:type:list");
		RedisCache.del("problem:type:count");
		return problemTypeMapper.addProblemType(problemType);
	}

}
