package com.hhoj.judger.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hhoj.judger.entity.Language;
import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.util.MyBatisUtil;

public class LanguageMapperTest {
	private SqlSession sqlSession;
	private LanguageMapper languageMapper;
	@Before
	public void setUp() throws Exception {
		sqlSession=MyBatisUtil.getSqlSession();
		languageMapper=sqlSession.getMapper(LanguageMapper.class);
	}

	@After
	public void tearDown() throws Exception {
		sqlSession.commit();
	}
	@Test
	public void findLanguageByIdTest() {
		System.out.println(languageMapper.findLanguageById(1));
	}
	
	@Test
	public void findCountTest() {
		System.out.println(languageMapper.findCount());
	}
	@Test
	public void findLanguagesTest() {
		System.out.println(languageMapper.findLanguages());
	}
	@Test
	public void updateLanguageTest() {
		Language language=new Language();
		language.setLanguageId(1);
		language.setCompileCommand("complile");
		language.setLanguageName("zhu");
		language.setRunCommand("run");
		System.out.println(languageMapper.updateLanguage(language));
	}
	@Test
	public void removeProblemTypeTest() {
		
		System.out.println(languageMapper.removeLanguage(2));
	}
	@Test
	public void addLanguageTest() {
		Language language=new Language();
		language.setLanguageId(2);
		language.setCompileCommand("c++");
		language.setLanguageName("g++");
		language.setRunCommand("ruasd");
		System.out.println(languageMapper.addLanguage(language));
	}
}
