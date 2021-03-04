package com.poseidon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.poseidon.entity.Rating;
import com.poseidon.dto.RatingDto;

@Mapper(componentModel = "spring")
public interface RatingMapper {

	RatingMapper ratingMapper = Mappers.getMapper(RatingMapper.class);
	
	RatingDto ratingToRatingDto (Rating rating);
	Rating ratingDtoToRating (RatingDto ratingDto);
}
