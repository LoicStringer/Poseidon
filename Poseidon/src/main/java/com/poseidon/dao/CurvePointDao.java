package com.poseidon.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.poseidon.dto.CurvePointDto;
import com.poseidon.entity.CurvePoint;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.CurvePointMapper;
import com.poseidon.utilities.ResourceIdChecker;

@Repository
public class CurvePointDao extends GenericDao<CurvePoint, Integer> implements IGenericDao<CurvePointDto,Integer>{
	
	@Autowired
	private ResourceIdChecker<CurvePoint,Integer> resourceIdChecker;
	
	@Autowired
	private CurvePointMapper curvePointMapper;
	
	@Value("Curve point")
	private String resourceName;

	@Override
	public List<CurvePointDto> getAll() {
		List<CurvePoint> curvePointEntitiesList = getAllEntity();
		List<CurvePointDto> curvePointDtosList = curvePointEntitiesList.stream()
				.map(cp->curvePointMapper.curvePointToCurvePointDto(cp))
				.collect(Collectors.toList());
		return curvePointDtosList;
	}

	@Override
	public CurvePointDto create(CurvePointDto entityToCreateAsDto) throws DuplicatedResourceException {
		resourceIdChecker.checkIfResourceExistsBeforeCreate(entityToCreateAsDto.getCurvePointId());
		createEntity(curvePointMapper.curvePointDtoToCurvePoint(entityToCreateAsDto));
		return entityToCreateAsDto;
	}

	@Override
	public CurvePointDto read(Integer entityId) throws ResourceNotFoundException {
		resourceIdChecker.checkIfResourceExistsBeforeRead(resourceName, entityId);
		return curvePointMapper.curvePointToCurvePointDto(readEntity(entityId));
	}

	@Override
	public CurvePointDto update(Integer entityId, CurvePointDto entityToUpdateAsDto) throws ResourceNotFoundException {
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(resourceName, entityId, entityToUpdateAsDto.getCurvePointId());
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(resourceName, entityId,  entityToUpdateAsDto.getCurvePointId());
		updateEntity(entityId, curvePointMapper.curvePointDtoToCurvePoint(entityToUpdateAsDto));
		return entityToUpdateAsDto;
	}

	@Override
	public CurvePointDto delete(Integer entityId, CurvePointDto entityToDeleteAsDto) throws ResourceNotFoundException {
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(resourceName, entityId, entityToDeleteAsDto.getCurvePointId());
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(resourceName, entityId,  entityToDeleteAsDto.getCurvePointId());
		deleteEntity(entityId, curvePointMapper.curvePointDtoToCurvePoint(entityToDeleteAsDto));
		return entityToDeleteAsDto;
	}

	
	
}
