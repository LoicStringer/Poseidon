package com.poseidon.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.poseidon.dto.TradeDto;
import com.poseidon.entity.Trade;

public class TradeMapperTest {
	
	private TradeMapper tradeMapper = Mappers.getMapper(TradeMapper.class);
	
	@Test
	void tradeDtoToTrade() {
		TradeDto tradeDto = new TradeDto();
		tradeDto.setDealName("Narcotics");
		Trade trade = tradeMapper.tradeDtoToTrade(tradeDto);
		assertEquals("Narcotics",trade.getDealName());
	}

	@Test
	void tradeToTradeDto() {
		Trade trade = new Trade();
		trade.setDealName("Narcotics");
		TradeDto tradeDto = tradeMapper.tradeToTradeDto(trade);
		assertEquals("Narcotics",tradeDto.getDealName());
	}

}
