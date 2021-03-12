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
		return curvePointDao.getAll();
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

	
	
	/*
	@Autowired
	private CurvePointMapper curvePointMapper;
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	@Autowired
	private ResourceIdChecker<CurvePoint, Integer> resourceIdChecker ;
	
	
	public List<CurvePointDto> getAllCurvePoints(){
		List<CurvePoint> curvePoints = curvePointRepository.findAll();
		List<CurvePointDto> curvePointsDto = curvePoints.stream()
				.map(cp->curvePointMapper.curvePointToCurvePointDto(cp))
				.collect(Collectors.toList());
		return curvePointsDto;
	}
	
	public CurvePointDto create(CurvePointDto curvePointToCreate) throws DuplicatedResourceException {
		resourceIdChecker.checkIfResourceExistsBeforeCreate(curvePointToCreate.getCurvePointId());
		curvePointRepository.save(curvePointMapper.curvePointDtoToCurvePoint(curvePointToCreate));
		return curvePointToCreate;
	}
	
	public CurvePointDto read(Integer id) throws ResourceNotFoundException {
		resourceIdChecker.checkIfResourceExistsBeforeRead(CurvePoint.class, id);
		return curvePointMapper.curvePointToCurvePointDto(curvePointRepository.findById(id).get());
	}
	
	public CurvePointDto update (Integer resourceId, CurvePointDto curvePointToUpdate) throws ResourceNotFoundException {
		Integer curvePointToUpdateId = curvePointToUpdate.getCurvePointId();
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(CurvePoint.class, resourceId, curvePointToUpdateId);
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(CurvePoint.class, resourceId, curvePointToUpdateId);
		curvePointRepository.save(curvePointMapper.curvePointDtoToCurvePoint(curvePointToUpdate));
		return curvePointToUpdate;
	}
	
	public CurvePointDto  delete(Integer resourceId, CurvePointDto curvePointToDelete) throws ResourceNotFoundException {
		Integer curvePointToDeleteId = curvePointToDelete.getCurvePointId();
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(CurvePoint.class, resourceId, curvePointToDeleteId);
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(CurvePoint.class, resourceId, curvePointToDeleteId);
		curvePointRepository.delete(curvePointMapper.curvePointDtoToCurvePoint(curvePointToDelete));
		return curvePointToDelete;
	}
	*/
}
