package com.hhoj.judger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhoj.judger.entity.ProblemType;
import com.hhoj.judger.mapper.ProblemTypeMapper;
import com.hhoj.judger.service.ProblemTypeService;

@Service("problemTypeService")
public class ProblemTypeServiceImpl implements ProblemTypeService{
	
	@Autowired
	private ProblemTypeMapper problemTypeMapper;
	
	@Override
	public ProblemType findProblemTypeById(Integer typeId) {
		return problemTypeMapper.findProblemTypeById(typeId);
	}

	@Override
	public Integer findCount() {
		return problemTypeMapper.findCount();
	}

	@Override
	public List<ProblemType> findProblemTypes() {
		return problemTypeMapper.findProblemTypes();
	}

	@Override
	public Integer updateProblemType(ProblemType problemType) {
		return problemTypeMapper.updateProblemType(problemType);
	}

	@Override
	public Integer removeProblemType(Integer typeId) {
		return problemTypeMapper.removeProblemType(typeId);
	}

	@Override
	public Integer addProblemType(ProblemType problemType) {
		return problemTypeMapper.addProblemType(problemType);
	}

}
