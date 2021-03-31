package com.poseidon.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dto.RatingDto;
import com.poseidon.entity.Rating;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.RatingMapper;
import com.poseidon.repository.RatingRepository;

@DisplayName("RatingDao CRUD operations tests")
@ExtendWith(MockitoExtension.class)
public class RatingDaoTest {
	
	@Mock
	private RatingRepository ratingRepository;

	@Mock
	private RatingMapper ratingMapper;

	@InjectMocks
	private RatingDao ratingDao;

	private static Rating testedRating;
	private static List<Rating> ratingsList;
	private static RatingDto testedRatingDto;
	private static List<RatingDto> ratingDtosList;

	@BeforeAll
	static void setUp() {
		testedRating = new Rating();
		testedRating.setRatingId(1);
		ratingsList = new ArrayList<Rating>();
		ratingsList.add(testedRating);
		testedRatingDto = new RatingDto();
		testedRatingDto.setRatingId(1);
		ratingDtosList = new ArrayList<RatingDto>();
		ratingDtosList.add(testedRatingDto);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getAllTest() {
			when(ratingMapper.ratingToRatingDto(testedRating)).thenReturn(testedRatingDto);
			when(ratingRepository.findAll()).thenReturn(ratingsList);
			assertEquals(ratingDtosList, ratingDao.getAllList());
		}

		@Test
		void createTest() throws NotAllowedIdSettingException {
			Rating ratingToCreate = new Rating();
			ratingToCreate.setOrderNumber(1);
			RatingDto ratingDtoToCreate = new RatingDto(); 
			ratingDtoToCreate.setOrderNumber(1);
			when(ratingMapper.ratingDtoToRating(ratingDtoToCreate)).thenReturn(ratingToCreate);
			when(ratingRepository.save(any(Rating.class))).thenReturn(ratingToCreate);
			assertEquals(ratingDtoToCreate, ratingDao.create(ratingDtoToCreate));
		}

		@Test
		void readTest() throws ResourceNotFoundException {
			when(ratingMapper.ratingToRatingDto(testedRating)).thenReturn(testedRatingDto);
			when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.of(testedRating));
			assertEquals(testedRatingDto, ratingDao.read(1));
		}

		@Test
		void updateTest() throws ResourceNotFoundException {
			when(ratingMapper.ratingDtoToRating(testedRatingDto)).thenReturn(testedRating);
			when(ratingRepository.existsById(any(Integer.class))).thenReturn(true);
			when(ratingRepository.save(any(Rating.class))).thenReturn(testedRating);
			assertEquals(testedRatingDto, ratingDao.update(1, testedRatingDto));
		}

		@Test
		void deleteTest() throws ResourceNotFoundException {
			when(ratingRepository.existsById(any(Integer.class))).thenReturn(true);
			assertEquals(testedRatingDto, ratingDao.delete(1, testedRatingDto));
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {
		
		@Test
		void isExpectedExceptionThrownWhenRatingIsNotFound() {
			assertThrows(ResourceNotFoundException.class, ()->ratingDao.read(2));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeCreate() {
			assertThrows(NotAllowedIdSettingException.class,()->ratingDao.create(testedRatingDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenRequestIdIsDifferentFromResourceId() {
			assertThrows(ResourceNotFoundException.class,()->ratingDao.update(2, testedRatingDto));
		}
	}
	

}
