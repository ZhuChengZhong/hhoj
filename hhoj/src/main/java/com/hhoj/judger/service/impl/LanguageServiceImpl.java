package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hhoj.judger.cache.RedisCache;
import com.hhoj.judger.entity.Language;
import com.hhoj.judger.mapper.LanguageMapper;
import com.hhoj.judger.service.LanguageService;

@Service("languageService")
public class LanguageServiceImpl implements LanguageService{
	
	@Autowired
	private LanguageMapper languageMapper;
	
	@Override
	public Language findLanguageById(Integer languageId) {
		String key="language:"+languageId;
		String json=RedisCache.get(key);
		if(json!=null) {
			return JSON.parseObject(json, Language.class);
		}
		Language lan=languageMapper.findLanguageById(languageId);
		String value=JSON.toJSONString(lan);
		RedisCache.set(key, value, 60*60);
		return lan;
	}

	@Override
	public Integer findCount() {
		String key="language:count";
		String count=RedisCache.get(key);
		if(count==null) {
			int c=languageMapper.findCount();
			RedisCache.set(key, c+"", 60*60);
			return c;
		}
		return Integer.parseInt(count);
	}

	@Override
	public List<Language> findLanguages() {
		String key="language:list";
		String json=RedisCache.get(key);
		if(json!=null) {
			return JSON.parseArray(json,Language.class);
		}
		List<Language>list=languageMapper.findLanguages();
		String value=JSON.toJSONString(list);
		RedisCache.set(key, value, 60*60);
		return list;
	}

	@Override
	public Integer updateLanguage(Language language) {
		RedisCache.del("language:list");
		RedisCache.del("language:count");
		RedisCache.del("language:"+language.getLanguageId());
		return languageMapper.updateLanguage(language);
	}

	@Override
	public Integer removeLanguage(Integer languageId) {
		RedisCache.del("language:list");
		RedisCache.del("language:count");
		RedisCache.del("language:"+languageId);
		return languageMapper.removeLanguage(languageId);
	}

	@Override
	public Integer addLanguage(Language language) {
		RedisCache.del("language:list");
		RedisCache.del("language:count");
		RedisCache.del("language:"+language.getLanguageId());
		return languageMapper.addLanguage(language);
	}

}
