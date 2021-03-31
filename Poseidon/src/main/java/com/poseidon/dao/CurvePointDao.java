package com.poseidon.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poseidon.dto.CurvePointDto;
import com.poseidon.entity.CurvePoint;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.CurvePointMapper;
import com.poseidon.repository.CurvePointRepository;

@Repository
public class CurvePointDao implements IGenericCrudDao<CurvePointDto, Integer> {

	@Autowired
	private CurvePointMapper curvePointMapper;

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Override
	public List<CurvePointDto> getAllList() {
		List<CurvePoint> curvePointEntitiesList = curvePointRepository.findAll();
		List<CurvePointDto> curvePointDtosList = curvePointEntitiesList.stream()
				.map(cp -> curvePointMapper.curvePointToCurvePointDto(cp)).collect(Collectors.toList());
		return curvePointDtosList;
	}

	@Override
	public CurvePointDto create(CurvePointDto curvePointToCreate) throws NotAllowedIdSettingException {
		preventResourceIdBreach(curvePointToCreate);
		curvePointRepository.save(curvePointMapper.curvePointDtoToCurvePoint(curvePointToCreate));
		return curvePointToCreate;
	}

	@Override
	public CurvePointDto read(Integer curvePointId) throws ResourceNotFoundException {
		CurvePoint curvePointToRead = curvePointRepository.findById(curvePointId).orElseThrow(
				() -> new ResourceNotFoundException("Curve point with id " + curvePointId + " not found."));
		return curvePointMapper.curvePointToCurvePointDto(curvePointToRead);
	}

	@Override
	public CurvePointDto update(Integer curvePointId, CurvePointDto updatedCurvePoint)
			throws ResourceNotFoundException {
		checkResourceExistence(curvePointId);
		checkResourceIdCoherence(curvePointId, updatedCurvePoint.getCurvePointId());
		curvePointRepository.save(curvePointMapper.curvePointDtoToCurvePoint(updatedCurvePoint));
		return updatedCurvePoint;
	}

	@Override
	public CurvePointDto delete(Integer curvePointId, CurvePointDto curvePointToDelete)
			throws ResourceNotFoundException {
		checkResourceExistence(curvePointId);
		checkResourceIdCoherence(curvePointId, curvePointToDelete.getCurvePointId());
		curvePointRepository.delete(curvePointMapper.curvePointDtoToCurvePoint(curvePointToDelete));
		return curvePointToDelete;
	}

	private void preventResourceIdBreach(CurvePointDto curvePointToCreate) throws NotAllowedIdSettingException {
		if (curvePointToCreate.getCurvePointId()!=null)
			throw new NotAllowedIdSettingException("Not allowed to set an id to resources.");
	}

	private void checkResourceExistence(Integer curvePointId) throws ResourceNotFoundException {
		if (!curvePointRepository.existsById(curvePointId))
			throw new ResourceNotFoundException(
					"The curve point with " + curvePointId + " id number is not registered.");
	}

	private void checkResourceIdCoherence(Integer targetCurvePointId, Integer treatedCurvePointId)
			throws ResourceNotFoundException {
		if (!targetCurvePointId.equals(treatedCurvePointId))
			throw new ResourceNotFoundException("The requested curve point's id " + targetCurvePointId
					+ " is different from the currently handled curve point's id.");
	}

}
