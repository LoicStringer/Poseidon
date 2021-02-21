package com.poseidon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dto.RuleDto;
import com.poseidon.entity.Rule;
import com.poseidon.mapper.RuleMapper;
import com.poseidon.repository.RuleRepository;

@Service
public class RuleService {

	private RuleMapper ruleMapper = Mappers.getMapper(RuleMapper.class);
	
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
	
	public RuleDto read(Integer id) {
		return ruleMapper.ruleToRuleDto(ruleRepository.findById(id).orElse(null));
	}
	
	public RuleDto update (RuleDto ruleToUpdate) {
		ruleRepository.save(ruleMapper.ruleDtoToRule(ruleToUpdate));
		return ruleToUpdate ;
	}
	
	public RuleDto  delete(RuleDto ruleToDelete) {
		ruleRepository.delete(ruleMapper.ruleDtoToRule(ruleToDelete));
		return ruleToDelete;
	}
	
}
