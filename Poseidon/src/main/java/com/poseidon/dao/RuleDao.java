package com.poseidon.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.websocket.ReadBufferOverflowException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poseidon.dto.RuleDto;
import com.poseidon.entity.Rule;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.RuleMapper;
import com.poseidon.repository.RuleRepository;

@Repository
public class RuleDao implements IGenericCrudDao<RuleDto,Integer>{

	@Autowired
	private RuleMapper ruleMapper;
	
	@Autowired
	private RuleRepository ruleRepository;
	
	@Override
	public List<RuleDto> getAllList() {
		List<Rule> ruleEntitiesList = ruleRepository.findAll();
		List<RuleDto> ruleDtosList = ruleEntitiesList.stream()
				.map(r->ruleMapper.ruleToRuleDto(r))
				.collect(Collectors.toList());
		return ruleDtosList;
	}

	@Override
	public RuleDto create(RuleDto ruleToCreate) throws DuplicatedResourceException {
		preventResourceIdBreach(ruleToCreate.getRuleId());
		ruleRepository.save(ruleMapper.ruleDtoToRule(ruleToCreate));
		return ruleToCreate;
	}

	@Override
	public RuleDto read(Integer ruleId) throws ResourceNotFoundException {
		Rule ruleToRead = ruleRepository.findById(ruleId).orElseThrow(()->new ResourceNotFoundException("Rule with id "+ruleId+" not found."));
		return ruleMapper.ruleToRuleDto(ruleToRead);
	}

	@Override
	public RuleDto update(Integer ruleId, RuleDto updatedRule) throws ResourceNotFoundException {
		checkResourceExistence(ruleId);
		checkResourceIdCoherence(ruleId, updatedRule.getRuleId());
		ruleRepository.save(ruleMapper.ruleDtoToRule(updatedRule));
		return updatedRule;
	}

	@Override
	public RuleDto delete(Integer ruleId, RuleDto ruleToDelete) throws ResourceNotFoundException {
		checkResourceExistence(ruleId);
		checkResourceIdCoherence(ruleId, ruleToDelete.getRuleId());
		ruleRepository.delete(ruleMapper.ruleDtoToRule(ruleToDelete));
		return ruleToDelete;
	}

	private void preventResourceIdBreach(Integer ruleId) throws DuplicatedResourceException {
		if(ruleRepository.existsById(ruleId))
			throw new DuplicatedResourceException("Not allowed to set an id to resources.");
	}

	private void checkResourceExistence(Integer ruleId) throws ResourceNotFoundException {
		if(!ruleRepository.existsById(ruleId))
			throw new ResourceNotFoundException("The rule with "+ruleId+ " id number is not registered.");
	}
	
	private void checkResourceIdCoherence(Integer targetRuleId, Integer treatedRuleId) throws ResourceNotFoundException {
		if(!targetRuleId.equals(treatedRuleId))
			throw new ResourceNotFoundException("The requested rule's id "+targetRuleId+ " is different from the currently handled rule's id.");
	}
	
}
