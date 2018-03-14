package com.hhoj.judger.mapper;

import java.util.List;

import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.Submit;

/**
 * Submit 映射器
 * @author Administrator
 *
 */
public interface SubmitMapper {
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
	public List<Submit> findSubmits(Submit submit);
	
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
}
