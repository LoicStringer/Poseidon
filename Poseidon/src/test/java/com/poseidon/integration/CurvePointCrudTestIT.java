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
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poseidon.dto.CurvePointDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities= {"USER","ADMIN"})
@Transactional
public class CurvePointCrudTestIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void getCurvePointsListTest() throws Exception {
		
		mockMvc.perform(get("/poseidon/api/curvePoints"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].curveId").value(1));
	}
	
	@Test
	void getOneCurvePointTest() throws Exception {
		
		mockMvc.perform(get("/poseidon/api/curvePoints/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.curveId").value(1));
	}
	
	@Test
	void addCurvePointTest() throws Exception {
		
		CurvePointDto curvePointToAdd = new CurvePointDto();
		curvePointToAdd.setCurveId(1);
		curvePointToAdd.setValue(10.00);
		
		mockMvc.perform(post("/poseidon/api/curvePoints")
				.content(objectMapper.writeValueAsString(curvePointToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.curveId").value(1));
	}
	
	@Test
	void updateCurvePointTest() throws Exception {
		
		CurvePointDto curvePointToUpdate = new CurvePointDto();
		curvePointToUpdate.setCurvePointId(2);
		curvePointToUpdate.setCurveId(3);
		curvePointToUpdate.setValue(50.00);
		
		mockMvc.perform(put("/poseidon/api/curvePoints/2")
				.content(objectMapper.writeValueAsString(curvePointToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.value").value(50));
	}
	
	@Test
	void deleteCurvePointTest() throws Exception {
		
		CurvePointDto curvePointToDelete = new CurvePointDto();
		curvePointToDelete.setCurvePointId(1);
		curvePointToDelete.setCurveId(1);
		curvePointToDelete.setValue(10.00);
		
		mockMvc.perform(delete("/poseidon/api/curvePoints/1")
				.content(objectMapper.writeValueAsString(curvePointToDelete))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.value").value(10.00));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeAddingResourceTest() throws JsonProcessingException, Exception {
		
		CurvePointDto curvePointToAdd = new CurvePointDto();
		curvePointToAdd.setCurvePointId(1);
		curvePointToAdd.setCurveId(1);
		curvePointToAdd.setValue(10.00);
		
		mockMvc.perform(post("/poseidon/api/curvePoints")
				.content(objectMapper.writeValueAsString(curvePointToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NotAllowedIdSettingException))
				.andExpect(result -> assertEquals("Not allowed to set an id to resources.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenResourceIsNotFoundTest() throws Exception{
		
		mockMvc.perform(get("/poseidon/api/curvePoints/10"))
		.andExpect(status().is4xxClientError())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
		.andExpect(result -> assertEquals("Curve point with id 10 not found.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenUriIdIsDifferentFromResourceIdTest() throws JsonProcessingException, Exception {
		
		CurvePointDto curvePointToUpdate = new CurvePointDto();
		curvePointToUpdate.setCurvePointId(2);
		curvePointToUpdate.setCurveId(2);
		curvePointToUpdate.setValue(20.00);
		
		mockMvc.perform(put("/poseidon/api/curvePoints/1")
				.content(objectMapper.writeValueAsString(curvePointToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("The requested curve point's id 1 is different from the currently handled curve point's id.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToHandleUnexistingResourceTest() throws JsonProcessingException, Exception{
		
		CurvePointDto curvePointToUpdate = new CurvePointDto();
		curvePointToUpdate.setCurvePointId(10);
		curvePointToUpdate.setCurveId(10);
		curvePointToUpdate.setValue(100.00);
		
		mockMvc.perform(put("/poseidon/api/curvePoints/10")
				.content(objectMapper.writeValueAsString(curvePointToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("The curve point with 10 id number is not registered.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToCreateInvalidResourceTest() throws JsonProcessingException, Exception {
		
		CurvePointDto curvePointToAdd = new CurvePointDto();
		curvePointToAdd.setCurveId(-10);
		
		mockMvc.perform(post("/poseidon/api/curvePoints")
				.content(objectMapper.writeValueAsString(curvePointToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
	}
	
}
