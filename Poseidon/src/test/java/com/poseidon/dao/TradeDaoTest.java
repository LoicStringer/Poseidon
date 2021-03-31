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

import com.poseidon.dto.TradeDto;
import com.poseidon.entity.Trade;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.TradeMapper;
import com.poseidon.repository.TradeRepository;

@DisplayName("TradeDao CRUD operations tests")
@ExtendWith(MockitoExtension.class)
public class TradeDaoTest {

	@Mock
	private TradeRepository tradeRepository;

	@Mock
	private TradeMapper tradeMapper;

	@InjectMocks
	private TradeDao tradeDao;

	private static Trade testedTrade;
	private static List<Trade> tradesList;
	private static TradeDto testedTradeDto;
	private static List<TradeDto> tradeDtosList;

	@BeforeAll
	static void setUp() {
		testedTrade = new Trade();
		testedTrade.setTradeId(1);
		tradesList = new ArrayList<Trade>();
		tradesList.add(testedTrade);
		testedTradeDto = new TradeDto();
		testedTradeDto.setTradeId(1);
		tradeDtosList = new ArrayList<TradeDto>();
		tradeDtosList.add(testedTradeDto);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getAllTest() {
			when(tradeMapper.tradeToTradeDto(testedTrade)).thenReturn(testedTradeDto);
			when(tradeRepository.findAll()).thenReturn(tradesList);
			assertEquals(tradeDtosList, tradeDao.getAllList());
		}
		@Test
		void createTest() throws NotAllowedIdSettingException {
			Trade tradeToCreate = new Trade();
			tradeToCreate.setAccount("GamblingAccount");
			TradeDto tradeDtoToCreate = new TradeDto(); 
			tradeDtoToCreate.setAccount("GamblingAccount");
			when(tradeMapper.tradeDtoToTrade(tradeDtoToCreate)).thenReturn(tradeToCreate);
			when(tradeRepository.save(any(Trade.class))).thenReturn(tradeToCreate);
			assertEquals(tradeDtoToCreate, tradeDao.create(tradeDtoToCreate));
		}
		
		@Test
		void readTest() throws ResourceNotFoundException {
			when(tradeMapper.tradeToTradeDto(testedTrade)).thenReturn(testedTradeDto);
			when(tradeRepository.findById(any(Integer.class))).thenReturn(Optional.of(testedTrade));
			assertEquals(testedTradeDto, tradeDao.read(1));
		}

		@Test
		void updateTest() throws ResourceNotFoundException {
			when(tradeMapper.tradeDtoToTrade(testedTradeDto)).thenReturn(testedTrade);
			when(tradeRepository.existsById(any(Integer.class))).thenReturn(true);
			when(tradeRepository.save(any(Trade.class))).thenReturn(testedTrade);
			assertEquals(testedTradeDto, tradeDao.update(1, testedTradeDto));
		}

		@Test
		void deleteTest() throws ResourceNotFoundException {
			when(tradeRepository.existsById(any(Integer.class))).thenReturn(true);
			assertEquals(testedTradeDto, tradeDao.delete(1, testedTradeDto));
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {
		
		@Test
		void isExpectedExceptionThrownWhenTradeIsNotFound() {
			assertThrows(ResourceNotFoundException.class, ()->tradeDao.read(2));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeCreate() {
			assertThrows(NotAllowedIdSettingException.class,()->tradeDao.create(testedTradeDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenRequestIdIsDifferentFromResourceId() {
			assertThrows(ResourceNotFoundException.class,()->tradeDao.update(2, testedTradeDto));
		}
	}
	
	
}
