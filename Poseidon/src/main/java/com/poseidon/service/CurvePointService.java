package com.poseidon.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dto.CurvePointDto;
import com.poseidon.entity.CurvePoint;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.CurvePointMapper;
import com.poseidon.repository.CurvePointRepository;

@Service
@Transactional(rollbackOn = Exception.class)
public class CurvePointService {

	@Autowired
	private CurvePointMapper curvePointMapper;
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	public List<CurvePointDto> getAllCurvePoints(){
		List<CurvePoint> curvePoints = curvePointRepository.findAll();
		List<CurvePointDto> curvePointsDto = curvePoints.stream()
				.map(cp->curvePointMapper.curvePointToCurvePointDto(cp))
				.collect(Collectors.toList());
		return curvePointsDto;
	}
	
	public CurvePointDto create(CurvePointDto curvePointToCreate) {
		curvePointRepository.save(curvePointMapper.curvePointDtoToCurvePoint(curvePointToCreate));
		return curvePointToCreate;
	}
	
	public CurvePointDto read(Integer id) throws ResourceNotFoundException {
		return curvePointMapper.curvePointToCurvePointDto(curvePointRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Curve point not found")));
	}
	
	public CurvePointDto update (CurvePointDto curvePointToUpdate) throws ResourceNotFoundException {
		if(!curvePointRepository.existsById(curvePointToUpdate.getCurvePointId()))
			throw new ResourceNotFoundException("Curve point not found");
		curvePointRepository.save(curvePointMapper.curvePointDtoToCurvePoint(curvePointToUpdate));
		return curvePointToUpdate;
	}
	
	public CurvePointDto  delete(CurvePointDto curvePointToDelete) throws ResourceNotFoundException {
		if(!curvePointRepository.existsById(curvePointToDelete.getCurvePointId()))
			throw new ResourceNotFoundException("Curve point not found");
		curvePointRepository.delete(curvePointMapper.curvePointDtoToCurvePoint(curvePointToDelete));
		return curvePointToDelete;
	}
	
}
