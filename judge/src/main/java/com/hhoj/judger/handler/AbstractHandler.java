package com.hhoj.judger.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hhoj.judger.constant.ResultConstant;
import com.hhoj.judger.core.Executor;
import com.hhoj.judger.entity.ExecResult;
import com.hhoj.judger.entity.JudgeResult;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.entity.TestPoint;
import com.hhoj.judger.util.FileUtil;
import com.hhoj.judger.util.PropertiesUtil;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;

/**
 * 核心处理器
 * 
 * @author Administrator
 *
 */
public abstract class AbstractHandler implements Handler {

	private static Logger logger=LoggerFactory.getLogger(AbstractHandler.class);
	
	public static final String INPUT_FILE_PATH_LIST_KEY = "inputFileList";

	public static final String OUTPUT_FILE_PATH_LIST_KEY = "outputFileList";

	public static final String USER_OUTPUT_FILE_PATH_LIST_KEY = "userOutputFileList";

	public static final String RESULT_FILE_PATH_KEY = "resultFilePath";

	public static final String CODE_FILE_PATH_KEY = "codeFilePath";

	public static final String PROGRAM_FILE_PATH_KEY = "programFilePath";

	public static final String INPUT_FILE_PATH;

	public static final String OUTPUT_FILE_PATH;

	public static final String USER_OUTPUT_FILE_PATH;

	public static final String CODE_FILE_PATH;

	public static final String PROGRAM_FILE_PATH;

	public static final String RESULT_FILE_PATH;

	public static final String SUBMIT_ID_PLACEHOLDER = "SUBMIT_ID_PLACEHOLDER";

	public static final String POINT_ID_PLACEHOLDER = "POINT_ID_PLACEHOLDER";

	public static final String PROGRAM_NAME_PLACEHOLDER = "PROGRAM_NAME_PLACEHOLDER";

	public static final String PROGRAM_SUFFIX_PLACEHOLDER = "PROGRAM_SUFFIX_PLACEHOLDER";

	public static final String WORKSPACE;

	static {
		WORKSPACE = PropertiesUtil.getParam("workspace");

		INPUT_FILE_PATH = WORKSPACE + FileUtil.separator + "input" + FileUtil.separator + SUBMIT_ID_PLACEHOLDER
				+ FileUtil.separator + POINT_ID_PLACEHOLDER + ".in";

		OUTPUT_FILE_PATH = WORKSPACE + FileUtil.separator + "output" + FileUtil.separator + SUBMIT_ID_PLACEHOLDER
				+ FileUtil.separator + POINT_ID_PLACEHOLDER + ".out";

		USER_OUTPUT_FILE_PATH = WORKSPACE + FileUtil.separator + "useroutput" + FileUtil.separator
				+ SUBMIT_ID_PLACEHOLDER + FileUtil.separator + POINT_ID_PLACEHOLDER + ".out";

		CODE_FILE_PATH = WORKSPACE + FileUtil.separator + "code" + FileUtil.separator + SUBMIT_ID_PLACEHOLDER
				+ FileUtil.separator + PROGRAM_NAME_PLACEHOLDER + PROGRAM_SUFFIX_PLACEHOLDER;

		PROGRAM_FILE_PATH = WORKSPACE + FileUtil.separator + "program" + FileUtil.separator + SUBMIT_ID_PLACEHOLDER
				+ FileUtil.separator + PROGRAM_NAME_PLACEHOLDER + PROGRAM_SUFFIX_PLACEHOLDER;

		RESULT_FILE_PATH = WORKSPACE + FileUtil.separator + "result" + FileUtil.separator + SUBMIT_ID_PLACEHOLDER
				+ FileUtil.separator + "res.txt";
	}

	private Executor executor = Executor.instance();

	public JudgeResult handlerSubmit(Submit submit) throws Exception {
		JudgeResult jr = new JudgeResult();
		jr.setSubmitId(submit.getSubmitId());
		Map<String, Object> paths = this.prepare(submit);
		if (compile(jr, submit, paths) && run(jr, submit, paths) && compare(jr, paths,submit)) {
			jr.setResult(ResultConstant.AC);
		}
		this.clean(submit);
		return jr;
	}

	public abstract void createCodeFile(Submit submit, Map<String, Object> paths);

