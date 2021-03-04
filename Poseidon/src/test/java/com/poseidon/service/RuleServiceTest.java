package com.poseidon.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dto.RuleDto;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.repository.RuleRepository;

@ExtendWith(MockitoExtension.class)
class RuleServiceTest {

	@Mock
	private RuleRepository ruleRepository;

	@InjectMocks
	private RuleService ruleService;

	private static RuleDto testedRuleDto;

	@BeforeAll
	static void setUp() {
		testedRuleDto = new RuleDto();
		testedRuleDto.setRuleId(1);
	}

	@Test
	void isExpectedExceptionThrownWhenTryingToFindUnexistingRuleTest() {
		when(ruleRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> ruleService.read(1));
	}

	@Test
	void isExpectedExceptionThrownWhenDeletingUnexistingRuleTest() {
		when(ruleRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> ruleService.delete(testedRuleDto));
	}

	@Test
	void isExpectedExceptionThrownWhenUpdatingUnexistingRuleTest() {
		when(ruleRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> ruleService.update(testedRuleDto));
	}


}
