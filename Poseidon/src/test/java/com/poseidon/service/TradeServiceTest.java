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

import com.poseidon.dao.TradeDao;
import com.poseidon.dto.TradeDto;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;

@DisplayName("TradeService CRUD operations tests")
@ExtendWith(MockitoExtension.class)
class TradeServiceTest {
	
	@Mock
	private TradeDao tradedao;

	@InjectMocks
	private TradeService tradeService;

	private static TradeDto testedTradeDto;

	@BeforeAll
	static void setUp() {
		testedTradeDto = new TradeDto();
		testedTradeDto.setTradeId(1);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void createTest() throws DuplicatedResourceException {
			when(tradedao.create(any(TradeDto.class))).thenReturn(testedTradeDto);
			assertEquals(testedTradeDto, tradeService.create(testedTradeDto));
		}

		@Test
		void readTest() throws ResourceNotFoundException {
			when(tradedao.read(any(Integer.class))).thenReturn(testedTradeDto);
			assertEquals(testedTradeDto, tradeService.read(1));
		}

		@Test
		void updateTest() throws ResourceNotFoundException {
			when(tradedao.update(any(Integer.class),any(TradeDto.class))).thenReturn(testedTradeDto);
			assertEquals(testedTradeDto, tradeService.update(1,testedTradeDto));
		}

		@Test
		void deleteTest() throws ResourceNotFoundException {
			when(tradedao.delete(any(Integer.class),any(TradeDto.class))).thenReturn(testedTradeDto);
			assertEquals(testedTradeDto, tradeService.delete(1,testedTradeDto));
		}

	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenCreatingAlreadyExistingTradeTest() throws DuplicatedResourceException {
			when(tradedao.create(testedTradeDto)).thenThrow(DuplicatedResourceException.class);
			assertThrows(DuplicatedResourceException.class, () -> tradeService.create(testedTradeDto));
		}

		@Test
		void isExpectedExceptionThrownWhenTryingToFindUnexistingTradeTest() throws ResourceNotFoundException {
			when(tradedao.read(any(Integer.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> tradeService.read(1));
		}

		@Test
		void isExpectedExceptionThrownWhenDeletingUnexistingTradeTest() throws ResourceNotFoundException {
			when(tradedao.delete(any(Integer.class), any(TradeDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> tradeService.delete(1, testedTradeDto));
		}

		@Test
		void isExpectedExceptionThrownWhenDeletingBidWithIncoherentIdTest() throws ResourceNotFoundException {
			when(tradedao.delete(any(Integer.class), any(TradeDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> tradeService.delete(2, testedTradeDto));
		}

		@Test
		void isExpectedExceptionThrownWhenUpdatingUnexistingTradeTest() throws ResourceNotFoundException {
			when(tradedao.update(any(Integer.class), any(TradeDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> tradeService.update(1, testedTradeDto));
		}

		@Test
		void isExpectedExceptionThrownWhenUpdatingBidWithIncoherentIdTest() throws ResourceNotFoundException {
			when(tradedao.update(any(Integer.class), any(TradeDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> tradeService.update(2, testedTradeDto));
		}
	}
}
