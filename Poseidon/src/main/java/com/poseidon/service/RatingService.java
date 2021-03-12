package com.poseidon.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dao.RatingDao;
import com.poseidon.dto.RatingDto;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;

@Service
@Transactional(rollbackOn = Exception.class)
public class RatingService implements IGenericService<RatingDto,Integer> {

	@Autowired
	private RatingDao ratingDao;

	@Override
	public List<RatingDto> getDtoList() {
		return ratingDao.getAll();
	}

	@Override
	public RatingDto create(RatingDto dtoToCreate) throws DuplicatedResourceException {
		return ratingDao.create(dtoToCreate);
	}

	@Override
	public RatingDto read(Integer dtoId) throws ResourceNotFoundException {
		return ratingDao.read(dtoId);
	}

	@Override
	public RatingDto update(Integer dtoId, RatingDto dtoToUpdate) throws ResourceNotFoundException {
		return ratingDao.update(dtoId, dtoToUpdate);
	}

	@Override
	public RatingDto delete(Integer dtoId, RatingDto dtoToDelete) throws ResourceNotFoundException {
		return ratingDao.delete(dtoId, dtoToDelete);
	}

	
	/*
	@Autowired
	private RatingMapper ratingMapper ;
	
	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private ResourceIdChecker<Rating, Integer> resourceIdChecker ;
	
	public List<RatingDto> getAllRatings(){
		List<Rating> ratings = ratingRepository.findAll(); 
		List<RatingDto> ratingsDto = ratings.stream()
				.map(r-> ratingMapper.ratingToRatingDto(r))
				.collect(Collectors.toList());
		return ratingsDto;
	}
	
	public RatingDto create(RatingDto ratingToCreate) throws DuplicatedResourceException {
		resourceIdChecker.checkIfResourceExistsBeforeCreate(ratingToCreate.getRatingId());
		ratingRepository.save(ratingMapper.ratingDtoToRating(ratingToCreate));
		return ratingToCreate;
	}
	
	public RatingDto read(Integer id) throws ResourceNotFoundException {
		resourceIdChecker.checkIfResourceExistsBeforeRead(Rating.class, id);
		return ratingMapper.ratingToRatingDto(ratingRepository.findById(id).get());
	}
	
	public RatingDto update (Integer resourceId, RatingDto ratingToUpdate) throws ResourceNotFoundException {
		Integer ratingToUpdateId = ratingToUpdate.getRatingId();
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(Rating.class, resourceId, ratingToUpdateId);
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(Rating.class, resourceId, ratingToUpdateId);
		ratingRepository.save(ratingMapper.ratingDtoToRating(ratingToUpdate));
		return ratingToUpdate;
	}
	
	public RatingDto delete(Integer resourceId, RatingDto ratingToDelete) throws ResourceNotFoundException {
		Integer ratingToDeleteId = ratingToDelete.getRatingId();
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(Rating.class, resourceId, ratingToDeleteId);
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(Rating.class, resourceId, ratingToDeleteId);
		ratingRepository.delete(ratingMapper.ratingDtoToRating(ratingToDelete));
		return ratingToDelete;
	}
	*/
}
