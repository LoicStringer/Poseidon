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

import com.poseidon.dto.TradeDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.TradeService;

@DisplayName("TradeController CRUD operations tests")
@ExtendWith(MockitoExtension.class)
public class TradeControllerTest {

	@Mock
	private TradeService tradeService;

	@InjectMocks
	private TradeController tradeController;

	private static TradeDto tradeDto;
	private static List<TradeDto> tradeDtosList;

	@BeforeAll
	static void setUp() {
		tradeDto = new TradeDto();
		tradeDto.setTradeId(1);
		tradeDto.setAccount("GamblingAccount");
		tradeDto.setType("Gambling");
		tradeDtosList = new ArrayList<TradeDto>();
		tradeDtosList.add(tradeDto);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getTradesListTest() {
			when(tradeService.getDtoList()).thenReturn(tradeDtosList);
			assertEquals(ResponseEntity.ok(tradeDtosList), tradeController.getTradesList());
		}

		@Test
		void getOneTradeTest() throws ResourceNotFoundException {
			when(tradeService.read(any(Integer.class))).thenReturn(tradeDto);
			assertEquals(ResponseEntity.ok(tradeDto), tradeController.getOneTrade(1));
		}

		@Test
		void addTradeTest() throws NotAllowedIdSettingException {
			when(tradeService.create(any(TradeDto.class))).thenReturn(tradeDto);
			assertEquals(ResponseEntity.ok(tradeDto), tradeController.addTrade(tradeDto));
		}

		@Test
		void updateTradeTest() throws ResourceNotFoundException {
			when(tradeService.update(any(Integer.class), any(TradeDto.class))).thenReturn(tradeDto);
			assertEquals(ResponseEntity.ok(tradeDto), tradeController.updateTrade(1, tradeDto));
		}

		@Test
		void deletetradeTest() throws ResourceNotFoundException {
			when(tradeService.delete(any(Integer.class), any(TradeDto.class))).thenReturn(tradeDto);
			assertEquals(ResponseEntity.ok(tradeDto), tradeController.deleteTrade(1, tradeDto));
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeAddingResourceTest() throws NotAllowedIdSettingException {
			when(tradeService.create(any(TradeDto.class))).thenThrow(NotAllowedIdSettingException.class);
			assertThrows(NotAllowedIdSettingException.class,()->tradeController.addTrade(tradeDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToGetUnexistingResourceTest() throws ResourceNotFoundException {
			when(tradeService.read(any(Integer.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->tradeController.getOneTrade(2));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToUpdategResourceNotFoundTest() throws ResourceNotFoundException {
			when(tradeService.update(any(Integer.class),any(TradeDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->tradeController.updateTrade(2,tradeDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToDeleteResourceNotFoundTest() throws ResourceNotFoundException {
			when(tradeService.delete(any(Integer.class),any(TradeDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->tradeController.deleteTrade(2,tradeDto));
		}
		
	}
	
}
