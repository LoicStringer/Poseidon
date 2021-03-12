package com.poseidon.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.poseidon.dto.RuleDto;
import com.poseidon.entity.Rule;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.RuleMapper;
import com.poseidon.utilities.ResourceIdChecker;

@Repository
public class RuleDao extends GenericDao< Rule, Integer> implements IGenericDao<RuleDto, Integer>{
	
	@Autowired
	private ResourceIdChecker<Rule,Integer> resourceIdChecker;
	
	@Autowired
	private RuleMapper ruleMapper;
	
	@Value("Rule")
	private String resourceName;

	@Override
	public List<RuleDto> getAll() {
		List<Rule> ruleEntitiesList = getAllEntity();
		List<RuleDto> ruleDtosList = ruleEntitiesList.stream()
				.map(r->ruleMapper.ruleToRuleDto(r))
				.collect(Collectors.toList());
		return ruleDtosList;
	}

	@Override
	public RuleDto create(RuleDto entityToCreateAsDto) throws DuplicatedResourceException {
		resourceIdChecker.checkIfResourceExistsBeforeCreate(entityToCreateAsDto.getRuleId());
		createEntity(ruleMapper.ruleDtoToRule(entityToCreateAsDto));
		return entityToCreateAsDto;
	}

	@Override
	public RuleDto read(Integer entityId) throws ResourceNotFoundException {
		resourceIdChecker.checkIfResourceExistsBeforeRead(resourceName, entityId);
		return ruleMapper.ruleToRuleDto(readEntity(entityId));
	}

	@Override
	public RuleDto update(Integer entityId, RuleDto entityToUpdateAsDto) throws ResourceNotFoundException {
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(resourceName, entityId, entityToUpdateAsDto.getRuleId());
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(resourceName, entityId,  entityToUpdateAsDto.getRuleId());
		updateEntity(entityId, ruleMapper.ruleDtoToRule(entityToUpdateAsDto));
		return entityToUpdateAsDto;
	}

	@Override
	public RuleDto delete(Integer entityId, RuleDto entityToDeleteAsDto) throws ResourceNotFoundException {
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(resourceName, entityId, entityToDeleteAsDto.getRuleId());
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(resourceName, entityId,  entityToDeleteAsDto.getRuleId());
		deleteEntity(entityId, ruleMapper.ruleDtoToRule(entityToDeleteAsDto));
		return entityToDeleteAsDto;
	}

	
	
}
