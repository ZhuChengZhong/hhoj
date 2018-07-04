package com.hhoj.judger.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.hhoj.judger.cache.RedisCache;
import com.hhoj.judger.entity.ContestUser;
import com.hhoj.judger.entity.JudgeResult;
import com.hhoj.judger.entity.Message;
import com.hhoj.judger.entity.Message.Point;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.entity.TestPoint;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.mapper.SubmitMapper;
import com.hhoj.judger.service.ContestService;
import com.hhoj.judger.service.ProblemService;
import com.hhoj.judger.service.SubmitService;
import com.hhoj.judger.service.TestPointService;
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
	
	@Autowired
	private TestPointService testPointService;
	
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
		boolean isSolved=problemService.checkIsAccepted(submit.getUser().getUid(),submit.getProblem().getPid());
		// 处理比赛提交
		if (submit.getContestId() > 0) {
			if ("Accepted".equals(submit.getResult())&&!isSolved) {
				ContestUser contestUser = contestService.findContestUser(submit.getUser().getUid(),submit.getContestId());
				contestUser.setSolved(contestUser.getSolved() + 1);
				contestUser.setUseTotalTime(contestUser.getUseTotalTime() + submit.getUseTime());
				contestUser.setUseTotalMemary(contestUser.getUseTotalMemary() + submit.getUseMemary());
				contestService.updateContestUser(contestUser.getCuId(), contestUser);
			}
		}else {
			User user=submit.getUser();
			User updateUser=new User();
			updateUser.setUid(user.getUid());
			if ("Accepted".equals(submit.getResult())) {
				if(!isSolved) {
					updateUser.setSolved(user.getSolved()+1);
				}
				updateUser.setAccepted(user.getAccepted()+1);
			}
			updateUser.setSubmited(user.getSubmited()+1);
			userService.updateUser(updateUser);
		}
		Problem newProblem = new Problem();
		newProblem.setPid(submit.getProblem().getPid());
		if ("Accepted".equals(submit.getResult())) {
			newProblem.setAccepted(submit.getProblem().getAccepted() + 1);
		}
		newProblem.setSubmited(submit.getProblem().getSubmited());
		problemService.updateProblem(newProblem);
		return submitMapper.updateSubmit(submit);
	}
    
	
	
	@Override
	public Integer findCount(Submit submit) {
		int c=submitMapper.findCount(submit);
		return c;
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

	@Override
	public String transforToMessage(Submit submit) {
		Message message=new Message();
		message.setSubmitId(submit.getSid());
		message.setCode(submit.getCode());
		message.setLanguage(submit.getLanguage().getLanguageName());
		message.setMemaryLimit(submit.getProblem().getMemaryLimit());
		message.setTimeLimit(submit.getProblem().getTimeLimit());
		List<TestPoint>list=testPointService.findTestPoints(submit.getProblem().getPid());
		List<Point>points=new ArrayList<>();
		for(TestPoint tp:list) {
			Point p=new Point(tp.getInput(), tp.getOutput());
			points.add(p);
		}
		message.setPoints(points);
		String res=JSON.toJSONString(message);
		return res;
	}

	@Override
	public Integer updateSubmit(JudgeResult jr) {
		Submit submit=findSubmitById(jr.getSubmitId());
		submit.setSid(jr.getSubmitId());
		submit.setJudged(1);
		submit.setResult(jr.getResult());
		submit.setUseMemary(jr.getUseMemary());
		submit.setUseTime(jr.getUseTime());
		return updateSubmit(submit);
	}

}
