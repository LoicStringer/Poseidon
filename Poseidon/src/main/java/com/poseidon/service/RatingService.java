package com.poseidon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dto.RatingDto;
import com.poseidon.entity.Rating;
import com.poseidon.mappers.RatingMapper;
import com.poseidon.repository.RatingRepository;

@Service
public class RatingService {

	private RatingMapper ratingMapper = Mappers.getMapper(RatingMapper.class);
	
	@Autowired
	private RatingRepository ratingRepository;
	
	public List<RatingDto> getAllRatings(){
		List<Rating> ratings = ratingRepository.findAll(); 
		List<RatingDto> ratingsDto = ratings.stream()
				.map(r-> ratingMapper.ratingToRatingDto(r))
				.collect(Collectors.toList());
		return ratingsDto;
	}
	
	public RatingDto create(RatingDto ratingToCreate) {
		ratingRepository.save(ratingMapper.ratingDtoToRating(ratingToCreate));
		return ratingToCreate;
	}
	
	public RatingDto read(Integer id) {
		return ratingMapper.ratingToRatingDto(ratingRepository.findById(id).orElse(null));
	}
	
	public RatingDto update (RatingDto ratingToUpdate) {
		ratingRepository.save(ratingMapper.ratingDtoToRating(ratingToUpdate));
		return ratingToUpdate;
	}
	
	public RatingDto delete(RatingDto ratingToDelete) {
		ratingRepository.delete(ratingMapper.ratingDtoToRating(ratingToDelete));
		return ratingToDelete;
	}
	
}
