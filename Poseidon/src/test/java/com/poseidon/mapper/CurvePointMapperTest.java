package com.poseidon.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.poseidon.dto.CurvePointDto;
import com.poseidon.entity.CurvePoint;

public class CurvePointMapperTest {

	private CurvePointMapper curvePointMapper = Mappers.getMapper(CurvePointMapper.class);
	
	@Test
	void curvePointDtoToCurvePointTest() {
		CurvePointDto curvePointDto = new CurvePointDto();
		curvePointDto.setCurvePointId(1);
		CurvePoint curvePoint = curvePointMapper.curvePointDtoToCurvePoint(curvePointDto);
		assertEquals(1,curvePoint.getCurvePointId());
	}
	
	@Test
	void curvePointToCurvePointDtoTest() {
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setCurvePointId(1);
		CurvePointDto curvePointDto = curvePointMapper.curvePointToCurvePointDto(curvePoint);
		assertEquals(1,curvePointDto.getCurvePointId());
	}
	
}
