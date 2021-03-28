package com.poseidon.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dao.CurvePointDao;
import com.poseidon.dto.CurvePointDto;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;

@Service
@Transactional(rollbackOn = Exception.class)
public class CurvePointService implements IGenericService<CurvePointDto, Integer>{
	
	@Autowired
	private CurvePointDao curvePointDao;

	@Override
	public List<CurvePointDto> getDtoList() {
		return curvePointDao.getAllList();
	}

	@Override
	public CurvePointDto create(CurvePointDto dtoToCreate) throws DuplicatedResourceException {
		return curvePointDao.create(dtoToCreate);
	}

	@Override
	public CurvePointDto read(Integer dtoId) throws ResourceNotFoundException {
		return curvePointDao.read(dtoId);
	}

	@Override
	public CurvePointDto update(Integer dtoId, CurvePointDto dtoToUpdate) throws ResourceNotFoundException {
		return curvePointDao.update(dtoId, dtoToUpdate);
	}

	@Override
	public CurvePointDto delete(Integer dtoId, CurvePointDto dtoToDelete) throws ResourceNotFoundException {
		return curvePointDao.delete(dtoId, dtoToDelete);
	}

}
