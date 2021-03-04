package com.poseidon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.poseidon.entity.CurvePoint;
import com.poseidon.dto.CurvePointDto;

@Mapper(componentModel = "spring")
public interface CurvePointMapper {

	CurvePointMapper curvePointMapper = Mappers.getMapper(CurvePointMapper.class);
	
	CurvePointDto curvePointToCurvePointDto (CurvePoint curvePoint);
	CurvePoint curvePointDtoToCurvePoint (CurvePointDto curvePointDto);
}
