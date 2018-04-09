package com.hhoj.judger.handler;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hhoj.judger.constant.ResultConstant;
import com.hhoj.judger.core.Runner;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.entity.TestPoint;
import com.hhoj.judger.util.FileUtil;

/**
 * 针对Java语言的处理类
 * @author Administrator
 *
 */
public class JavaHandler extends AbstractHandler{
	public JavaHandler(String dataFileDir, String programFileDir) {
		super(dataFileDir, programFileDir);
	}
	
	/**
	 * 为用户提交的java代码创建源码文件
	 */
	@Override
	public String createCodeFile(Submit submit, String codeDir) {
	    String className=findClassName(submit.getCode());
	    if(className==null)
		   return null;
	    String newFilePath=codeDir+FileUtil.separator+submit.getSid()+FileUtil.separator+className+".java";
		FileUtil.createFile(newFilePath, submit.getCode());
		return className;
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
	public String getCompileCommand(Submit submit,String fileName) {
		String programFilePath=programFileDir+FileUtil.separator+submit.getSid()+FileUtil.separator+fileName+".java";
		String commandLine="javac "+programFilePath;
		return commandLine;
	}


	/**
	 * 获取Java语言的运行命令
	 */
	@Override
	public String getRunCommand(Submit submit,String fileName) {
		String detailProgramFileDir=programFileDir+FileUtil.separator+submit.getSid();
		String commandLine="java -cp "+detailProgramFileDir+"   "+fileName;
		return commandLine;
	}

}
