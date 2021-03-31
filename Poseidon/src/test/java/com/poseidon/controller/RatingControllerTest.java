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

import com.poseidon.dto.RatingDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.RatingService;

@DisplayName("RatingController CRUD operations tests")
@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {


	@Mock
	private RatingService ratingService;

	@InjectMocks
	private RatingController ratingController;

	private static RatingDto ratingDto;
	private static List<RatingDto> ratingDtosList;

	@BeforeAll
	static void setUp() {
		ratingDto = new RatingDto();
		ratingDto.setRatingId(1);
		ratingDto.setOrderNumber(1);
		ratingDtosList = new ArrayList<RatingDto>();
		ratingDtosList.add(ratingDto);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getRatingsListTest() {
			when(ratingService.getDtoList()).thenReturn(ratingDtosList);
			assertEquals(ResponseEntity.ok(ratingDtosList), ratingController.getRatingsList());
		}

		@Test
		void getOneRatingTest() throws ResourceNotFoundException {
			when(ratingService.read(any(Integer.class))).thenReturn(ratingDto);
			assertEquals(ResponseEntity.ok(ratingDto), ratingController.getOneRating(1));
		}

		@Test
		void addRatingTest() throws NotAllowedIdSettingException {
			when(ratingService.create(any(RatingDto.class))).thenReturn(ratingDto);
			assertEquals(ResponseEntity.ok(ratingDto), ratingController.addRating(ratingDto));
		}

		@Test
		void updateRatingTest() throws ResourceNotFoundException {
			when(ratingService.update(any(Integer.class), any(RatingDto.class))).thenReturn(ratingDto);
			assertEquals(ResponseEntity.ok(ratingDto), ratingController.updateRating(1, ratingDto));
		}

		@Test
		void deleteratingTest() throws ResourceNotFoundException {
			when(ratingService.delete(any(Integer.class), any(RatingDto.class))).thenReturn(ratingDto);
			assertEquals(ResponseEntity.ok(ratingDto), ratingController.deleteRating(1, ratingDto));
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeAddingResourceTest() throws NotAllowedIdSettingException {
			when(ratingService.create(any(RatingDto.class))).thenThrow(NotAllowedIdSettingException.class);
			assertThrows(NotAllowedIdSettingException.class,()->ratingController.addRating(ratingDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToGetUnexistingResourceTest() throws ResourceNotFoundException {
			when(ratingService.read(any(Integer.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->ratingController.getOneRating(2));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToUpdategResourceNotFoundTest() throws ResourceNotFoundException {
			when(ratingService.update(any(Integer.class),any(RatingDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->ratingController.updateRating(2,ratingDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToDeleteResourceNotFoundTest() throws ResourceNotFoundException {
			when(ratingService.delete(any(Integer.class),any(RatingDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->ratingController.deleteRating(2,ratingDto));
		}
		
	}
	
}
