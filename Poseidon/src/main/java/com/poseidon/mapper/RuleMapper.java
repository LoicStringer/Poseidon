package com.poseidon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.poseidon.entity.Rule;
import com.poseidon.dto.RuleDto;

@Mapper(componentModel = "spring")
public interface RuleMapper {

	RuleMapper ruleMapper = Mappers.getMapper(RuleMapper.class);
	
	RuleDto ruleToRuleDto (Rule rule);
	Rule ruleDtoToRule (RuleDto ruleDto);
}
