package com.poseidon.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.poseidon.dto.RuleDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.RuleService;

@DisplayName("RuleController CRUD operations tests")
@ExtendWith(MockitoExtension.class)
public class RuleControllerTest {

	@Mock
	private RuleService ruleService;

	@InjectMocks
	private RuleController ruleController;

	private static RuleDto ruleDto;
	private static List<RuleDto> ruleDtosList;

	@BeforeAll
	static void setUp() {
		ruleDto = new RuleDto();
		ruleDto.setRuleId(1);
		ruleDto.setName("Tallion");
		ruleDtosList = new ArrayList<RuleDto>();
		ruleDtosList.add(ruleDto);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getRulesListTest() {
			when(ruleService.getDtoList()).thenReturn(ruleDtosList);
			assertEquals(ResponseEntity.ok(ruleDtosList), ruleController.getRulesList());
		}

		@Test
		void getOneRuleTest() throws ResourceNotFoundException {
			when(ruleService.read(any(Integer.class))).thenReturn(ruleDto);
			assertEquals(ResponseEntity.ok(ruleDto), ruleController.getOneRule(1));
		}

		@Test
		void addRuleTest() throws NotAllowedIdSettingException {
			when(ruleService.create(any(RuleDto.class))).thenReturn(ruleDto);
			assertEquals(ResponseEntity.ok(ruleDto), ruleController.addRule(ruleDto));
		}

		@Test
		void updateRuleTest() throws ResourceNotFoundException {
			when(ruleService.update(any(Integer.class), any(RuleDto.class))).thenReturn(ruleDto);
			assertEquals(ResponseEntity.ok(ruleDto), ruleController.updateRule(1, ruleDto));
		}

		@Test
		void deleteruleTest() throws ResourceNotFoundException {
			when(ruleService.delete(any(Integer.class), any(RuleDto.class))).thenReturn(ruleDto);
			assertEquals(ResponseEntity.ok(ruleDto), ruleController.deleteRule(1, ruleDto));
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeAddingResourceTest() throws NotAllowedIdSettingException {
			when(ruleService.create(any(RuleDto.class))).thenThrow(NotAllowedIdSettingException.class);
			assertThrows(NotAllowedIdSettingException.class,()->ruleController.addRule(ruleDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToGetUnexistingResourceTest() throws ResourceNotFoundException {
			when(ruleService.read(any(Integer.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->ruleController.getOneRule(2));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToUpdategResourceNotFoundTest() throws ResourceNotFoundException {
			when(ruleService.update(any(Integer.class),any(RuleDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->ruleController.updateRule(2,ruleDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToDeleteResourceNotFoundTest() throws ResourceNotFoundException {
			when(ruleService.delete(any(Integer.class),any(RuleDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->ruleController.deleteRule(2,ruleDto));
		}
		
	}
	
}
