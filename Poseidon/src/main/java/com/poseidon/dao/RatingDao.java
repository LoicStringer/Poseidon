package com.poseidon.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.poseidon.dto.RatingDto;
import com.poseidon.entity.Rating;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.RatingMapper;
import com.poseidon.utilities.ResourceIdChecker;

@Repository
public class RatingDao extends GenericDao<Rating, Integer> implements IGenericDao<RatingDto, Integer>{
	
	@Autowired
	private ResourceIdChecker<Rating, Integer> resourceIdChecker;
	
	@Autowired
	private RatingMapper ratingMapper;
	
	@Value("Rating")
	private String resourceName;
	
	@Override
	public List<RatingDto> getAll() {
		List<Rating> ratingEntitiesList = getAllEntity();
		List<RatingDto> ratingDtosList = ratingEntitiesList.stream()
				.map(r-> ratingMapper.ratingToRatingDto(r))
				.collect(Collectors.toList());
		return ratingDtosList;
	}

	@Override
	public RatingDto create(RatingDto entityToCreateAsDto) throws DuplicatedResourceException {
		resourceIdChecker.checkIfResourceExistsBeforeCreate(entityToCreateAsDto.getRatingId());
		createEntity(ratingMapper.ratingDtoToRating(entityToCreateAsDto));
		return entityToCreateAsDto;
	}

	@Override
	public RatingDto read(Integer entityId) throws ResourceNotFoundException {
		resourceIdChecker.checkIfResourceExistsBeforeRead(resourceName, entityId);
		return ratingMapper.ratingToRatingDto(readEntity(entityId));
	}

	@Override
	public RatingDto update(Integer entityId, RatingDto entityToUpdateAsDto) throws ResourceNotFoundException {
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(resourceName, entityId, entityToUpdateAsDto.getRatingId());
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(resourceName, entityId,  entityToUpdateAsDto.getRatingId());
		updateEntity(entityId, ratingMapper.ratingDtoToRating(entityToUpdateAsDto));
		return entityToUpdateAsDto;
	}

	@Override
	public RatingDto delete(Integer entityId, RatingDto entityToDeleteAsDto) throws ResourceNotFoundException {
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(resourceName, entityId, entityToDeleteAsDto.getRatingId());
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(resourceName, entityId,  entityToDeleteAsDto.getRatingId());
		deleteEntity(entityId, ratingMapper.ratingDtoToRating(entityToDeleteAsDto));
		return entityToDeleteAsDto;
	}

	
	
}
