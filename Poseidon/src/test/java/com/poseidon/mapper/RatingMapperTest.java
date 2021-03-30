package com.poseidon.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.poseidon.dto.RatingDto;
import com.poseidon.entity.Rating;

public class RatingMapperTest {
	
	private RatingMapper ratingMapper = Mappers.getMapper(RatingMapper.class);
	
	@Test
	void ratingDtoToRatingTest() {
		RatingDto ratingDto  =new RatingDto();
		ratingDto.setRatingId(1);
		Rating rating = ratingMapper.ratingDtoToRating(ratingDto);
		assertEquals(1,rating.getRatingId());
	}
	
	@Test
	void ratingToRatingDtoTest() {
		Rating rating  =new Rating();
		rating.setRatingId(1);
		RatingDto ratingDto = ratingMapper.ratingToRatingDto(rating);
		assertEquals(1,ratingDto.getRatingId());
	}

}
