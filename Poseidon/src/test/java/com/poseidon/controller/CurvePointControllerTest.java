package com.poseidon.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.poseidon.dto.CurvePointDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.CurvePointService;

@DisplayName("CurvePointController CRUD operations tests")
@ExtendWith(MockitoExtension.class)
public class CurvePointControllerTest {


	@Mock
	private CurvePointService curvePointService;

	@InjectMocks
	private CurvePointController curvePointController;

	private static CurvePointDto curvePointDto;
	private static List<CurvePointDto> curvePointDtosList;

	@BeforeAll
	static void setUp() {
		curvePointDto = new CurvePointDto();
		curvePointDto.setCurvePointId(1);
		curvePointDto.setValue(10.00);
		curvePointDtosList = new ArrayList<CurvePointDto>();
		curvePointDtosList.add(curvePointDto);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getCurvePointsListTest() {
			when(curvePointService.getDtoList()).thenReturn(curvePointDtosList);
			assertEquals(ResponseEntity.ok(curvePointDtosList), curvePointController.getCurvePointsList());
		}

		@Test
		void getOneCurvePointTest() throws ResourceNotFoundException {
			when(curvePointService.read(any(Integer.class))).thenReturn(curvePointDto);
			assertEquals(ResponseEntity.ok(curvePointDto), curvePointController.getOneCurvePoint(1));
		}

		@Test
		void addCurvePointTest() throws NotAllowedIdSettingException {
			when(curvePointService.create(any(CurvePointDto.class))).thenReturn(curvePointDto);
			assertEquals(ResponseEntity.ok(curvePointDto), curvePointController.addCurvePoint(curvePointDto));
		}

		@Test
		void updateCurvePointTest() throws ResourceNotFoundException {
			when(curvePointService.update(any(Integer.class), any(CurvePointDto.class))).thenReturn(curvePointDto);
			assertEquals(ResponseEntity.ok(curvePointDto), curvePointController.updateCurvePoint(1, curvePointDto));
		}

		@Test
		void deletecurvePointTest() throws ResourceNotFoundException {
			when(curvePointService.delete(any(Integer.class), any(CurvePointDto.class))).thenReturn(curvePointDto);
			assertEquals(ResponseEntity.ok(curvePointDto), curvePointController.deleteCurvePoint(1, curvePointDto));
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeAddingResourceTest() throws NotAllowedIdSettingException {
			when(curvePointService.create(any(CurvePointDto.class))).thenThrow(NotAllowedIdSettingException.class);
			assertThrows(NotAllowedIdSettingException.class,()->curvePointController.addCurvePoint(curvePointDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToGetUnexistingResourceTest() throws ResourceNotFoundException {
			when(curvePointService.read(any(Integer.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->curvePointController.getOneCurvePoint(2));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToUpdategResourceNotFoundTest() throws ResourceNotFoundException {
			when(curvePointService.update(any(Integer.class),any(CurvePointDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->curvePointController.updateCurvePoint(2,curvePointDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToDeleteResourceNotFoundTest() throws ResourceNotFoundException {
			when(curvePointService.delete(any(Integer.class),any(CurvePointDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->curvePointController.deleteCurvePoint(2,curvePointDto));
		}
		
	}
	
}
