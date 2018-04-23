package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhoj.judger.entity.ContestUser;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.mapper.SubmitMapper;
import com.hhoj.judger.service.ContestService;
import com.hhoj.judger.service.ProblemService;
import com.hhoj.judger.service.SubmitService;

@Service("submitService")
public class SubmitServiceImpl implements SubmitService {

	@Autowired
	private SubmitMapper submitMapper;

	@Autowired
	private ProblemService problemService;

	@Autowired
	private ContestService contestService;

	@Override
	public Submit findSubmitById(Integer sid) {
		return submitMapper.findSubmitById(sid);
	}

	@Override
	public List<Submit> findSubmits(Submit submit, PageBean pageBean) {
		return submitMapper.findSubmits(submit, pageBean);
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
	@Transactional
	public Integer updateSubmit(Submit submit) {
		Submit s = this.findSubmitById(submit.getSid());
		Problem problem = s.getProblem();
		problem.setSubmited(problem.getSubmited() + 1);
		// 判断是否为比赛提交
		if (submit.getContestId() > 0) {
			ContestUser contestUser = contestService.findContestUser(s.getUser().getUid(), s.getContestId());
			if ("Accepted".equals(submit.getResult())) {
				contestUser.setSolved(contestUser.getSolved() + 1);
			}
			contestUser.setUseTotalTime(contestUser.getUseTotalTime() + submit.getUseTime());
			contestUser.setUseTotalMemary(contestUser.getUseTotalMemary() + submit.getUseMemary());
			contestService.updateContestUser(contestUser.getCuId(), contestUser);
		}
		Problem newProblem = new Problem();
		newProblem.setPid(problem.getPid());
		if ("Accepted".equals(submit.getResult())) {
			newProblem.setAccepted(problem.getAccepted() + 1);
		}
		newProblem.setSubmited(problem.getSubmited() + 1);
		problemService.updateProblem(newProblem);
		return submitMapper.updateSubmit(submit);
	}

	@Override
	public Integer findCount(Submit submit) {
		return submitMapper.findCount(submit);
	}

	@Override
	public List<Submit> findContestSubmits(Integer contestId, PageBean pageBean) {
		if (contestId == null && pageBean != null) {
			return null;
		}
		return submitMapper.findContestSubmits(contestId, pageBean);
	}

	@Override
	public Integer findContestSubmitCount(Integer contestId) {
		if (contestId == null) {
			return null;
		}
		return submitMapper.findContestSubmitCount(contestId);
	}

}
