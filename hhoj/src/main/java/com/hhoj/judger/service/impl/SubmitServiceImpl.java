package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hhoj.judger.entity.ContestUser;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.mapper.SubmitMapper;
import com.hhoj.judger.service.ContestService;
import com.hhoj.judger.service.ProblemService;
import com.hhoj.judger.service.SubmitService;
import com.hhoj.judger.service.UserService;

@Service("submitService")
public class SubmitServiceImpl implements SubmitService {

	@Autowired
	private SubmitMapper submitMapper;

	@Autowired
	private ProblemService problemService;

	@Autowired
	private ContestService contestService;
	
	@Autowired
	private UserService userService;

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
		Submit conditionSubmit=new Submit();
		conditionSubmit.setProblem(s.getProblem());
		conditionSubmit.setUser(s.getUser());
		conditionSubmit.setResult("Accepted");
		int acCount=this.findCount(conditionSubmit);
		
		Problem problem = s.getProblem();
		problem.setSubmited(problem.getSubmited() + 1);
		// 判断是否为比赛提交
		if (submit.getContestId() > 0) {
			if ("Accepted".equals(submit.getResult())&&acCount<1) {
				ContestUser contestUser = contestService.findContestUser(s.getUser().getUid(), s.getContestId());
				contestUser.setSolved(contestUser.getSolved() + 1);
				contestUser.setUseTotalTime(contestUser.getUseTotalTime() + submit.getUseTime());
				contestUser.setUseTotalMemary(contestUser.getUseTotalMemary() + submit.getUseMemary());
				contestService.updateContestUser(contestUser.getCuId(), contestUser);
			}
		}else {
			User user=s.getUser();
			User updateUser=new User();
			updateUser.setUid(user.getUid());
			if ("Accepted".equals(submit.getResult())) {
				if(acCount<1) {
					updateUser.setSolved(user.getSolved()+1);
				}
				updateUser.setAccepted(user.getAccepted()+1);
			}
			updateUser.setSubmited(user.getSubmited()+1);
			userService.updateUser(updateUser);
		}
		Problem newProblem = new Problem();
		newProblem.setPid(problem.getPid());
		if ("Accepted".equals(submit.getResult())) {
			newProblem.setAccepted(problem.getAccepted() + 1);
		}
		newProblem.setSubmited(problem.getSubmited());
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
