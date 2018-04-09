package com.hhoj.judger.mapper;

import java.util.List;

import com.hhoj.judger.entity.Language;
import com.hhoj.judger.entity.TestPoint;

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
