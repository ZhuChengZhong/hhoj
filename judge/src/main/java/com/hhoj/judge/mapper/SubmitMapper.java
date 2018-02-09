package com.hhoj.judge.mapper;

import com.hhoj.judge.entity.Submit;

public interface SubmitMapper {
	/**
	 * 通过id查找Submit
	 * @param sid
	 * @return
	 */
    public Submit findById(int submitId);
    
    /**
     * 插入Submit对象
     * @param submit
     */
    public void insert(Submit submit);
    /**
     * 更新Submit对象
     * @param submit
     */
    public void update(Submit submit);
    
    /**
     * 获取提交总次数
     * @return
     */
    public int count();
}
