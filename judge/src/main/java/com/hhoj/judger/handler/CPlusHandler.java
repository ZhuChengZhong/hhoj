package com.hhoj.judger.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hhoj.judger.entity.JudgeResult;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.entity.TestPoint;

/**
 * C/C++语言 提交处理类
 * @author zhu
 *
 */
public class CPlusHandler extends AbstractHandler{
	//c++文件后缀
	public static final String CODE_SUFFIX=".cpp";
	//c++可执行文件后缀
	public static final String PROGRAM_SUFFIX=".o";
	@Override
	public void createCodeFile(Submit submit, Map<String, Object> paths) {
		// TODO Auto-generated method stub
		String fileName="program"+submit.getSubmitId();
	    generalProgramFile(submit, fileName, CODE_SUFFIX, PROGRAM_SUFFIX, paths);
	}

	@Override
	public String getCompileCommand(String codeFilePath,String programFilePath) {
		// TODO Auto-generated method stub
		String command="g++ -o "+programFilePath+" "+codeFilePath;
		return command;
	}

	@Override
	public String getRunCommand(String programFilePath) {
		return programFilePath;
	}

	public static void main(String[] args) throws Exception {
		try {
			Handler handler=new CPlusHandler();
			String code="#include<stdio.h>\nint main() {printf(\"hahaha\");}";
			List<TestPoint> points=new ArrayList<>();
			TestPoint p1=new TestPoint("","hahaha");
			points.add(p1);
			Submit submit=new Submit(1, 1000, 65535, "Java",code, points);
			JudgeResult result=handler.handlerSubmit(submit);
			System.out.println(result);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
		}
	}

}
