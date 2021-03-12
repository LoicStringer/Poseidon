package com.poseidon.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dao.RuleDao;
import com.poseidon.dto.RuleDto;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;

@Service
@Transactional(rollbackOn = Exception.class)
public class RuleService implements IGenericService<RuleDto,Integer>{

	@Autowired
	private RuleDao ruleDao;
	
	@Override
	public List<RuleDto> getDtoList() {
		return ruleDao.getAll();
	}

	@Override
	public RuleDto create(RuleDto dtoToCreate) throws DuplicatedResourceException {
		return ruleDao.create(dtoToCreate);
	}

	@Override
	public RuleDto read(Integer dtoId) throws ResourceNotFoundException {
		return ruleDao.read(dtoId);
	}

	@Override
	public RuleDto update(Integer dtoId, RuleDto dtoToUpdate) throws ResourceNotFoundException {
		return ruleDao.update(dtoId, dtoToUpdate);
	}

	@Override
	public RuleDto delete(Integer dtoId, RuleDto dtoToDelete) throws ResourceNotFoundException {
		return ruleDao.delete(dtoId, dtoToDelete);
	}

	/*
	@Autowired
	private RuleMapper ruleMapper ;
	
	@Autowired
	private RuleRepository ruleRepository;
	
	@Autowired
	private ResourceIdChecker<Rule, Integer> resourceIdChecker ;
	
	
	public List<RuleDto> getAllRules(){
		List<Rule> rules = ruleRepository.findAll();
		List<RuleDto> rulesDto = rules.stream()
				.map(r-> ruleMapper.ruleToRuleDto(r))
				.collect(Collectors.toList());
		return rulesDto ;
	}
	
	public RuleDto create(RuleDto ruleToCreate) throws DuplicatedResourceException {
		resourceIdChecker.checkIfResourceExistsBeforeCreate(ruleToCreate.getRuleId());
		ruleRepository.save(ruleMapper.ruleDtoToRule(ruleToCreate));
		return ruleToCreate ;
	}
	
	public RuleDto read(Integer id) throws ResourceNotFoundException {
		resourceIdChecker.checkIfResourceExistsBeforeRead(Rule.class, id);
		return ruleMapper.ruleToRuleDto(ruleRepository.findById(id).get());
	}
	
	public RuleDto update (Integer resourceId,RuleDto ruleToUpdate) throws ResourceNotFoundException {
		Integer ruleToUpdateId = ruleToUpdate.getRuleId();
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(Rule.class, resourceId, ruleToUpdateId);
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(Rule.class, resourceId, ruleToUpdateId);
		ruleRepository.save(ruleMapper.ruleDtoToRule(ruleToUpdate));
		return ruleToUpdate ;
	}
	
	public RuleDto  delete(Integer resourceId, RuleDto ruleToDelete) throws ResourceNotFoundException {
		Integer ruleToDeleteId = ruleToDelete.getRuleId();
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(Rule.class, resourceId, ruleToDeleteId);
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(Rule.class, resourceId, ruleToDeleteId);
		ruleRepository.delete(ruleMapper.ruleDtoToRule(ruleToDelete));
		return ruleToDelete;
	}
	*/
	
}
