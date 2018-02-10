package com.hhoj.judge.handler;

import java.util.List;
import java.util.Map;

import com.hhoj.judge.entity.Submit;
import com.hhoj.judge.entity.TestPoint;

/**
 * 核心处理器
 * @author Administrator
 *
 */
public interface Handler {
	/**
	 * 准备工作
	 * @param submit
	 * @param pointList
	 * @param codePath
	 * @param testPointPath
	 * @return
	 */
	public boolean prepare(Submit submit,List<TestPoint> pointList,String codePath,String testPointPath,Map<String,Object>map);
	/**
	   * 将用户提交的代码编译成可执行文件
	   * codePath  待编译文件路径
	   * codeName  文件名
	   * @return
	   */
	  public  boolean compile(Submit submit,String codePath,String codeName,Map<String,Object>map);
	  
	  /**
	   * 运行程序
	   * @param submit
	   * @param programPath 待运行程序路径
	   * @param programName 程序名
	   * @param inList
	   * @param outList
	   */
	  public  void run(Submit submit,String programPath,String programName,List<String>inList,List<String>outList);
	  /**
	   * 比较输出结果是否与答案相同
	   * @param standardOutPath  标准答案
	   * @param userOutPath      用户输出
	   * @return
	   */
	  public boolean compare(String standardOutPath,String userOutPath);
	  
	  /**
	   * 清理产生的临时文件
	   * @param codeDir
	   * @param userOutDir
	   * @return
	   */
	  public boolean clean(String codeDir,String userOutDir);
}
