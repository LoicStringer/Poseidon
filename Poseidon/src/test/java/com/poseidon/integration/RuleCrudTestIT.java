package com.poseidon.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poseidon.dto.RuleDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = { "USER", "ADMIN" })
@Transactional
public class RuleCrudTestIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void getRulesListTest() throws Exception {

		mockMvc.perform(get("/poseidon/api/rules")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].name").value("TallionRule"));
	}

	@Test
	void getOneRuleTest() throws Exception {

		mockMvc.perform(get("/poseidon/api/rules/2")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("StrongestRule"));
	}

	@Test
	void addRuleTest() throws Exception {

		RuleDto ruleToAdd = new RuleDto();
		ruleToAdd.setName("MyRule");
		ruleToAdd.setDescription("A new rule");

		mockMvc.perform(post("/poseidon/api/rules").content(objectMapper.writeValueAsString(ruleToAdd))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("MyRule"));
	}

	@Test
	void updateRuleTest() throws Exception {

		RuleDto ruleToUpdate = new RuleDto();
		ruleToUpdate.setRuleId(2);
		ruleToUpdate.setName("MyRule");
		ruleToUpdate.setDescription("A new rule");

		mockMvc.perform(put("/poseidon/api/rules/2").content(objectMapper.writeValueAsString(ruleToUpdate))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("MyRule"));
	}

	@Test
	void deleteRuleTest() throws Exception {

		RuleDto ruleToDelete = new RuleDto();
		ruleToDelete.setRuleId(1);
		ruleToDelete.setName("TallionRule");

		mockMvc.perform(delete("/poseidon/api/rules/1").content(objectMapper.writeValueAsString(ruleToDelete))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("TallionRule"));
	}

	@Test
	void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeAddingResourceTest()
			throws JsonProcessingException, Exception {

		RuleDto ruleToAdd = new RuleDto();
		ruleToAdd.setRuleId(1);
		ruleToAdd.setName("MyRule");
		ruleToAdd.setDescription("A new rule");

		mockMvc.perform(post("/poseidon/api/rules").content(objectMapper.writeValueAsString(ruleToAdd))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NotAllowedIdSettingException))
				.andExpect(result -> assertEquals("Not allowed to set an id to resources.",
						result.getResolvedException().getMessage()));
	}

	@Test
	void isExpectedExceptionThrownWhenResourceIsNotFoundTest() throws Exception {

		mockMvc.perform(get("/poseidon/api/rules/10")).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("Rule with id 10 not found.",
						result.getResolvedException().getMessage()));
	}

	@Test
	void isExpectedExceptionThrownWhenUriIdIsDifferentFromResourceIdTest() throws JsonProcessingException, Exception {

		RuleDto ruleToUpdate = new RuleDto();
		ruleToUpdate.setRuleId(2);
		ruleToUpdate.setName("MyRule");
		ruleToUpdate.setDescription("A new rule");

		mockMvc.perform(put("/poseidon/api/rules/1").content(objectMapper.writeValueAsString(ruleToUpdate))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals(
						"The requested rule's id 1 is different from the currently handled rule's id.",
						result.getResolvedException().getMessage()));
	}

	@Test
	void isExpectedExceptionThrownWhenTryingToHandleUnexistingResourceTest() throws JsonProcessingException, Exception {

		RuleDto ruleToUpdate = new RuleDto();
		ruleToUpdate.setRuleId(10);
		ruleToUpdate.setName("MyRule");
		ruleToUpdate.setDescription("A new rule");

		mockMvc.perform(put("/poseidon/api/rules/10").content(objectMapper.writeValueAsString(ruleToUpdate))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("The rule with 10 id number is not registered.",
						result.getResolvedException().getMessage()));
	}

}