	public abstract String getCompileCommand(String codeFilePath,String programFilePath);

	public abstract String getRunCommand(String programFilePath);

	public boolean compile(JudgeResult jr, Submit submit, Map<String, Object> paths) throws Exception {
		long start=System.currentTimeMillis();
		String codeFilePath = (String) paths.get(CODE_FILE_PATH_KEY);
		String programFilePath = (String) paths.get(PROGRAM_FILE_PATH_KEY);
		String resultFilePath = (String) paths.get(RESULT_FILE_PATH_KEY);
		String command = this.getCompileCommand(codeFilePath,programFilePath);
		ExecResult result = executor.exec(command, resultFilePath);
		long cost=System.currentTimeMillis()-start;
		logger.info("submit-id:"+submit.getSubmitId()+"  编译阶段花费时间："+cost+" ms");
		if(result==null) {
			jr.setResult(ResultConstant.TLE);
			return false;
		}
		Integer exitCode = result.getExitCode();
		if (exitCode != 0) {
			jr.setResult(ResultConstant.CE);
			return false;
		}
		return true;
	}

	public boolean run(JudgeResult jr, Submit submit, Map<String, Object> paths) throws Exception {
		long start=System.currentTimeMillis();
		String programFilePath = (String) paths.get(PROGRAM_FILE_PATH_KEY);
		String command = this.getRunCommand(programFilePath);
		int usedTime = 0;
		int usedMemory = 0;
		int timeLimit = submit.getTimeLimit();
		int memaryLimit = submit.getMemaryLimit();
		List<String> inputFilePathList = (List<String>) paths.get(INPUT_FILE_PATH_LIST_KEY);
		List<String> userOnputFilePathList = (List<String>) paths.get(USER_OUTPUT_FILE_PATH_LIST_KEY);
		String resultFilePath = (String) paths.get(RESULT_FILE_PATH_KEY);
		int len = inputFilePathList.size();
		for (int i = 0; i < len; i++) {
			String inputFilePath = inputFilePathList.get(i);
			String userOutputFilePath = userOnputFilePathList.get(i);
			ExecResult result = executor.exec(inputFilePath, userOutputFilePath, command, resultFilePath);
			if(result==null){
				jr.setResult(ResultConstant.TLE);
				break;
			}
			if (result.getExitCode() != 0) {
				jr.setResult(ResultConstant.RE);
				break;
			}
			usedTime += result.getUseTime();
			usedMemory = Math.max(result.getUseMemary(), usedMemory);
		}
		long cost=System.currentTimeMillis()-start;
		logger.info("submit-id:"+submit.getSubmitId()+"  运行阶段花费时间："+cost+" ms");
		if(jr.getResult()!=null) {
			return false;
		}
		if (usedTime > timeLimit) {
			jr.setResult(ResultConstant.TLE);
			return false;
		}
		if (usedMemory > memaryLimit) {
			jr.setResult(ResultConstant.MLE);
			return false;
		}
		jr.setUseTime(usedTime);
		jr.setUseMemary(usedMemory);
		return true;
	}

	public boolean compare(JudgeResult jr, Map<String, Object> paths,Submit submit) {
		long start=System.currentTimeMillis();
		List<String> outputFilePathList = (List<String>) paths.get(OUTPUT_FILE_PATH_LIST_KEY);
		List<String> userOutputFilePathList = (List<String>) paths.get(USER_OUTPUT_FILE_PATH_LIST_KEY);
		int len = outputFilePathList.size();
		for (int i = 0; i < len; i++) {
			String outputFilePath = outputFilePathList.get(i);
			String userOutputFilePath = userOutputFilePathList.get(i);
			if (!FileUtil.compare(outputFilePath, userOutputFilePath)) {
				jr.setResult(ResultConstant.WA);
				break;
			}
		}
		long cost=System.currentTimeMillis()-start;
		logger.info("submit-id:"+submit.getSubmitId()+"  比较阶段花费时间："+cost+" ms");
		if(jr.getResult()!=null) {
			return false;
		}
		return true;
	}

