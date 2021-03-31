package com.poseidon.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dao.RatingDao;
import com.poseidon.dto.RatingDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;

@Service
@Transactional(rollbackOn = Exception.class)
public class RatingService implements IGenericService<RatingDto,Integer> {

	@Autowired
	private RatingDao ratingDao;

	@Override
	public List<RatingDto> getDtoList() {
		return ratingDao.getAllList();
	}

	@Override
	public RatingDto create(RatingDto dtoToCreate) throws NotAllowedIdSettingException {
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

}
