package com.poseidon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dto.CurvePointDto;
import com.poseidon.entity.CurvePoint;
import com.poseidon.mapper.CurvePointMapper;
import com.poseidon.repository.CurvePointRepository;

@Service
public class CurvePointService {

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
	
	public CurvePointDto read(Integer id) {
		return curvePointMapper.curvePointToCurvePointDto(curvePointRepository.findById(id).orElse(null));
	}
	
	public CurvePointDto update (CurvePointDto curvePointToUpdate) {
		curvePointRepository.save(curvePointMapper.curvePointDtoToCurvePoint(curvePointToUpdate));
		return curvePointToUpdate;
	}
	
	public CurvePointDto  delete(CurvePointDto curvePointToDelete) {
		curvePointRepository.delete(curvePointMapper.curvePointDtoToCurvePoint(curvePointToDelete));
		return curvePointToDelete;
	}
	
}
