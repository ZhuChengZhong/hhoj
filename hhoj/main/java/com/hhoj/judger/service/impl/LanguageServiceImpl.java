package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhoj.judger.entity.Language;
import com.hhoj.judger.mapper.LanguageMapper;
import com.hhoj.judger.service.LanguageService;

@Service("languageService")
public class LanguageServiceImpl implements LanguageService{
	
	@Autowired
	private LanguageMapper languageMapper;
	
	@Override
	public Language findLanguageById(Integer languageId) {
		return languageMapper.findLanguageById(languageId);
	}

	@Override
	public Integer findCount() {
		return languageMapper.findCount();
	}

	@Override
	public List<Language> findLanguages() {
		return languageMapper.findLanguages();
	}

	@Override
	public Integer updateLanguage(Language language) {
		return languageMapper.updateLanguage(language);
	}

	@Override
	public Integer removeLanguage(Integer languageId) {
		return languageMapper.removeLanguage(languageId);
	}

	@Override
	public Integer addLanguage(Language language) {
		return languageMapper.addLanguage(language);
	}

}
