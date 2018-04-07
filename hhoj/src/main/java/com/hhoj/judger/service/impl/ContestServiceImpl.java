package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhoj.judger.entity.Contest;
import com.hhoj.judger.entity.ContestProblem;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.mapper.ContestMapper;
import com.hhoj.judger.mapper.ContestProblemMapper;
import com.hhoj.judger.service.ContestService;
import com.hhoj.judger.service.ProblemService;

@Service("contestServiceImpl")
public class ContestServiceImpl implements ContestService{
	
	@Autowired
	private ContestMapper contestMapper;
	
	@Autowired
	private ContestProblemMapper contestProblemMapper;
	
	@Autowired
	private ProblemService problemService;
	
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

	@Override
	public List<ContestProblem> findContestProblems(Integer contestId) {
		 List<ContestProblem> result=null;
		if(contestId!=null) {
			result=contestProblemMapper.findContestProblems(contestId);
		}
		return result;
	}

	@Override
	public Integer addContestProblem(ContestProblem contestProblem) {
		if(contestProblem==null) {
			return -1;
		}
		Problem problem=problemService.findProblemById(contestProblem.getProblem().getPid());
		if(problem==null) {
			return -1;
		}
		ContestProblem cProblem=this.findContestProblemByPidAndContestId(
				contestProblem.getProblem().getPid(),contestProblem.getContestId());
		if(cProblem!=null) {
			return -1;
		}
		return contestProblemMapper.addContestProblem(contestProblem);
	}

	@Override
	public Integer removeContestProblem(Integer cpId) {
		Integer result=-1;
		if(cpId!=null) {
			result=contestProblemMapper.removeContestProblem(cpId);
		}
		return result;
	}

	@Override
	public ContestProblem findContestProblemByPidAndContestId(Integer pid, Integer contestId) {
		ContestProblem contestProblem=null;
		if(pid!=null&&contestId!=null) {
			contestProblem=contestProblemMapper.findContestProblemByPidAndContestId(pid, contestId);
		}
		return contestProblem;
	}

}
