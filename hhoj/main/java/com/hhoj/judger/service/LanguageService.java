package com.hhoj.judger.service;

import java.util.List;

import com.hhoj.judger.entity.Language;

public interface LanguageService {
	/**
	 * 通过id查找Language
	 * @param languageId
	 * @return
	 */
	public Language findLanguageById(Integer languageId);
	
	/**
	 * 获取Language数目
	 * @return
	 */
	public Integer findCount();
	
	/**
	 * 获取 Language列表
	 * @return
	 */
	public List<Language> findLanguages();
	
	/**
	 * 更新Language
	 * @param testPoint
	 * @return
	 */
	public Integer updateLanguage(Language language);
	
	/**
	 * 删除Language
	 * @param languageId
	 * @return
	 */
	public Integer removeLanguage(Integer languageId);
	
	/**
	 * 增加Language
	 * @param language
	 * @return
	 */
	public Integer addLanguage(Language language);
}
