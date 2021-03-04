package com.poseidon.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dto.CurvePointDto;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.repository.CurvePointRepository;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceTest {
	
	@Mock
	private CurvePointRepository curvePointRepository;

	@InjectMocks
	private CurvePointService curvePointService;

	private static CurvePointDto testedCurvePointDto;

	@BeforeAll
	static void setUp() {
		testedCurvePointDto = new CurvePointDto();
		testedCurvePointDto.setCurvePointId(1);
	}

	@Test
	void isExpectedExceptionThrownWhenTryingToFindUnexistingCurvePointTest() {
		when(curvePointRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> curvePointService.read(1));
	}

	@Test
	void isExpectedExceptionThrownWhenDeletingUnexistingCurvePointTest() {
		when(curvePointRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> curvePointService.delete(testedCurvePointDto));
	}

	@Test
	void isExpectedExceptionThrownWhenUpdatingUnexistingCurvePointTest() {
		when(curvePointRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> curvePointService.update(testedCurvePointDto));
	}

}
