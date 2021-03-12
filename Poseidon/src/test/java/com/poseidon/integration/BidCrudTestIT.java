package com.poseidon.integration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParser;
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

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
	}

	@Test
	void test() throws JsonProcessingException, Exception {

		BidDto bid = new BidDto();
		bid.setBidId(1);
		bid.setAccount("GamblingAccount");
		bid.setType("Gambling");
		mockMvc.perform(
				post("/bids").content(objectMapper.writeValueAsString(bid)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	/*
	private String obtainAccessToken(String username, String password) throws Exception {
		 
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("client_id", "fooClientIdPassword");
	    params.add("username", username);
	    params.add("password", password);

	    ResultActions result 
	      = mockMvc.perform(post("/oauth/token")
	        .params(params)
	        .with(httpBasic("fooClientIdPassword","secret"))
	        .accept("application/json;charset=UTF-8"))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json;charset=UTF-8"));

	    String resultString = result.andReturn().getResponse().getContentAsString();
	   
	    return null;
	}

	*/
	
}
