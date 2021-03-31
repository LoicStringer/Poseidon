package com.poseidon.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dao.RatingDao;
import com.poseidon.dto.RatingDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;

@DisplayName("RatingService CRUD operations tests")
@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

	@Mock
	private RatingDao ratingDao;
	
	@InjectMocks
	private RatingService ratingService;

	private static RatingDto testedRatingDto;

	@BeforeAll
	static void setUp() {
		testedRatingDto = new RatingDto();
		testedRatingDto.setRatingId(1);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void createTest() throws NotAllowedIdSettingException {
			when(ratingDao.create(any(RatingDto.class))).thenReturn(testedRatingDto);
			assertEquals(testedRatingDto, ratingService.create(testedRatingDto));
		}

		@Test
		void readTest() throws ResourceNotFoundException {
			when(ratingDao.read(any(Integer.class))).thenReturn(testedRatingDto);
			assertEquals(testedRatingDto, ratingService.read(1));
		}

		@Test
		void updateTest() throws ResourceNotFoundException {
			when(ratingDao.update(any(Integer.class),any(RatingDto.class))).thenReturn(testedRatingDto);
			assertEquals(testedRatingDto, ratingService.update(1,testedRatingDto));
		}

		@Test
		void deleteTest() throws ResourceNotFoundException {
			when(ratingDao.delete(any(Integer.class),any(RatingDto.class))).thenReturn(testedRatingDto);
			assertEquals(testedRatingDto, ratingService.delete(1,testedRatingDto));
		}

	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenCreatingAlreadyExistingRatingTest() throws NotAllowedIdSettingException {
			when(ratingDao.create(testedRatingDto)).thenThrow(NotAllowedIdSettingException.class);
			assertThrows(NotAllowedIdSettingException.class, () -> ratingService.create(testedRatingDto));
		}

		@Test
		void isExpectedExceptionThrownWhenTryingToFindUnexistingRatingTest() throws ResourceNotFoundException {
			when(ratingDao.read(any(Integer.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> ratingService.read(1));
		}

		@Test
		void isExpectedExceptionThrownWhenDeletingUnexistingRatingTest() throws ResourceNotFoundException {
			when(ratingDao.delete(any(Integer.class), any(RatingDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> ratingService.delete(1, testedRatingDto));
		}

		@Test
		void isExpectedExceptionThrownWhenDeletingRatingWithIncoherentIdTest() throws ResourceNotFoundException {
			when(ratingDao.delete(any(Integer.class), any(RatingDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> ratingService.delete(2, testedRatingDto));
		}

		@Test
		void isExpectedExceptionThrownWhenUpdatingUnexistingRatingTest() throws ResourceNotFoundException {
			when(ratingDao.update(any(Integer.class), any(RatingDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> ratingService.update(1, testedRatingDto));
		}

		@Test
		void isExpectedExceptionThrownWhenUpdatingRatingWithIncoherentIdTest() throws ResourceNotFoundException {
			when(ratingDao.update(any(Integer.class), any(RatingDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> ratingService.update(2, testedRatingDto));
		}
	}
}
