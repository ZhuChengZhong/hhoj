package com.hhoj.judge.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hhoj.judge.constant.ConfigConstant;
import com.hhoj.judge.constant.ResultConstant;
import com.hhoj.judge.core.Runner;
import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.entity.TestPoint;
import com.hhoj.judge.util.FileUtil;

/**
 * 核心处理器
 * 
 * @author Administrator
 *
 */
public abstract class AbstractHandler implements Handler {
	/**
	 * 调用本地方法进行编译运行的类
	 */
	private Runner runner = new Runner();

	protected String dataFileDir;
	protected String programFileDir;
	private String programFileName;

	public AbstractHandler(String dataFileDir, String programFileDir) {
		this.dataFileDir = dataFileDir;
		this.programFileDir = programFileDir;
	}

	/**
	 * 
	 * 用于对用户的提交进行处理
	 */
	public void handlerSubmit(Submit submit, List<TestPoint> pointList) {
		boolean failed = false;
		String fileName = this.prepare(submit, pointList);
		if (compile(submit, fileName) && run(submit, fileName, pointList) && compare(submit, pointList)) {
			submit.setResult(ResultConstant.AC);
		}
		submit.setJudged(1);
		this.clean();
	}

	/**
	 * 根据用户提交的源码创建源码文件 不同的语言创建源码文件的方式不同，所以该方法留给子类实现
	 * 
	 * @param submit
	 * @param codeDir
	 * @return
	 */
	public abstract String createCodeFile(Submit submit, String codeDir);

	/**
	 * 获取编译程序命令
	 * 
	 * @param fileName
	 * @return
	 */
	public abstract String getCompileCommand(Submit submit, String fileName);

	/**
	 * 获取运行程序命令
	 * 
	 * @param fileName
	 * @return
	 */
	public abstract String getRunCommand(Submit submit, String fileName);

	
	/**
	 * 编译程序
	 */
	public boolean compile(Submit submit, String fileName) {
		String commandLine = this.getCompileCommand(submit, fileName);
		Map<String, Object> map = runner.runCommand(commandLine, null, null, "", "", submit.getProblem().getTimeLimit(),
				submit.getProblem().getMemaryLimit());
		Integer exitCode = (Integer) map.get("exitCode");
		if (exitCode != 0) {
			submit.setResult(ResultConstant.CE);
			return false;
		}
		return true;
	}
	
	/**
	 * 运行程序
	 */
	public boolean run(Submit submit, String fileName, List<TestPoint> pointList) {
		String commandLine=this.getRunCommand(submit, fileName);
		int usedTime = 0;
		int usedMemory = 0;
		int timeLimit=submit.getProblem().getTimeLimit();
		int memaryLimit=submit.getProblem().getMemaryLimit();
		for (int i = 0; i < pointList.size(); i++) {
			String inputFilePath = dataFileDir + FileUtil.separator + submit.getSid() + "/in/"
					+ pointList.get(i).getPointId() + ".in";
			String outputFilePath = dataFileDir + FileUtil.separator + submit.getSid() + "/userout/"
					+ pointList.get(i).getPointId() + ".out";
			Map<String, Object> map = runner.runCommand(commandLine, null, null, inputFilePath, outputFilePath,
					timeLimit, memaryLimit);
			Integer exitCode = (Integer) map.get("exitCode");
			if (exitCode != 0) {
				submit.setResult(ResultConstant.RE);
				return false;
			}
			usedTime += (Integer) map.get("usedTime");
			usedMemory = Math.max(usedMemory, (Integer) map.get("usedMemory"));
		}
		submit.setUseTime(usedTime);
		submit.setUseMemary(usedMemory);
		if(usedTime>timeLimit) {
			submit.setResult(ResultConstant.TLE);
			return false;
		}
		if(usedMemory>timeLimit) {
			submit.setResult(ResultConstant.MLE);
			return false;
		}
		return true;
	}
	
	/**
	 * 比较结果
	 */
	public boolean compare(Submit submit, List<TestPoint> pointList) {
		for (int i = 0; i < pointList.size(); i++) {
			String standardOutputFilePath = dataFileDir + FileUtil.separator + submit.getSid() + "/out/"
					+ pointList.get(i).getPointId() + ".out";
			String userOutputFilePath = dataFileDir + FileUtil.separator + submit.getSid() + "/userout/"
					+ pointList.get(i).getPointId() + ".out";
			boolean failed = FileUtil.compare(standardOutputFilePath, userOutputFilePath);
			if (!failed) {
				submit.setResult(ResultConstant.WA);
				return failed;
			}
		}
		return true;
	}

	/**
	 * 准备工作 将所有的测试数据以文件的形式保存到指定目录 将用户代码以文件的形式保存到指定目录
	 * 
	 * @param submit
	 *            提交实体类
	 * @param pointList
	 *            测试点的集合
	 * @return 返回创建的代码文件名称
	 */
	public String prepare(Submit submit, List<TestPoint> pointList) {
		String inFileDir = dataFileDir + FileUtil.separator + submit.getSid() + "/in";
		String outFileDir = dataFileDir + FileUtil.separator + submit.getSid() + "/out";
		String userOutFileDir = dataFileDir + FileUtil.separator + submit.getSid() + "/userout";
		String detailProgramFileDir = programFileDir + FileUtil.separator + submit.getSid();
		FileUtil.mkdir(inFileDir);
		FileUtil.mkdir(outFileDir);
		FileUtil.mkdir(userOutFileDir);
		FileUtil.mkdir(detailProgramFileDir);
		// 创建测试数据
		createTestDataFiles(submit, pointList, inFileDir, outFileDir);
		// 根据用户提交的源码创建源码文件
		return createCodeFile(submit, programFileDir);
	}

	/**
	 * 生成测试数据
	 * 
	 * @param submit
	 * @param pointList
	 * @param inDataDir
	 * @param outDataDir
	 * @return
	 */
	public boolean createTestDataFiles(Submit submit, List<TestPoint> pointList, String inDataDir, String outDataDir) {
		List<String> inList = new ArrayList<String>();
		List<String> outList = new ArrayList<String>();
		for (TestPoint point : pointList) {
			String inputPointFileName = inDataDir + FileUtil.separator + point.getPointId() + ".in";
			if (!FileUtil.createFile(inputPointFileName, point.getInput())) {
				return false;
			}
			inList.add(inputPointFileName);
			String outputPointFileName = outDataDir + FileUtil.separator + point.getPointId() + ".out";
			if (!FileUtil.createFile(outputPointFileName, point.getOutput())) {
				return false;
			}
			outList.add(outputPointFileName);
		}
		return true;
	}

	/**
	 * 清理处理调教产生的文件文件
	 */
	public boolean clean() {

		return true;
	}

}
