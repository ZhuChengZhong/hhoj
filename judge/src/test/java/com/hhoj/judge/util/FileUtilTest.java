package com.hhoj.judge.util;

import java.io.File;

public class FileUtilTest {
	public static void testCreateFile(){
		FileUtil.createFile("e://zhu/a.txt", "asdaggd");
	}
	
	public static void testCreateDir(){
		boolean result=FileUtil.mkdir("e://zhu");
		System.out.println(result);
	}
	public static void testGetCharsFromFile(){
		String code=FileUtil.getCharsFromFile("E:/workspace/judge/src/test/java/com/hhoj/judge/util/FileUtilTest.java");
		System.out.println(code);
	}
	public static void main(String[] args) {
		//testCreateFile();
		//testCreateDir();
		testGetCharsFromFile();
	}
}
