package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.service.ProblemTypeService;
@Service("problemTypeService")
public class ProblemTypeServiceImpl implements ProblemTypeService{
	
	@Autowired
	private ProblemTypeService problemTypeService;
	
	@Override
	public ProblemType findProblemTypeById(Integer typeId) {
		return problemTypeService.findProblemTypeById(typeId);
	}

	@Override
	public Integer findCount() {
		return problemTypeService.findCount();
	}

	@Override
	public List<ProblemType> findProblemTypes() {
		return problemTypeService.findProblemTypes();
	}

	@Override
	public Integer updateProblemType(ProblemType problemType) {
		return problemTypeService.updateProblemType(problemType);
	}

	@Override
	public Integer removeProblemType(Integer typeId) {
		return problemTypeService.removeProblemType(typeId);
	}

	@Override
	public Integer addProblemType(ProblemType problemType) {
		return problemTypeService.addProblemType(problemType);
	}

}
