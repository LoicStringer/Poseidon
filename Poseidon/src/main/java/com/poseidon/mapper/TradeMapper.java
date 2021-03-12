package com.poseidon.mapper;

import org.mapstruct.Mapper;

import com.poseidon.dto.TradeDto;
import com.poseidon.entity.Trade;

@Mapper(componentModel = "spring")
public interface TradeMapper  {

	TradeDto tradeToTradeDto (Trade trade);
	Trade tradeDtoToTrade (TradeDto tradeDto); 
}
