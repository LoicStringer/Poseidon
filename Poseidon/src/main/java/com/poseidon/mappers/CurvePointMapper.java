package com.poseidon.mappers;

import org.mapstruct.Mapper;

import com.poseidon.entity.CurvePoint;
import com.poseidon.dto.CurvePointDto;

@Mapper(componentModel = "spring")
public interface CurvePointMapper {

	CurvePointDto curvePointToCurvePointDto (CurvePoint curvePoint);
	CurvePoint curvePointDtoToCurvePoint (CurvePointDto curvePointDto);
}
