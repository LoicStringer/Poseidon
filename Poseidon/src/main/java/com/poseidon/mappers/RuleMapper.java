package com.poseidon.mappers;

import org.mapstruct.Mapper;

import com.poseidon.entity.Rule;
import com.poseidon.dto.RuleDto;

@Mapper(componentModel = "spring")
public interface RuleMapper {

	RuleDto ruleToRuleDto (Rule rule);
	Rule ruleDtoToRule (RuleDto ruleDto);
}
