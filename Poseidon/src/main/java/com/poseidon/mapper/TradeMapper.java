package com.poseidon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.poseidon.entity.Trade;
import com.poseidon.dto.TradeDto;

@Mapper(componentModel = "spring")
public interface TradeMapper {

	TradeMapper tradeMapper = Mappers.getMapper(TradeMapper.class);
	
	TradeDto tradeToTradeDto (Trade trade);
	Trade tradeDtoToTrade (TradeDto tradeDto); 
}
