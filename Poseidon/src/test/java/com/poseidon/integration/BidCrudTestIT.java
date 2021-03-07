package com.poseidon.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poseidon.dto.BidDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BidCrudTestIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void test() throws JsonProcessingException, Exception {
		
		BidDto bid = new BidDto();
		bid.setBidId(1);
		bid.setAccount("GamblingAccount");
		bid.setType("Gambling");
		mockMvc.perform(post("/bids")
				.content(objectMapper.writeValueAsString(bid))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
						

	}

}
