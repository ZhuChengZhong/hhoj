package com.hhoj.judge.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.entity.TestPoint;
import com.hhoj.judge.util.FileUtil;

/**
 * 核心处理器
 * @author Administrator
 *
 */
public abstract class AbstractHandler implements Handler{
	/**
	 * 准备工作
	 * @param submit
	 * @param pointList
	 * @param codePath
	 * @param testPointPath
	 * @return
	 */
	public boolean prepare(Submit submit,List<TestPoint> pointList,String codePath,String testPointPath,Map<String,Object>map){
		String codeDir=codePath+FileUtil.separator+submit.getSid();
		map.put("codeDir",codeDir);
		if(!FileUtil.mkdir(codeDir)||!createCodeFile(submit,codeDir,map)){
			return false;
		}
		String testPointDir=testPointPath+FileUtil.separator+submit.getSid();
		map.put("testPointDir",testPointDir);
		if(!FileUtil.mkdir(testPointDir)||!createTestDataFiles(submit,pointList,testPointDir,map)){
			return false;
		}
		return true;
	}
	/**
	 * 将用户提交的代码保存为对应的源代码文件用于之后的编译运行
	 * @param submit
	 * @param codeDir
	 * @return
	 */
	public abstract boolean createCodeFile(Submit submit,String codeDir,Map<String,Object>map);
		

	
	/**
	 * 为每个测试点创建对应的输入输出数据文件
	 * @param submit
	 * @param pointList
	 * @param testPointDir
	 * @return
	 */
	private boolean createTestDataFiles(Submit submit,List<TestPoint> pointList,String testPointDir,Map<String,Object>map){
		List<String>inList=new ArrayList<String>();
		List<String>outList=new ArrayList<String>();
		for(TestPoint point:pointList){
			String inputPointFileName=testPointDir+FileUtil.separator+point.getPointId()+".in";
			if(!FileUtil.createFile(inputPointFileName, point.getInput())){
				return false;
			}
			inList.add(inputPointFileName);
			String outputPointFileName=testPointDir+FileUtil.separator+point.getPointId()+".out";
			if(!FileUtil.createFile(outputPointFileName, point.getOutput())){
				return false;
			}
			outList.add(outputPointFileName);
		}
		map.put("inList", inList);
		map.put("outList", outList);
		return true;
	}
	
	
	  /**
	   * 比较输出结果是否与答案相同
	   * @param standardOutPath  标准答案
	   * @param userOutPath      用户输出
	   * @return
	   */
	  public boolean compare(String standardOutPath,String userOutPath){
		  
		  return FileUtil.compare(standardOutPath, userOutPath);
	  }
		
	  /**
	   * 清理产生的临时文件
	   * @param codeDir
	   * @param userOutDir
	   * @return
	   */
	  public boolean clean(String codeDir,String userOutDir){
		 return FileUtil.rmdir(codeDir)&&FileUtil.rmdir(userOutDir);
	  }
	
	  
	
}
