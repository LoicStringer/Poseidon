package com.poseidon.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dto.RatingDto;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.repository.RatingRepository;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

	@Mock
	private RatingRepository ratingRepository;

	@InjectMocks
	private RatingService ratingService;

	private static RatingDto testedRatingDto;

	@BeforeAll
	static void setUp() {
		testedRatingDto = new RatingDto();
		testedRatingDto.setRatingId(1);
	}

	@Test
	void isExpectedExceptionThrownWhenTryingToFindUnexistingRatingTest() {
		when(ratingRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> ratingService.read(1));
	}

	@Test
	void isExpectedExceptionThrownWhenDeletingUnexistingRatingTest() {
		when(ratingRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> ratingService.delete(testedRatingDto));
	}

	@Test
	void isExpectedExceptionThrownWhenUpdatingUnexistingRatingTest() {
		when(ratingRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> ratingService.update(testedRatingDto));
	}


}
