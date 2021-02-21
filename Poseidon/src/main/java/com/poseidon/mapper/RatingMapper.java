package com.poseidon.mapper;

import org.mapstruct.Mapper;

import com.poseidon.entity.Rating;
import com.poseidon.dto.RatingDto;

@Mapper(componentModel = "spring")
public interface RatingMapper {

	RatingDto ratingToRatingDto (Rating rating);
	Rating ratingDtoToRating (RatingDto ratingDto);
}
