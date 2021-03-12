package com.poseidon.mapper;

import org.mapstruct.Mapper;

import com.poseidon.dto.RuleDto;
import com.poseidon.entity.Rule;

@Mapper(componentModel = "spring")
public interface RuleMapper {

	RuleDto ruleToRuleDto (Rule rule);
	Rule ruleDtoToRule (RuleDto ruleDto);
}
