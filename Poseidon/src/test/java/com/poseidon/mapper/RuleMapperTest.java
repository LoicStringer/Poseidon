package com.poseidon.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.poseidon.dto.RuleDto;
import com.poseidon.entity.Rule;

public class RuleMapperTest {
	
	private RuleMapper ruleMapper = Mappers.getMapper(RuleMapper.class);
	
	@Test
	void ruleDtoToRuleTest() {
		RuleDto ruleDto = new RuleDto();
		ruleDto.setName("Tallion");
		Rule rule = ruleMapper.ruleDtoToRule(ruleDto);
		assertEquals("Tallion",rule.getName());
	}

	@Test
	void ruleToRuleDtoTest() {
		Rule rule = new Rule();
		rule.setName("Tallion");
		RuleDto ruleDto = ruleMapper.ruleToRuleDto(rule);
		assertEquals("Tallion",ruleDto.getName());
	}
	
	
}

