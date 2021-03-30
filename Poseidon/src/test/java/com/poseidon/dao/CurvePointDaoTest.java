package com.poseidon.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dto.CurvePointDto;
import com.poseidon.entity.CurvePoint;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.CurvePointMapper;
import com.poseidon.repository.CurvePointRepository;

@DisplayName("CurvePointDao CRUD operations tests")
@ExtendWith(MockitoExtension.class)
public class CurvePointDaoTest {
	
	@Mock
	private CurvePointRepository curvePointRepository;

	@Mock
	private CurvePointMapper curvePointMapper;

	@InjectMocks
	private CurvePointDao curvePointDao;

	private static CurvePoint testedCurvePoint;
	private static List<CurvePoint> curvePointsList;
	private static CurvePointDto testedCurvePointDto;
	private static List<CurvePointDto> curvePointDtosList;

	@BeforeAll
	static void setUp() {
		testedCurvePoint = new CurvePoint();
		testedCurvePoint.setCurvePointId(1);
		curvePointsList = new ArrayList<CurvePoint>();
		curvePointsList.add(testedCurvePoint);
		testedCurvePointDto = new CurvePointDto();
		testedCurvePointDto.setCurvePointId(1);
		curvePointDtosList = new ArrayList<CurvePointDto>();
		curvePointDtosList.add(testedCurvePointDto);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getAllTest() {
			when(curvePointMapper.curvePointToCurvePointDto(testedCurvePoint)).thenReturn(testedCurvePointDto);
			when(curvePointRepository.findAll()).thenReturn(curvePointsList);
			assertEquals(curvePointDtosList, curvePointDao.getAllList());
		}

		@Test
		void createTest() throws DuplicatedResourceException {
			when(curvePointMapper.curvePointDtoToCurvePoint(testedCurvePointDto)).thenReturn(testedCurvePoint);
			when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(testedCurvePoint);
			assertEquals(testedCurvePointDto, curvePointDao.create(testedCurvePointDto));
		}

		@Test
		void readTest() throws ResourceNotFoundException {
			when(curvePointMapper.curvePointToCurvePointDto(testedCurvePoint)).thenReturn(testedCurvePointDto);
			when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.of(testedCurvePoint));
			assertEquals(testedCurvePointDto, curvePointDao.read(1));
		}

		@Test
		void updateTest() throws ResourceNotFoundException {
			when(curvePointMapper.curvePointDtoToCurvePoint(testedCurvePointDto)).thenReturn(testedCurvePoint);
			when(curvePointRepository.existsById(any(Integer.class))).thenReturn(true);
			when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(testedCurvePoint);
			assertEquals(testedCurvePointDto, curvePointDao.update(1, testedCurvePointDto));
		}

		@Test
		void deleteTest() throws ResourceNotFoundException {
			when(curvePointRepository.existsById(any(Integer.class))).thenReturn(true);
			assertEquals(testedCurvePointDto, curvePointDao.delete(1, testedCurvePointDto));
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {
		
		@Test
		void isExpectedExceptionThrownWhenBidIsNotFound() {
			assertThrows(ResourceNotFoundException.class, ()->curvePointDao.read(2));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeCreate() {
			when(curvePointRepository.existsById(any(Integer.class))).thenReturn(true);
			assertThrows(DuplicatedResourceException.class,()->curvePointDao.create(testedCurvePointDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenRequestIdIsDifferentFromResourceId() {
			assertThrows(ResourceNotFoundException.class,()->curvePointDao.update(2, testedCurvePointDto));
		}
	}
	

}