	public Map<String, Object> prepare(Submit submit) throws IOException {
		long start=System.currentTimeMillis();
		// 创建测试数据 及相应的文件
		Map<String, Object> paths = createTestDataFiles(submit);
		// 根据用户提交的源码创建源码文件
		createCodeFile(submit, paths);
		long cost=System.currentTimeMillis()-start;
		logger.info("submit-id:"+submit.getSubmitId()+"  准备阶段花费时间："+cost+" ms");
		return paths;
	}

	public Map<String, Object> createTestDataFiles(Submit submit) throws IOException {
		// 用来保存 输入测试数据 的文件路经
		List<String> inputFileList = new ArrayList<String>();
		// 用来保存 输出测试数据 的文件路经
		List<String> outputFileList = new ArrayList<String>();
		// 用来保存 用户输出 的文件路经
		List<String> userOutputFileList = new ArrayList<String>();
		List<TestPoint> pointList = submit.getPoints();
		for (int i = 1; i <= pointList.size(); i++) {
			TestPoint point = pointList.get(i-1);
			String inputFilePath = INPUT_FILE_PATH
					.replace(SUBMIT_ID_PLACEHOLDER, submit.getSubmitId() + "")
					.replace(POINT_ID_PLACEHOLDER, i + "");
			if (!FileUtil.createFile(inputFilePath, point.getInput())) {
				throw new IOException("创建文件失败");
			}
			inputFileList.add(inputFilePath);
			String outputFileName = OUTPUT_FILE_PATH
					.replace(SUBMIT_ID_PLACEHOLDER, submit.getSubmitId() + "")
					.replace(POINT_ID_PLACEHOLDER, i + "");
			if (!FileUtil.createFile(outputFileName, point.getOutput())) {
				throw new IOException("创建文件失败");
			}
			outputFileList.add(outputFileName);
			String userOutFilePath = USER_OUTPUT_FILE_PATH
					.replace(SUBMIT_ID_PLACEHOLDER, submit.getSubmitId() + "")
					.replace(POINT_ID_PLACEHOLDER, i + "");
			if (!FileUtil.createFile(userOutFilePath,"")) {
				throw new IOException("创建文件失败");
			}
			userOutputFileList.add(userOutFilePath);
		}
		String resultFilePath = RESULT_FILE_PATH
				.replace(SUBMIT_ID_PLACEHOLDER, submit.getSubmitId() + "");
		FileUtil.createFile(resultFilePath, "");
		Map<String, Object> paths = new HashMap<>();
		paths.put(INPUT_FILE_PATH_LIST_KEY, inputFileList);
		paths.put(OUTPUT_FILE_PATH_LIST_KEY, outputFileList);
		paths.put(USER_OUTPUT_FILE_PATH_LIST_KEY, userOutputFileList);
		paths.put(RESULT_FILE_PATH_KEY, resultFilePath);
		return paths;
	}
	
	public void generalProgramFile(Submit submit,String fileName,String codeSuffix,String programSufix,Map<String,Object>paths) {
		String codeFilePath=CODE_FILE_PATH.replaceFirst(SUBMIT_ID_PLACEHOLDER,submit.getSubmitId()+"")
	    		.replaceFirst(PROGRAM_NAME_PLACEHOLDER,fileName)
	    		.replaceFirst(PROGRAM_SUFFIX_PLACEHOLDER, codeSuffix);
		paths.put(CODE_FILE_PATH_KEY, codeFilePath);
		FileUtil.createFile(codeFilePath, submit.getCode());
		String programFilePath=PROGRAM_FILE_PATH.replaceFirst(SUBMIT_ID_PLACEHOLDER,submit.getSubmitId()+"")
				.replaceFirst(PROGRAM_NAME_PLACEHOLDER, fileName)
				.replaceFirst(PROGRAM_SUFFIX_PLACEHOLDER,programSufix);
		paths.put(PROGRAM_FILE_PATH_KEY, programFilePath);
		String programFileDir=programFilePath.substring(0, programFilePath.lastIndexOf("/"));
		FileUtil.mkdir(programFileDir);
	}
	
	public boolean clean(Submit submit) {
		long start=System.currentTimeMillis();
		
		long cost=System.currentTimeMillis()-start;
		logger.info("submit-id:"+submit.getSubmitId()+"  清理阶段花费时间："+cost+" ms");
		return true;
	}

}
