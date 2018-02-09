package com.hhoj.judge.core;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.entity.TestPoint;
import com.hhoj.judge.util.FileUtil;
/**
 * 预处理类 ，处理运行之前的准备工作
 * @author Administrator
 *
 */
public  class Preprocessor {
	public boolean prepare(Submit submit,List<TestPoint> pointList,String codePath,String testPointPath){
		String codeDir=codePath+FileUtil.separator+submit.getSid();
		if(!FileUtil.mkdir(codeDir)||!createCodeFile(submit,codePath)){
			return false;
		}
		String testPointDir=testPointPath+FileUtil.separator+submit.getSid();
		if(!FileUtil.mkdir(testPointDir)||!createTestDataFiles(submit,pointList,testPointDir)){
			return false;
		}
		return true;
	}
	/*
	 * 将用户提交的代码保存为对应的源代码文件用于之后的编译运行
	 * */
	private boolean createCodeFile(Submit submit,String codeDir){
		String languageName=submit.getLanguage().getLanguageName();
		String newFilePath;
		if("Java".equals(languageName)){
		   String className=findClassName(submit.getCode());
		   if(className==null)
			   return false;
		   newFilePath=codeDir+FileUtil.separator+languageName+".java";
		}else{
			newFilePath=codeDir+FileUtil.separator+submit.getSid()+".cpp";
		}
		
		FileUtil.createFile(newFilePath, submit.getCode());
		return true;
	}
	
	/**
	 * 为每个测试点创建对应的输入输出数据文件
	 * @param submit
	 * @param pointList
	 * @param testPointDir
	 * @return
	 */
	private boolean createTestDataFiles(Submit submit,List<TestPoint> pointList,String testPointDir){
		for(TestPoint point:pointList){
			String inputPointFileName=testPointDir+FileUtil.separator+point.getPointId()+".in";
			if(!FileUtil.createFile(inputPointFileName, point.getInput())){
				return false;
			}
			String outputPointFileName=testPointDir+FileUtil.separator+point.getPointId()+".out";
			if(!FileUtil.createFile(outputPointFileName, point.getOutput())){
				return false;
			}
		}
		return true;
	}
	/**
	 * 找出Java代码中的类名
	 * @param code
	 * @return
	 */
	public String findClassName(String code){
		String className="";
		Pattern pattern=Pattern.compile("(public)?\\s+\\S*\\s+class\\s+(\\S+)");
		Matcher matcher=pattern.matcher(code);
		if(matcher.find()){
			className=matcher.group(2);
		}
		return className;
	}
}
