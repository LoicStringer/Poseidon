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
import com.poseidon.dto.RatingDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = { "USER", "ADMIN" })
@Transactional
public class RatingCrudTestIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void getRatingsListTest() throws Exception {

		mockMvc.perform(get("/poseidon/api/ratings")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].moodysRating").value("Aa1"));
	}

	@Test
	void getOneRatingTest() throws Exception {

		mockMvc.perform(get("/poseidon/api/ratings/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.moodysRating").value("Aa1"));
	}

	@Test
	void addRatingTest() throws Exception {

		RatingDto ratingToAdd = new RatingDto();
		ratingToAdd.setOrderNumber(1);
		ratingToAdd.setMoodysRating("AAA");

		mockMvc.perform(post("/poseidon/api/ratings").content(objectMapper.writeValueAsString(ratingToAdd))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.orderNumber").value(1));
	}

	@Test
	void updateRatingTest() throws Exception {

		RatingDto ratingToUpdate = new RatingDto();
		ratingToUpdate.setRatingId(2);
		ratingToUpdate.setOrderNumber(4);
		ratingToUpdate.setMoodysRating("AAB");

		mockMvc.perform(put("/poseidon/api/ratings/2").content(objectMapper.writeValueAsString(ratingToUpdate))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.orderNumber").value(4));
	}

	@Test
	void deleteRatingTest() throws Exception {

		RatingDto ratingToDelete = new RatingDto();
		ratingToDelete.setRatingId(1);
		ratingToDelete.setOrderNumber(1);
		ratingToDelete.setMoodysRating("Aa1");

		mockMvc.perform(delete("/poseidon/api/ratings/1").content(objectMapper.writeValueAsString(ratingToDelete))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.moodysRating").value("Aa1"));
	}

	@Test
	void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeAddingResourceTest()
			throws JsonProcessingException, Exception {

		RatingDto ratingToAdd = new RatingDto();
		ratingToAdd.setRatingId(1);
		ratingToAdd.setOrderNumber(1);
		ratingToAdd.setMoodysRating("AAA");

		mockMvc.perform(post("/poseidon/api/ratings").content(objectMapper.writeValueAsString(ratingToAdd))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NotAllowedIdSettingException))
				.andExpect(result -> assertEquals("Not allowed to set an id to resources.",
						result.getResolvedException().getMessage()));
	}

	@Test
	void isExpectedExceptionThrownWhenResourceIsNotFoundTest() throws Exception {

		mockMvc.perform(get("/poseidon/api/ratings/10")).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("Rating with id 10 not found.",
						result.getResolvedException().getMessage()));
	}

	@Test
	void isExpectedExceptionThrownWhenUriIdIsDifferentFromResourceIdTest() throws JsonProcessingException, Exception {

		RatingDto ratingToUpdate = new RatingDto();
		ratingToUpdate.setRatingId(2);
		ratingToUpdate.setOrderNumber(4);
		ratingToUpdate.setMoodysRating("AAB");

		mockMvc.perform(put("/poseidon/api/ratings/1").content(objectMapper.writeValueAsString(ratingToUpdate))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals(
						"The requested rating's id 1 is different from the currently handled rating's id.",
						result.getResolvedException().getMessage()));
	}

	@Test
	void isExpectedExceptionThrownWhenTryingToHandleUnexistingResourceTest() throws JsonProcessingException, Exception {

		RatingDto ratingToUpdate = new RatingDto();
		ratingToUpdate.setRatingId(10);
		ratingToUpdate.setOrderNumber(4);
		ratingToUpdate.setMoodysRating("AAB");

		mockMvc.perform(put("/poseidon/api/ratings/10").content(objectMapper.writeValueAsString(ratingToUpdate))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
				.andExpect(result -> assertEquals("The rating with 10 id number is not registered.",
						result.getResolvedException().getMessage()));
	}

	@Test
	void isExpectedExceptionThrownWhenTryingToCreateInvalidResourceTest() throws JsonProcessingException, Exception {

		RatingDto ratingToAdd = new RatingDto();
		ratingToAdd.setOrderNumber(-4);

		mockMvc.perform(post("/poseidon/api/ratings").content(objectMapper.writeValueAsString(ratingToAdd))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError()).andExpect(
						result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
	}

}
