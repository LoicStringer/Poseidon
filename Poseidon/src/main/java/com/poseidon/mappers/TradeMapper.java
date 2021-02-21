package com.poseidon.mappers;

import org.mapstruct.Mapper;

import com.poseidon.entity.Trade;
import com.poseidon.dto.TradeDto;

@Mapper(componentModel = "spring")
public interface TradeMapper {

	TradeDto tradeToTradeDto (Trade trade);
	Trade tradeDtoToTrade (TradeDto tradeDto); 
}
