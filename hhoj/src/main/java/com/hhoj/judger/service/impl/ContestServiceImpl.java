package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhoj.judger.entity.Contest;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.mapper.ContestMapper;
import com.hhoj.judger.service.ContestService;

@Service("contestServiceImpl")
public class ContestServiceImpl implements ContestService{
	
	@Autowired
	private ContestMapper contestMapper;
	@Override
	public Integer addContest(Contest contest) {
		Integer result=null;
		if(contest!=null) {
			result=contestMapper.addContest(contest);
		}
		return result;
	}

	@Override
	public Integer removeContest(Integer contestId) {
		Integer result=null;
		if(contestId!=null) {
			result=contestMapper.removeContest(contestId);
		}
		return result;
	}

	@Override
	public Integer updateContest(Contest contest) {
		Integer result=null;
		if(contest!=null) {
			result=contestMapper.updateContest(contest);
		}
		return result;
	}

	@Override
	public Contest findContestById(Integer contestId) {
		Contest contest=null;
		if(contestId!=null) {
			contest=contestMapper.findContestById(contestId);
		}
		return contest;
	}

	@Override
	public List<Contest> findContests(PageBean pageBean) {
		List<Contest> list=null;
		if(pageBean!=null) {
			list=contestMapper.findContests(pageBean);
		}
		return list;
	}

	@Override
	public Integer findCount() {
		return contestMapper.findCount();
	}

}
