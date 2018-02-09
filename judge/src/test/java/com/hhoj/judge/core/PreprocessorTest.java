package com.hhoj.judge.core;

import com.hhoj.judge.util.FileUtil;

public final class PreprocessorTest {
	private static Preprocessor pp=new Preprocessor();
	public static void testFindClassName(){
		String code=FileUtil.getCharsFromFile("E:/workspace/judge/src/test/java/com/hhoj/judge/core/PreprocessorTest.java");
		String className=pp.findClassName(code);
		System.out.println(className);
	}
	public static void main(String[] args) {
		testFindClassName();
	}
}
