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

import com.poseidon.dto.RatingDto;
import com.poseidon.dto.TradeDto;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.repository.TradeRepository;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {
	@Mock
	private TradeRepository tradeRepository;

	@InjectMocks
	private TradeService tradeService;

	private static TradeDto testedTradeDto;

	@BeforeAll
	static void setUp() {
		testedTradeDto = new TradeDto();
		testedTradeDto.setTradeId(1);
	}

	@Test
	void isExpectedExceptionThrownWhenTryingToFindUnexistingTradeTest() {
		when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> tradeService.read(1));
	}

	@Test
	void isExpectedExceptionThrownWhenDeletingUnexistingTradeTest() {
		when(tradeRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> tradeService.delete(testedTradeDto));
	}

	@Test
	void isExpectedExceptionThrownWhenUpdatingUnexistingTradeTest() {
		when(tradeRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, () -> tradeService.update(testedTradeDto));
	}



}
