package com.poseidon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dto.TradeDto;
import com.poseidon.entity.Trade;
import com.poseidon.mapper.TradeMapper;
import com.poseidon.repository.TradeRepository;

@Service
public class TradeService {

	private TradeMapper tradeMapper = Mappers.getMapper(TradeMapper.class);
	
	@Autowired
	private TradeRepository tradeRepository;
	
	public List<TradeDto> getAllTrades(){
		List<Trade> trades = tradeRepository.findAll();
		List<TradeDto> tradesDto = trades.stream()
				.map(t->tradeMapper.tradeToTradeDto(t))
				.collect(Collectors.toList());
		return tradesDto;
	}
	
	public TradeDto create(TradeDto tradeToCreate) {
		tradeRepository.save(tradeMapper.tradeDtoToTrade(tradeToCreate));
		return tradeToCreate;
	}
	
	public TradeDto read(Integer id) {
		return tradeMapper.tradeToTradeDto(tradeRepository.findById(id).orElse(null));
	}
	
	public TradeDto update(TradeDto tradeToUpdate) {
		tradeRepository.save(tradeMapper.tradeDtoToTrade(tradeToUpdate));
		return tradeToUpdate;
	}
	
	public TradeDto delete(TradeDto tradeToDelete) {
		tradeRepository.delete(tradeMapper.tradeDtoToTrade(tradeToDelete));
		return tradeToDelete;
	}
	
}
