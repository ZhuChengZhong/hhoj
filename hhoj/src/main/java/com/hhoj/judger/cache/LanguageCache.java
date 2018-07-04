package com.hhoj.judger.cache;

import com.alibaba.fastjson.JSON;
import com.hhoj.judger.entity.Language;
import com.hhoj.judger.entity.Problem;

public class LanguageCache {
	
	private final static String LANGUAGE_KEY_PRE="language";
	
	public static void cacheLanguage(Language language) {
		
		String key=LANGUAGE_KEY_PRE+"-"+language.getLanguageId();
		String value=JSON.toJSONString(language);
		RedisCache.set(key, value, 60*60);
	}
	
	public static Language getLanguage(int languageId) {
		String key=LANGUAGE_KEY_PRE+"-"+languageId;
		String value=RedisCache.get(key);
		Language res=JSON.parseObject(value, Language.class);
		return res;
	}
	
	public static long delLanguage(int languageId) {
		String key=LANGUAGE_KEY_PRE+"-"+languageId;
		return RedisCache.del(key);
	}
	
	public static void main(String[] args) {
		Language language=new Language();
		language.setCompileCommand("javac filename");
		language.setLanguageId(1);
		language.setLanguageName("java");
		language.setRunCommand("java filename");
		cacheLanguage(language);
		Language l=getLanguage(1);
		System.out.println(l);
		long res=delLanguage(1);
		System.out.println(res);
	}
}
