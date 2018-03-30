package com.hhoj.judge.mapper;

import java.util.List;

import com.hhoj.judge.entity.Language;
import com.hhoj.judge.entity.TestPoint;

/**
 * Language 映射器
 * @author zhuchengzhong
 *
 */
public interface LanguageMapper {
	/**
	 * 通过id查找Language
	 * @param languageId
	 * @return
	 */
	public Language findLanguageById(Integer languageId);
	
}
