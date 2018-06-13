package com.hhoj.judger.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhoj.judger.entity.JudgeResult;
import com.hhoj.judger.entity.Message;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Submit;

public interface SubmitService {
	/**
	 * 通过id查找Submit
	 * @param pId
	 * @return
	 */
	public Submit findSubmitById(Integer sid);
	
	/**
	 * 查找Submit集合 
	 * @param submit 条件，为null时表示无限制条件查找
	 * @return
	 */
	public List<Submit> findSubmits(Submit submit,PageBean pageBean);
	
	/**
	 * 添加Submit
	 * @param submit
	 * @return
	 */
	public Integer addSubmit(Submit submit);
	
	
	/**
	 * 删除Submit
	 * @param sId
	 * @return
	 */
	public Integer removeSubmit(Integer sid);
	
	/**
	 * 更新Submit
	 * @param submit
	 * @return
	 */
	public Integer updateSubmit(Submit submit);
	
	/**
	 * 查找Submit 数量
	 * @param submit 条件，为null时表示无限制条件查找
	 * @return
	 */
	public Integer findCount(Submit submit);
	
	/**
	 *  查找比赛提交列表
	 * @param contestId
	 * @return
	 */
	public List<Submit> findContestSubmits(Integer contestId,PageBean pageBean);
	
	/**
	 * 查找比赛提交个数
	 * @param contestId
	 * @return
	 */
	public Integer findContestSubmitCount(Integer contestId);
	
	/**
	 * 将提交转换为消息
	 * @param submit
	 * @return
	 */
	public String transforToMessage(Submit submit);
	
	/**
	 * 将判题结果转换为Submit并更新
	 * @param jr
	 * @return
	 */
	public Integer updateSubmit(JudgeResult jr);
}
