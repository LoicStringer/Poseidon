package com.poseidon.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dao.CurvePointDao;
import com.poseidon.dto.CurvePointDto;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;

@DisplayName("CurvePointService CRUD operations tests")
@ExtendWith(MockitoExtension.class)
class CurvePointServiceTest {
	
	@Mock
	private CurvePointDao curvePointDao;
	
	@InjectMocks
	private CurvePointService curvePointService;

	private static CurvePointDto testedCurvePointDto;

	@BeforeAll
	static void setUp() {
		testedCurvePointDto = new CurvePointDto();
		testedCurvePointDto.setCurvePointId(1);
	}
	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void createTest() throws DuplicatedResourceException {
			when(curvePointDao.create(any(CurvePointDto.class))).thenReturn(testedCurvePointDto);
			assertEquals(testedCurvePointDto, curvePointService.create(testedCurvePointDto));
		}

		@Test
		void readTest() throws ResourceNotFoundException {
			when(curvePointDao.read(any(Integer.class))).thenReturn(testedCurvePointDto);
			assertEquals(testedCurvePointDto, curvePointService.read(1));
		}

		@Test
		void updateTest() throws ResourceNotFoundException {
			when(curvePointDao.update(any(Integer.class),any(CurvePointDto.class))).thenReturn(testedCurvePointDto);
			assertEquals(testedCurvePointDto, curvePointService.update(1,testedCurvePointDto));
		}

		@Test
		void deleteTest() throws ResourceNotFoundException {
			when(curvePointDao.delete(any(Integer.class),any(CurvePointDto.class))).thenReturn(testedCurvePointDto);
			assertEquals(testedCurvePointDto, curvePointService.delete(1,testedCurvePointDto));
		}

	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenCreatingAlreadyExistingCurvePointTest() throws DuplicatedResourceException {
			when(curvePointDao.create(testedCurvePointDto)).thenThrow(DuplicatedResourceException.class);
			assertThrows(DuplicatedResourceException.class, () -> curvePointService.create(testedCurvePointDto));
		}

		@Test
		void isExpectedExceptionThrownWhenTryingToFindUnexistingCurvePointTest() throws ResourceNotFoundException {
			when(curvePointDao.read(any(Integer.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> curvePointService.read(1));
		}

		@Test
		void isExpectedExceptionThrownWhenDeletingUnexistingCurvePointTest() throws ResourceNotFoundException {
			when(curvePointDao.delete(any(Integer.class), any(CurvePointDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> curvePointService.delete(1, testedCurvePointDto));
		}

		@Test
		void isExpectedExceptionThrownWhenDeletingCurvePointWithIncoherentIdTest() throws ResourceNotFoundException {
			when(curvePointDao.delete(any(Integer.class), any(CurvePointDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> curvePointService.delete(2, testedCurvePointDto));
		}

		@Test
		void isExpectedExceptionThrownWhenUpdatingUnexistingCurvePointTest() throws ResourceNotFoundException {
			when(curvePointDao.update(any(Integer.class), any(CurvePointDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> curvePointService.update(1, testedCurvePointDto));
		}

		@Test
		void isExpectedExceptionThrownWhenUpdatingCurvePointWithIncoherentIdTest() throws ResourceNotFoundException {
			when(curvePointDao.update(any(Integer.class), any(CurvePointDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> curvePointService.update(2, testedCurvePointDto));
		}
	}
}
