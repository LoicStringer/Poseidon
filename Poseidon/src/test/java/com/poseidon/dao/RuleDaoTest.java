package com.poseidon.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dto.RuleDto;
import com.poseidon.entity.Rule;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.RuleMapper;
import com.poseidon.repository.RuleRepository;

@DisplayName("RuleDao CRUD operations tests")
@ExtendWith(MockitoExtension.class)
public class RuleDaoTest {

	@Mock
	private RuleRepository ruleRepository;

	@Mock
	private RuleMapper ruleMapper;

	@InjectMocks
	private RuleDao ruleDao;

	private static Rule testedRule;
	private static List<Rule> rulesList;
	private static RuleDto testedRuleDto;
	private static List<RuleDto> ruleDtosList;

	@BeforeAll
	static void setUp() {
		testedRule = new Rule();
		testedRule.setRuleId(1);
		rulesList = new ArrayList<Rule>();
		rulesList.add(testedRule);
		testedRuleDto = new RuleDto();
		testedRuleDto.setRuleId(1);
		ruleDtosList = new ArrayList<RuleDto>();
		ruleDtosList.add(testedRuleDto);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getAllTest() {
			when(ruleMapper.ruleToRuleDto(testedRule)).thenReturn(testedRuleDto);
			when(ruleRepository.findAll()).thenReturn(rulesList);
			assertEquals(ruleDtosList, ruleDao.getAllList());
		}

		@Test
		void createTest() throws NotAllowedIdSettingException {
			Rule ruleToCreate = new Rule();
			ruleToCreate.setName("Tallion");
			RuleDto ruleDtoToCreate = new RuleDto(); 
			ruleDtoToCreate.setName("Tallion");
			when(ruleMapper.ruleDtoToRule(ruleDtoToCreate)).thenReturn(ruleToCreate);
			when(ruleRepository.save(any(Rule.class))).thenReturn(ruleToCreate);
			assertEquals(ruleDtoToCreate, ruleDao.create(ruleDtoToCreate));
		}

		@Test
		void readTest() throws ResourceNotFoundException {
			when(ruleMapper.ruleToRuleDto(testedRule)).thenReturn(testedRuleDto);
			when(ruleRepository.findById(any(Integer.class))).thenReturn(Optional.of(testedRule));
			assertEquals(testedRuleDto, ruleDao.read(1));
		}

		@Test
		void updateTest() throws ResourceNotFoundException {
			when(ruleMapper.ruleDtoToRule(testedRuleDto)).thenReturn(testedRule);
			when(ruleRepository.existsById(any(Integer.class))).thenReturn(true);
			when(ruleRepository.save(any(Rule.class))).thenReturn(testedRule);
			assertEquals(testedRuleDto, ruleDao.update(1, testedRuleDto));
		}

		@Test
		void deleteTest() throws ResourceNotFoundException {
			when(ruleRepository.existsById(any(Integer.class))).thenReturn(true);
			assertEquals(testedRuleDto, ruleDao.delete(1, testedRuleDto));
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {
		
		@Test
		void isExpectedExceptionThrownWhenRuleIsNotFound() {
			assertThrows(ResourceNotFoundException.class, ()->ruleDao.read(2));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeCreate() {
			assertThrows(NotAllowedIdSettingException.class,()->ruleDao.create(testedRuleDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenRequestIdIsDifferentFromResourceId() {
			assertThrows(ResourceNotFoundException.class,()->ruleDao.update(2, testedRuleDto));
		}
	}

	
}
