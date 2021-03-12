package com.poseidon.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dao.RuleDao;
import com.poseidon.dto.RuleDto;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;

@DisplayName("RuleService CRUD operations tests")
@ExtendWith(MockitoExtension.class)
class RuleServiceTest {

	@Mock
	private RuleDao ruleDao;

	@InjectMocks
	private RuleService ruleService;

	private static RuleDto testedRuleDto;

	@BeforeAll
	static void setUp() {
		testedRuleDto = new RuleDto();
		testedRuleDto.setRuleId(1);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void createTest() throws DuplicatedResourceException {
			when(ruleDao.create(any(RuleDto.class))).thenReturn(testedRuleDto);
			assertEquals(testedRuleDto, ruleService.create(testedRuleDto));
		}

		@Test
		void readTest() throws ResourceNotFoundException {
			when(ruleDao.read(any(Integer.class))).thenReturn(testedRuleDto);
			assertEquals(testedRuleDto, ruleService.read(1));
		}

		@Test
		void updateTest() throws ResourceNotFoundException {
			when(ruleDao.update(any(Integer.class),any(RuleDto.class))).thenReturn(testedRuleDto);
			assertEquals(testedRuleDto, ruleService.update(1,testedRuleDto));
		}

		@Test
		void deleteTest() throws ResourceNotFoundException {
			when(ruleDao.delete(any(Integer.class),any(RuleDto.class))).thenReturn(testedRuleDto);
			assertEquals(testedRuleDto, ruleService.delete(1,testedRuleDto));
		}

	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenCreatingAlreadyExistingRuleTest() throws DuplicatedResourceException {
			when(ruleDao.create(testedRuleDto)).thenThrow(DuplicatedResourceException.class);
			assertThrows(DuplicatedResourceException.class, () -> ruleService.create(testedRuleDto));
		}

		@Test
		void isExpectedExceptionThrownWhenTryingToFindUnexistingRuleTest() throws ResourceNotFoundException {
			when(ruleDao.read(any(Integer.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> ruleService.read(1));
		}

		@Test
		void isExpectedExceptionThrownWhenDeletingUnexistingRuleTest() throws ResourceNotFoundException {
			when(ruleDao.delete(any(Integer.class), any(RuleDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> ruleService.delete(1, testedRuleDto));
		}

		@Test
		void isExpectedExceptionThrownWhenDeletingRuleWithIncoherentIdTest() throws ResourceNotFoundException {
			when(ruleDao.delete(any(Integer.class), any(RuleDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> ruleService.delete(2, testedRuleDto));
		}

		@Test
		void isExpectedExceptionThrownWhenUpdatingUnexistingRuleTest() throws ResourceNotFoundException {
			when(ruleDao.update(any(Integer.class), any(RuleDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> ruleService.update(1, testedRuleDto));
		}

		@Test
		void isExpectedExceptionThrownWhenUpdatingRuleWithIncoherentIdTest() throws ResourceNotFoundException {
			when(ruleDao.update(any(Integer.class), any(RuleDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> ruleService.update(2, testedRuleDto));
		}
	}
}
