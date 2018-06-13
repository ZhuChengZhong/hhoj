package com.hhoj.judger.handler;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hhoj.judger.entity.Submit;

/**
 * 针对Java语言的处理类
 * @author Administrator
 *
 */
public class JavaHandler extends AbstractHandler{
	//Java文件后缀
	public static final String CODE_SUFFIX=".java";
	//class文件后缀
	public static final String PROGRAM_SUFFIX=".class";
	/**
	 * 为用户提交的java代码创建源码文件
	 */
	public void createCodeFile(Submit submit,Map<String,Object>paths) {
	    String className=findClassName(submit.getCode());
	    if(className==null)
		   return ;
	    generalProgramFile(submit, className, CODE_SUFFIX, PROGRAM_SUFFIX, paths);
	}
	/**
	 * 找出Java代码中的类名
	 * @param code
	 * @return
	 */
	public String findClassName(String code){
		String className="";
		Pattern pattern=Pattern.compile("public\\s+(\\S*\\s+)?class\\s+([^\\s{]+)");
		Matcher matcher=pattern.matcher(code);
		if(matcher.find()){
			className=matcher.group(2);
		}
		return className;
	}


	/**
	 * 获取Java语言的编译命令
	 */
	@Override
	public String getCompileCommand(String codeFilePath,String programFilePath) {
		String programFileDir=programFilePath.substring(0, programFilePath.lastIndexOf("/")); 
		String commandLine="javac -d "+programFileDir+" "+codeFilePath;
		return commandLine;
	}


	/**
	 * 获取Java语言的运行命令
	 */
	@Override
	public String getRunCommand(String programFilePath) {
		int index=programFilePath.indexOf("/");
		String classPath=programFilePath.substring(0, index);
		String className=programFilePath.substring(index+1);
		int i=className.indexOf(".");
		className=className.substring(0,i);
		String command="java -cp "+classPath+" "+className;
		return command;
	}

}
