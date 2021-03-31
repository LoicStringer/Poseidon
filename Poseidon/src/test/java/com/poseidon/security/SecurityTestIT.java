package com.poseidon.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTestIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	void permittedAccessShouldSucceedTest() throws Exception {

		mockMvc.perform(get("/poseidon")).andExpect(status().isOk()).andExpect(
				result -> assertEquals("Welcome to Poseidon Home page !", result.getResponse().getContentAsString()));

		mockMvc.perform(get("/poseidon/login")).andExpect(status().isOk()).andExpect(
				result -> assertEquals("Welcome to login page !", result.getResponse().getContentAsString()));
	}

	@Test
	void unauthenticatedAccessShouldBeDeniedTest() throws Exception {

		mockMvc.perform(get("/poseidon/api")).andExpect(status().isUnauthorized());
		mockMvc.perform(get("/poseidon/users")).andExpect(status().isUnauthorized());
		mockMvc.perform(get("/poseidon/admin")).andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "Tony", password = "scarface", authorities = "USER")
	void authorizationWithRoleUserTest() throws Exception {

		mockMvc.perform(get("/poseidon/api/bids")).andExpect(status().isOk());
		mockMvc.perform(get("/poseidon/users")).andExpect(status().isForbidden());
		mockMvc.perform(get("/poseidon/admin")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "Tony", password = "scarface", authorities = "ADMIN")
	void authorizationWithRoleAdminTest() throws Exception {

		mockMvc.perform(get("/poseidon/api/bids")).andExpect(status().isOk());
		mockMvc.perform(get("/poseidon/users")).andExpect(status().isOk());
		mockMvc.perform(get("/poseidon/admin")).andExpect(status().isOk());
	}

	@Test
	void getOAuthTokenShouldSucceedWithRegisteredCredentialsTest() throws Exception {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", "my-trusted-client");
		params.add("username", "Tony");
		params.add("password", "scarface");

		mockMvc.perform(post("/oauth/token").params(params).with(httpBasic("my-trusted-client", "secret"))
				.accept("application/json;charset=UTF-8")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@Test
	void getOAuthTokenShouldFailWithUnregisteredCredentialsTest() throws Exception {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", "my-trusted-client");
		params.add("username", "Toto");
		params.add("password", "toto");

		mockMvc.perform(post("/oauth/token").params(params).with(httpBasic("my-trusted-client", "secret"))
				.accept("application/json;charset=UTF-8")).andExpect(status().isBadRequest());
	}
	
	
}
