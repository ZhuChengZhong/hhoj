package com.hhoj.judger.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hhoj.judger.entity.Contest;
import com.hhoj.judger.entity.ContestProblem;
import com.hhoj.judger.entity.PageBean;
import com.hhoj.judger.entity.Problem;
import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.entity.TestPoint;
import com.hhoj.judger.entity.User;
import com.hhoj.judger.mapper.ContestMapper;
import com.hhoj.judger.mapper.ContestProblemMapper;
import com.hhoj.judger.mapper.ContestUserMapper;
import com.hhoj.judger.service.ContestService;
import com.hhoj.judger.service.ProblemService;
import com.hhoj.judger.service.TestPointService;

@Service("contestServiceImpl")
public class ContestServiceImpl implements ContestService{
	
	@Autowired
	private ContestMapper contestMapper;
	
	@Autowired
	private ContestProblemMapper contestProblemMapper;
	
	@Autowired
	private ContestUserMapper contestUserMapper;
	
	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private TestPointService testPointService;
	
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
	@Transactional
	public Integer addContestProblem(ContestProblem contestProblem) {
		if(contestProblem==null) {
			return -1;
		}
		Problem problem=problemService.findProblemById(contestProblem.getProblem().getPid());
		if(problem==null) {
			return -1;
		}
		
		/**
		 * 复制原来的试题用作竞赛试题
		 */
		int oldPid=problem.getPid();
		Problem newProblem=new Problem();
		newProblem.setAccepted(0);
		newProblem.setCreateTime(new Date());
		newProblem.setDesc(problem.getDesc());
		newProblem.setHint(problem.getHint());
		newProblem.setInputExample(problem.getInputExample());
		newProblem.setOutputExample(problem.getOutputExample());
		newProblem.setPublish(-1);
		newProblem.setSource(problem.getSource());
		newProblem.setTimeLimit(problem.getTimeLimit());
		newProblem.setMemaryLimit(problem.getMemaryLimit());
		newProblem.setTitle(problem.getTitle());
		newProblem.setType(problem.getType());
		newProblem.setSubmited(0);
		/**
		 * mybatis会自动回填主键
		 */
		problemService.addProblem(newProblem);
		int newPid=newProblem.getPid();
		List<TestPoint> list=testPointService.findTestPoints(oldPid);
		list.forEach((testPoint)->{
			testPoint.setPid(newPid);
			testPoint.setPointId(null);
			testPointService.addTestPoint(testPoint);
		});
		contestProblem.setProblem(newProblem);
		return contestProblemMapper.addContestProblem(contestProblem);
	}

	@Override
	@Transactional
	public Integer removeContestProblem(Integer cpId) {
		if(cpId==null) {
			return -1;
		}
		ContestProblem contestProblem=contestProblemMapper.findContestProblemById(cpId);
		int pid=contestProblem.getProblem().getPid();
		problemService.removeProblem(pid);
		return 	contestProblemMapper.removeContestProblem(cpId);
	}

	
	@Override
	public ContestProblem findContestProblemByPidAndContestId(Integer pid, Integer contestId) {
		ContestProblem contestProblem=null;
		if(pid!=null&&contestId!=null) {
			contestProblem=contestProblemMapper.findContestProblemByPidAndContestId(pid, contestId);
		}
		return contestProblem;
	}

	@Override
	public List<User> findContestsByUserId(Integer uid) {
		List<User> list=null;
		if(uid!=null) {
			list=contestMapper.findContestsByUserId(uid);
		}
		return list;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Integer joinContest(Integer uid, Integer contestId) {
		if(uid==null||contestId==null) {
			return -1;
		}
		if(this.existUser(uid, contestId)) {
			return -1;
		}
		Contest contest=contestMapper.findContestById(contestId);
		if(contest==null) {
			return -1;
		}
		Contest newContest=new Contest();
		newContest.setContestId(contest.getContestId());
		newContest.setJoinNumber(contest.getJoinNumber()+1);
		contestMapper.updateContest(newContest);
		return contestUserMapper.joinContest(uid, contestId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Integer exitContest(Integer uid, Integer contestId) {
		if(uid==null||contestId==null) {
			return -1;
		}
		
		if(!existUser(uid, contestId)) {
			return -1;
		}
		Contest newContest=new Contest();
		Contest contest=contestMapper.findContestById(contestId);
		if(contest==null) {
			return -1;
		}
		newContest.setJoinNumber(contest.getJoinNumber()-1);
		contestMapper.updateContest(newContest);
		return contestUserMapper.exitContest(uid, contestId);
	}

	@Override
	public boolean existUser(Integer uid, Integer contestId) {
		if(uid==null||contestId==null) {
			return false;
		}
		return contestUserMapper.existUser(uid, contestId)>0?true:false;
	}

}
