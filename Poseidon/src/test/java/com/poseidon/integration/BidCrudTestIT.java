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
import com.poseidon.dto.BidDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities= {"USER","ADMIN"})
@Transactional
class BidCrudTestIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void getBidsListTest() throws Exception {
		
		mockMvc.perform(get("/poseidon/api/bids"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].account").value("GamblingAccount"));
	}
	
	@Test
	void getOneBidTest() throws Exception {
		
		mockMvc.perform(get("/poseidon/api/bids/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.account").value("GamblingAccount"));
	}
	
	@Test
	void addBidTest() throws Exception {
		
		BidDto bidToAdd = new BidDto();
		bidToAdd.setAccount("NarcoticsAccount");
		bidToAdd.setType("Narcotics");
		
		mockMvc.perform(post("/poseidon/api/bids")
				.content(objectMapper.writeValueAsString(bidToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.account").value("NarcoticsAccount"));
	}
	
	
	@Test
	void updateBidTest() throws Exception {
		
		BidDto bidToUpdate = new BidDto();
		bidToUpdate.setBidId(2);
		bidToUpdate.setAccount("NarcoticsAccount");
		bidToUpdate.setType("Narcotics");
		
		mockMvc.perform(put("/poseidon/api/bids/2")
				.content(objectMapper.writeValueAsString(bidToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.account").value("NarcoticsAccount"));
	}
	
	@Test
	void deleteBidTest() throws Exception {
		
		BidDto bidToDelete = new BidDto();
		bidToDelete.setBidId(1);
		bidToDelete.setAccount("GamblingAccount");
		bidToDelete.setType("Gambling");
		
		mockMvc.perform(delete("/poseidon/api/bids/1")
				.content(objectMapper.writeValueAsString(bidToDelete))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.account").value("GamblingAccount"));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeAddingResourceTest() throws JsonProcessingException, Exception {
		
		BidDto bidToAdd = new BidDto();
		bidToAdd.setBidId(1);
		bidToAdd.setAccount("NarcoticsAccount");
		bidToAdd.setType("Narcotics");
		
		mockMvc.perform(post("/poseidon/api/bids")
				.content(objectMapper.writeValueAsString(bidToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NotAllowedIdSettingException))
				.andExpect(result -> assertEquals("Not allowed to set an id to resources.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenResourceIsNotFoundTest() throws Exception{
		
		mockMvc.perform(get("/poseidon/api/bids/10"))
		.andExpect(status().is4xxClientError())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
		.andExpect(result -> assertEquals("Bid with id 10 not found", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenUriIdIsDifferentFromResourceIdTest() throws JsonProcessingException, Exception {
		
		BidDto bidToUpdate = new BidDto();
		bidToUpdate.setBidId(2);
		bidToUpdate.setAccount("NarcoticsAccount");
		bidToUpdate.setType("Narcotics");
		
		mockMvc.perform(put("/poseidon/api/bids/1")
				.content(objectMapper.writeValueAsString(bidToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("The requested bid's id 1 is different from the currently handled bid's id.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToHandleUnexistingResourceTest() throws JsonProcessingException, Exception{
		
		BidDto bidToUpdate = new BidDto();
		bidToUpdate.setBidId(10);
		bidToUpdate.setAccount("NarcoticsAccount");
		bidToUpdate.setType("Narcotics");
		
		mockMvc.perform(put("/poseidon/api/bids/10")
				.content(objectMapper.writeValueAsString(bidToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("The bid with 10 id number is not registered.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToCreateInvalidResourceTest() throws JsonProcessingException, Exception {
		
		BidDto bidToAdd = new BidDto();
		bidToAdd.setAccount("NarcoticsAccount");
	
		mockMvc.perform(post("/poseidon/api/bids")
				.content(objectMapper.writeValueAsString(bidToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
	}
}
