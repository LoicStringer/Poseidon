package com.poseidon.mapper;

import org.mapstruct.Mapper;

import com.poseidon.dto.CurvePointDto;
import com.poseidon.entity.CurvePoint;

@Mapper(componentModel = "spring")
public interface CurvePointMapper {

	CurvePointDto curvePointToCurvePointDto (CurvePoint curvePoint);
	CurvePoint curvePointDtoToCurvePoint (CurvePointDto curvePointDto);
}
