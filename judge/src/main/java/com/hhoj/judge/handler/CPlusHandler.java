package com.hhoj.judge.handler;

import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.util.FileUtil;

/**
 * C/C++语言 提交处理类
 * @author zhu
 *
 */
public class CPlusHandler extends AbstractHandler{

	public CPlusHandler(String dataFileDir, String programFileDir) {
		super(dataFileDir, programFileDir);
	}

	/**
	 * 为用户提交的C++代码创建源码文件
	 */
	@Override
	public String createCodeFile(Submit submit, String programFileDir) {
		String fileName="program"+submit.getSid();
	    String newFilePath=programFileDir+FileUtil.separator+submit.getSid()+FileUtil.separator+fileName+".cpp";
		FileUtil.createFile(newFilePath, submit.getCode());
		return fileName;
	}

	
	/**
	 * 获取c++编译命令
	 */
	@Override
	public String getCompileCommand(Submit submit, String fileName) {
		String filePath=programFileDir+FileUtil.separator+submit.getSid()+FileUtil.separator+fileName;
		String compileCommand="g++ "+filePath+".cpp"+" -o "+filePath;
		return compileCommand;
	}

	
	
	/**
	 * 获取c++运行命令
	 */
	@Override
	public String getRunCommand(Submit submit, String fileName) {
		String detailProgramFilePath=programFileDir+FileUtil.separator+submit.getSid()+FileUtil.separator+fileName;
		String commandLine=detailProgramFilePath;
		return commandLine;
	}

}
