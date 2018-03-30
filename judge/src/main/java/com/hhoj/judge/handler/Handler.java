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
	 * 准备工作 将所有的测试数据以文件的形式保存到指定目录 将用户代码以文件的形式保存到指定目录
	 * 
	 * @param submit
	 *            提交实体类
	 * @param pointList
	 *            测试点的集合
	 * @return 返回创建的代码文件名称
	 */
	public String prepare(Submit submit, List<TestPoint> pointList);
	
	/**
	 * 编译程序
	 * @param submit
	 * @param fileName
	 * @return
	 */
	public boolean compile(Submit submit, String fileName);
	
	/**
	 * 运行程序
	 * @param submit
	 * @param fileName
	 * @param pointList
	 * @return
	 */
	public boolean run(Submit submit, String fileName, List<TestPoint> pointList);
	
	/**
	 * 比较输出结果是否正确
	 * @param submit
	 * @param pointList
	 * @return
	 */
	public boolean compare(Submit submit, List<TestPoint> pointList);

	/**
	 * 清理产生的文件
	 * @return
	 */
	public boolean clean();
	
	/**
	 * 处理提交
	 * @param submit
	 * @param pointList
	 */
	public void handlerSubmit(Submit submit, List<TestPoint> pointList);
}
