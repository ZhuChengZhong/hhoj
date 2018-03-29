package com.hhoj.judge.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hhoj.judge.constant.ConfigConstant;
import com.hhoj.judge.constant.ResultConstant;
import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.entity.TestPoint;
import com.hhoj.judge.util.FileUtil;

/**
 * 核心处理器
 * @author Administrator
 *
 */
public abstract class AbstractHandler implements Handler{
	protected String dataFileDir;
	protected String programFileDir;
	private String programFileName;
	public AbstractHandler(String dataFileDir,String programFileDir) {
		this.dataFileDir=dataFileDir;
		this.programFileDir=programFileDir;
	}
	public boolean clean() {
		return false;
	}
	
	/**
	 *  根据用户提交的源码创建源码文件
	 *  不同的语言创建源码文件的方式不同，所以该方法留给子类实现
	 * @param submit
	 * @param codeDir
	 * @return
	 */
	public abstract String createCodeFile(Submit submit, String codeDir);
	
	
	  public abstract boolean compile(Submit submit,String fileName);
	  
		 
	  public abstract boolean  run(Submit submit,String fileName,List<TestPoint> pointList);
	 
	  public abstract boolean compare(Submit submit,List<TestPoint> pointList);
	  
	/**
	 * 模板方法
	 * 用于对用户的提交进行处理
	 */
	public void handlerSubmit(Submit submit,List<TestPoint> pointList) {
		boolean failed=false;
		String fileName=this.prepare(submit, pointList);
		if(compile(submit,fileName)&&run(submit,fileName,pointList)&&compare(submit,pointList)) {
			submit.setResult(ResultConstant.AC);
		}
		submit.setJudged(1);
		this.clean();
	}
	
	/**
	 * 准备工作
	 * 将所有的测试数据以文件的形式保存到指定目录
	 * 将用户代码以文件的形式保存到指定目录
	 * @param submit    提交实体类
	 * @param pointList 测试点的集合
	 * @return   返回创建的代码文件名称
	 */
	public String prepare(Submit submit, List<TestPoint> pointList) {
		String inFileDir=dataFileDir+FileUtil.separator+submit.getSid()+"/in";
		String outFileDir=dataFileDir+FileUtil.separator+submit.getSid()+"/out";
		String userOutFileDir=dataFileDir+FileUtil.separator+submit.getSid()+"/userout";
		String detailProgramFileDir=programFileDir+FileUtil.separator+submit.getSid();
		FileUtil.mkdir(inFileDir);
		FileUtil.mkdir(outFileDir);
		FileUtil.mkdir(userOutFileDir);
		FileUtil.mkdir(detailProgramFileDir);
		//创建测试数据
		createTestDataFiles(submit,pointList,inFileDir,outFileDir);
		//根据用户提交的源码创建源码文件
		return createCodeFile(submit,programFileDir);
	}
	
	
	/**
	 * 生成测试数据
	 * @param submit
	 * @param pointList
	 * @param inDataDir
	 * @param outDataDir
	 * @return
	 */
	public boolean createTestDataFiles(Submit submit,List<TestPoint> pointList,String inDataDir,String outDataDir){
		List<String>inList=new ArrayList<String>();
		List<String>outList=new ArrayList<String>();
		for(TestPoint point:pointList){
			String inputPointFileName=inDataDir+FileUtil.separator+point.getPointId()+".in";
			if(!FileUtil.createFile(inputPointFileName, point.getInput())){
				return false;
			}
			inList.add(inputPointFileName);
			String outputPointFileName=outDataDir+FileUtil.separator+point.getPointId()+".out";
			if(!FileUtil.createFile(outputPointFileName, point.getOutput())){
				return false;
			}
			outList.add(outputPointFileName);
		}
		return true;
	}

}
