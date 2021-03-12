package com.poseidon.mapper;

import org.mapstruct.Mapper;

import com.poseidon.dto.RatingDto;
import com.poseidon.entity.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {

	RatingDto ratingToRatingDto (Rating rating);
	Rating ratingDtoToRating (RatingDto ratingDto);
}
