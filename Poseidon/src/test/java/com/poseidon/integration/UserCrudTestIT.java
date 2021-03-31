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
import com.poseidon.dto.UserDto;
import com.poseidon.exception.DuplicatedUserException;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.UserNotFoundException;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities= "ADMIN")
@Transactional
public class UserCrudTestIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void getUsersListTest() throws Exception {
		
		mockMvc.perform(get("/poseidon/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].userName").value("Tony"));
	}
	
	@Test
	void getOneUserTest() throws Exception {
		
		mockMvc.perform(get("/poseidon/users/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userName").value("Franck"));
	}
	
	
	@Test
	void addUserTest() throws Exception {
		
		UserDto userToAdd = new UserDto();
		userToAdd.setUserName("Michael");
		userToAdd.setUserPassword("goat6*Mvp");
		userToAdd.setUserFullname("MichaelJordan");
		userToAdd.setUserRole("USER");
		
		mockMvc.perform(post("/poseidon/users")
				.content(objectMapper.writeValueAsString(userToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userName").value("Michael"));
	}
	
	@Test
	void updateUserTest() throws Exception {
		
		UserDto userToUpdate = new UserDto();
		userToUpdate.setUserId(2);
		userToUpdate.setUserFullname("CarlitoBrigante");
		userToUpdate.setUserName("Carlito");
		userToUpdate.setUserPassword("carlitosWay1993!");
		userToUpdate.setUserRole("USER");
		
		mockMvc.perform(put("/poseidon/users/2")
				.content(objectMapper.writeValueAsString(userToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userName").value("Carlito"));
	}
	
	@Test
	void deleteUserTest() throws Exception {
		
		UserDto userToDelete = new UserDto();
		userToDelete.setUserId(2);
		userToDelete.setUserFullname("FranckSerpico");
		userToDelete.setUserName("Franck");
		userToDelete.setUserPassword("carlitosWay1993!");
		userToDelete.setUserRole("USER");
		
		
		mockMvc.perform(delete("/poseidon/users/2")
				.content(objectMapper.writeValueAsString(userToDelete))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userName").value("Franck"));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeAddingUserTest() throws JsonProcessingException, Exception {
		
		UserDto userToAdd = new UserDto();
		userToAdd.setUserId(1);
		userToAdd.setUserFullname("CarlitoBrigante");
		userToAdd.setUserName("Carlito");
		userToAdd.setUserPassword("carlitosWay1993!");
		userToAdd.setUserRole("USER");
		
		mockMvc.perform(post("/poseidon/users")
				.content(objectMapper.writeValueAsString(userToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NotAllowedIdSettingException))
				.andExpect(result -> assertEquals("Not allowed to set an id to users.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenUserIsNotFoundTest() throws Exception{
		
		mockMvc.perform(get("/poseidon/users/10"))
		.andExpect(status().is4xxClientError())
		.andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
		.andExpect(result -> assertEquals("User with id 10 not found.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenUriIdIsDifferentFromUserIdTest() throws JsonProcessingException, Exception {
		
		UserDto userToUpdate = new UserDto();
		userToUpdate.setUserId(2);
		userToUpdate.setUserFullname("CarlitoBrigante");
		userToUpdate.setUserName("Carlito");
		userToUpdate.setUserPassword("carlitosWay1993!");
		userToUpdate.setUserRole("USER");
		
		mockMvc.perform(put("/poseidon/users/1")
				.content(objectMapper.writeValueAsString(userToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
				.andExpect(result -> assertEquals("The requested user's id 1 is different from the currently handled user's id.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToHandleUnexistingUserTest() throws JsonProcessingException, Exception{
		
		UserDto userToUpdate = new UserDto();
		userToUpdate.setUserId(10);
		userToUpdate.setUserFullname("CarlitoBrigante");
		userToUpdate.setUserName("Carlito");
		userToUpdate.setUserPassword("carlitosWay1993!");
		userToUpdate.setUserRole("USER");
		
		mockMvc.perform(put("/poseidon/users/10")
				.content(objectMapper.writeValueAsString(userToUpdate))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
				.andExpect(result -> assertEquals("The user with 10 id number is not registered.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToInsertAlreadyRegisteredUserTest() throws JsonProcessingException, Exception {
		
		UserDto userToAdd = new UserDto();
		userToAdd.setUserFullname("TonyMontana");
		userToAdd.setUserName("Tony");
		userToAdd.setUserPassword("Scarface!1983");
		userToAdd.setUserRole("ADMIN");
		
		mockMvc.perform(post("/poseidon/users")
				.content(objectMapper.writeValueAsString(userToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof DuplicatedUserException))
				.andExpect(result -> assertEquals("User with the same user name Tony is already registered.", result.getResolvedException().getMessage()));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToHandleInvalidUserTest() throws JsonProcessingException, Exception {
		
		UserDto userToAdd = new UserDto();
		userToAdd.setUserFullname("KareemAbdulJabar");
		userToAdd.setUserName("Kareem");
		userToAdd.setUserPassword("Lakers1980!");
		userToAdd.setUserRole("");
		
		mockMvc.perform(post("/poseidon/users")
				.content(objectMapper.writeValueAsString(userToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToSetInvalidPasswordTest() throws JsonProcessingException, Exception {
		
		UserDto userToAdd = new UserDto();
		userToAdd.setUserFullname("KareemAbdulJabar");
		userToAdd.setUserName("Kareem");
		userToAdd.setUserPassword("Lakers");
		userToAdd.setUserRole("USER");
		
		mockMvc.perform(post("/poseidon/users")
				.content(objectMapper.writeValueAsString(userToAdd))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
	}
}
