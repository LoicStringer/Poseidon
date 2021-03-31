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
import com.poseidon.dto.TradeDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities= {"USER","ADMIN"})
@Transactional
public class TradeCrudTestIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void getTradesListTest() throws Exception {
		
		mockMvc.perform(get("/poseidon/api/trades"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].account").value("NbaAccount"));
	}
	
	@Test
	void getOneTradeTest() throws Exception {
		
		mockMvc.perform(get("/poseidon/api/trades/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.account").value("MlbAccount"));
	}
	
	@Test
	void addTradeTest() throws Exception {
		
		TradeDto tradeToAdd = new TradeDto();
		tradeToAdd.setAccount("NarcoticsAccount");
		tradeToAdd.setType("Narcotics");
		
		mockMvc.perform(post("/poseidon/api/trades")
				.content(objectMapper.writeValueAsString(tradeToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.account").value("NarcoticsAccount"));
	}
	
	
	@Test
	void updateTradeTest() throws Exception {
		
		TradeDto tradeToUpdate = new TradeDto();
		tradeToUpdate.setTradeId(2);
		tradeToUpdate.setAccount("NarcoticsAccount");
		tradeToUpdate.setType("Narcotics");
		
		mockMvc.perform(put("/poseidon/api/trades/2")
				.content(objectMapper.writeValueAsString(tradeToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.account").value("NarcoticsAccount"));
	}
	
	@Test
	void deleteTradeTest() throws Exception {
		
		TradeDto tradeToDelete = new TradeDto();
		tradeToDelete.setTradeId(1);
		tradeToDelete.setAccount("GamblingAccount");
		tradeToDelete.setType("Gambling");
		
		mockMvc.perform(delete("/poseidon/api/trades/1")
				.content(objectMapper.writeValueAsString(tradeToDelete))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.account").value("GamblingAccount"));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeAddingResourceTest() throws JsonProcessingException, Exception {
		
		TradeDto tradeToAdd = new TradeDto();
		tradeToAdd.setTradeId(1);
		tradeToAdd.setAccount("NarcoticsAccount");
		tradeToAdd.setType("Narcotics");
		
		mockMvc.perform(post("/poseidon/api/trades")
				.content(objectMapper.writeValueAsString(tradeToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NotAllowedIdSettingException))
				.andExpect(result -> assertEquals("Not allowed to set an id to resources.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenResourceIsNotFoundTest() throws Exception{
		
		mockMvc.perform(get("/poseidon/api/trades/10"))
		.andExpect(status().is4xxClientError())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
		.andExpect(result -> assertEquals("Trade with id 10 not found", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenUriIdIsDifferentFromResourceIdTest() throws JsonProcessingException, Exception {
		
		TradeDto tradeToUpdate = new TradeDto();
		tradeToUpdate.setTradeId(2);
		tradeToUpdate.setAccount("NarcoticsAccount");
		tradeToUpdate.setType("Narcotics");
		
		mockMvc.perform(put("/poseidon/api/trades/1")
				.content(objectMapper.writeValueAsString(tradeToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("The requested trade's id 1 is different from the currently handled trade's id.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToHandleUnexistingResourceTest() throws JsonProcessingException, Exception{
		
		TradeDto tradeToUpdate = new TradeDto();
		tradeToUpdate.setTradeId(10);
		tradeToUpdate.setAccount("NarcoticsAccount");
		tradeToUpdate.setType("Narcotics");
		
		mockMvc.perform(put("/poseidon/api/trades/10")
				.content(objectMapper.writeValueAsString(tradeToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("The trade with 10 id number is not registered.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToCreateInvalidResourceTest() throws JsonProcessingException, Exception {
		
		TradeDto tradeToAdd = new TradeDto();
		tradeToAdd.setBuyQuantity(-20.00);;
	
		mockMvc.perform(post("/poseidon/api/trades")
				.content(objectMapper.writeValueAsString(tradeToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
	}
	
}
