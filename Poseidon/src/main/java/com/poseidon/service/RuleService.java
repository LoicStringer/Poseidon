package com.poseidon.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dto.RuleDto;
import com.poseidon.entity.Rule;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.RuleMapper;
import com.poseidon.repository.RuleRepository;

@Service
@Transactional(rollbackOn = Exception.class)
public class RuleService {

	@Autowired
	private RuleMapper ruleMapper ;
	
	@Autowired
	private RuleRepository ruleRepository;
	
	public List<RuleDto> getAllRules(){
		List<Rule> rules = ruleRepository.findAll();
		List<RuleDto> rulesDto = rules.stream()
				.map(r-> ruleMapper.ruleToRuleDto(r))
				.collect(Collectors.toList());
		return rulesDto ;
	}
	
	public RuleDto create(RuleDto ruleToCreate) {
		ruleRepository.save(ruleMapper.ruleDtoToRule(ruleToCreate));
		return ruleToCreate ;
	}
	
	public RuleDto read(Integer id) throws ResourceNotFoundException {
		return ruleMapper.ruleToRuleDto(ruleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Rule not found")));
	}
	
	public RuleDto update (RuleDto ruleToUpdate) throws ResourceNotFoundException {
		if(!ruleRepository.existsById(ruleToUpdate.getRuleId()))
			throw new ResourceNotFoundException("Rule not found");
		ruleRepository.save(ruleMapper.ruleDtoToRule(ruleToUpdate));
		return ruleToUpdate ;
	}
	
	public RuleDto  delete(RuleDto ruleToDelete) throws ResourceNotFoundException {
		if(!ruleRepository.existsById(ruleToDelete.getRuleId()))
			throw new ResourceNotFoundException("Rule not found");
		ruleRepository.delete(ruleMapper.ruleDtoToRule(ruleToDelete));
		return ruleToDelete;
	}
	
}
