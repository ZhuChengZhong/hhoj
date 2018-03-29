package com.hhoj.judge.handler;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hhoj.judge.constant.ResultConstant;
import com.hhoj.judge.core.Runner;
import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.entity.TestPoint;
import com.hhoj.judge.util.FileUtil;

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
	 * 调用本地方法进行编译运行的类
	 */
	private Runner runner=new Runner();

	public String prepare(Submit submit, List<TestPoint> pointList) {
		String inFileDir=dataFileDir+FileUtil.separator+submit.getSid()+"/in";
		String outFileDir=dataFileDir+FileUtil.separator+submit.getSid()+"/out";
		String userOutFileDir=dataFileDir+FileUtil.separator+submit.getSid()+"/userout";
		String detailProgramFileDir=programFileDir+FileUtil.separator+submit.getSid();
		FileUtil.mkdir(inFileDir);
		FileUtil.mkdir(outFileDir);
		FileUtil.mkdir(userOutFileDir);
		FileUtil.mkdir(detailProgramFileDir);
		createTestDataFiles(submit,pointList,inFileDir,outFileDir);
		return createCodeFile(submit,programFileDir);
	}
	
	@Override
	public boolean compile(Submit submit, String fileName) {
		String programFilePath=programFileDir+FileUtil.separator+submit.getSid()+FileUtil.separator+fileName+".java";
		String commandLine="javac "+programFilePath;
		Map<String, Object>map=runner.runCommand(commandLine, null, null, null, null, 0, 0);
		Integer exitCode=(Integer)map.get("exitCode");
		if(exitCode!=0) {
			submit.setResult(ResultConstant.CE);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean run(Submit submit, String fileName,List<TestPoint> pointList) {
		String detailProgramFileDir=programFileDir+FileUtil.separator+submit.getSid();
		String commandLine="java -cp "+detailProgramFileDir+"   "+fileName;
		int usedTime=0;
		int usedMemory=0;
		for(int i=0;i<pointList.size();i++) {
			String inputFilePath=dataFileDir+FileUtil.separator+submit.getSid()+"/in/"+pointList.get(i).getPointId()+".in";
			String outputFilePath=dataFileDir+FileUtil.separator+submit.getSid()+"/userout/"+pointList.get(i).getPointId()+".out";
			Map<String,Object>map=runner.runCommand(commandLine, null, null, inputFilePath, outputFilePath, submit.getProblem().getTimeLimit(), submit.getProblem().getMemaryLimit());
			Integer exitCode=(Integer)map.get("exitCode");
			if(exitCode!=0) {
				submit.setResult(ResultConstant.RE);
				return false;
			}
			usedTime+=(Integer)map.get("usedTime");
			usedMemory+=(Integer)map.get("usedMemory");
		}
		submit.setUseTime(usedTime);
		submit.setUseMemary(usedMemory);
		return true;
	}

	public boolean compare(Submit submit,List<TestPoint> pointList) {
		for(int i=0;i<pointList.size();i++) {
			String standardOutputFilePath=dataFileDir+FileUtil.separator+submit.getSid()+"/out/"+pointList.get(i).getPointId()+".out";
			String userOutputFilePath=dataFileDir+FileUtil.separator+submit.getSid()+"/userout/"+pointList.get(i).getPointId()+".out";
			boolean failed=FileUtil.compare(standardOutputFilePath, userOutputFilePath);
			if(!failed) {
				submit.setResult(ResultConstant.WA);
				return failed;
			}
		}
		return true;
	}
	
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
		Pattern pattern=Pattern.compile("(public)?\\s+\\S*\\s+class\\s+(\\S+)");
		Matcher matcher=pattern.matcher(code);
		if(matcher.find()){
			className=matcher.group(2);
		}
		return className;
	}

}
