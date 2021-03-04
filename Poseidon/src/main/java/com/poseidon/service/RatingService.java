package com.poseidon.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dto.RatingDto;
import com.poseidon.entity.Rating;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.RatingMapper;
import com.poseidon.repository.RatingRepository;

@Service
@Transactional(rollbackOn = Exception.class)
public class RatingService {

	@Autowired
	private RatingMapper ratingMapper ;
	
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
	
	public RatingDto read(Integer id) throws ResourceNotFoundException {
		return ratingMapper.ratingToRatingDto(ratingRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Rating not found")));
	}
	
	public RatingDto update (RatingDto ratingToUpdate) throws ResourceNotFoundException {
		if(!ratingRepository.existsById(ratingToUpdate.getRatingId()))
			throw new ResourceNotFoundException("Rating not found");
		ratingRepository.save(ratingMapper.ratingDtoToRating(ratingToUpdate));
		return ratingToUpdate;
	}
	
	public RatingDto delete(RatingDto ratingToDelete) throws ResourceNotFoundException {
		if(!ratingRepository.existsById(ratingToDelete.getRatingId()))
			throw new ResourceNotFoundException("Rating not found");
		ratingRepository.delete(ratingMapper.ratingDtoToRating(ratingToDelete));
		return ratingToDelete;
	}
	
}
