package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.mapper.SubmitMapper;
import com.hhoj.judger.service.SubmitService;

@Service("submitService")
public class SubmitServiceImpl implements SubmitService{
	
	@Autowired
	private SubmitMapper submitMapper;
	
	@Override
	public Submit findSubmitById(Integer sid) {
		return submitMapper.findSubmitById(sid);
	}

	@Override
	public List<Submit> findSubmits(Submit submit,PageBean pageBean) {
		return submitMapper.findSubmits(submit,pageBean);
	}

	@Override
	public Integer addSubmit(Submit submit) {
		return submitMapper.addSubmit(submit);
	}

	@Override
	public Integer removeSubmit(Integer sid) {
		return submitMapper.removeSubmit(sid);
	}

	@Override
	public Integer updateSubmit(Submit submit) {
		return submitMapper.updateSubmit(submit);
	}

	@Override
	public Integer findCount(Submit submit) {
		return submitMapper.findCount(submit);
	}

	@Override
	public List<Submit> findContestSubmits(Integer contestId,PageBean pageBean) {
		if(contestId==null&&pageBean!=null) {
			return null;
		}
		return submitMapper.findContestSubmits(contestId,pageBean);
	}

	@Override
	public Integer findContestSubmitCount(Integer contestId) {
		if(contestId==null) {
			return null;
		}
		return submitMapper.findContestSubmitCount(contestId);
	}

}
