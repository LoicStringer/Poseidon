package com.poseidon.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poseidon.dto.RatingDto;
import com.poseidon.entity.Rating;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.RatingMapper;
import com.poseidon.repository.RatingRepository;

@Repository
public class RatingDao implements IGenericCrudDao<RatingDto,Integer>  {
	
	@Autowired
	private RatingMapper ratingMapper;
	
	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public List<RatingDto> getAllList() {
		List<Rating> ratingEntitiesList = ratingRepository.findAll();
		List<RatingDto> ratingDtosList = ratingEntitiesList.stream()
				.map(r-> ratingMapper.ratingToRatingDto(r))
				.collect(Collectors.toList());
		return ratingDtosList;
	}

	@Override
	public RatingDto create(RatingDto ratingToCreate) throws NotAllowedIdSettingException {
		preventResourceIdBreach(ratingToCreate);
		ratingRepository.save(ratingMapper.ratingDtoToRating(ratingToCreate));
		return ratingToCreate;
	}

	@Override
	public RatingDto read(Integer ratingId) throws ResourceNotFoundException {
		Rating ratingToRead = ratingRepository.findById(ratingId).orElseThrow(()-> new ResourceNotFoundException("Rating with id "+ratingId+" not found."));
		return ratingMapper.ratingToRatingDto(ratingToRead);
	}

	@Override
	public RatingDto update(Integer ratingId, RatingDto updatedRating) throws ResourceNotFoundException {
		checkResourceExistence(ratingId);
		checkResourceIdCoherence(ratingId, updatedRating.getRatingId());
		ratingRepository.save(ratingMapper.ratingDtoToRating(updatedRating));
		return updatedRating;
	}

	@Override
	public RatingDto delete(Integer ratingId, RatingDto ratingToDelete) throws ResourceNotFoundException {
		checkResourceExistence(ratingId);
		checkResourceIdCoherence(ratingId, ratingToDelete.getRatingId());
		ratingRepository.delete(ratingMapper.ratingDtoToRating(ratingToDelete));
		return ratingToDelete;
	}

	private void preventResourceIdBreach(RatingDto ratingToCreate) throws NotAllowedIdSettingException {
		if(ratingToCreate.getRatingId()!=null)
			throw new NotAllowedIdSettingException("Not allowed to set an id to resources.");
	}

	private void checkResourceExistence(Integer ratingId) throws ResourceNotFoundException {
		if(!ratingRepository.existsById(ratingId))
			throw new ResourceNotFoundException("The rating with "+ratingId+ " id number is not registered.");
	}
	
	private void checkResourceIdCoherence(Integer targetRatingId, Integer treatedRatingId) throws ResourceNotFoundException {
		if(!targetRatingId.equals(treatedRatingId))
			throw new ResourceNotFoundException("The requested rating's id "+targetRatingId+ " is different from the currently handled rating's id.");
	}
	
}
