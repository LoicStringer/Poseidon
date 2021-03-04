package com.poseidon.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dto.TradeDto;
import com.poseidon.entity.Trade;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.TradeMapper;
import com.poseidon.repository.TradeRepository;

@Service
@Transactional(rollbackOn = Exception.class)
public class TradeService {

	@Autowired
	private TradeMapper tradeMapper ;
	
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
	
	public TradeDto read(Integer id) throws ResourceNotFoundException {
		return tradeMapper.tradeToTradeDto(tradeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Trade not found")));
	}
	
	public TradeDto update(TradeDto tradeToUpdate) throws ResourceNotFoundException {
		if(!tradeRepository.existsById(tradeToUpdate.getTradeId()))
			throw new ResourceNotFoundException("Trade not found");
		tradeRepository.save(tradeMapper.tradeDtoToTrade(tradeToUpdate));
		return tradeToUpdate;
	}
	
	public TradeDto delete(TradeDto tradeToDelete) throws ResourceNotFoundException {
		if(!tradeRepository.existsById(tradeToDelete.getTradeId()))
			throw new ResourceNotFoundException("Trade not found");
		tradeRepository.delete(tradeMapper.tradeDtoToTrade(tradeToDelete));
		return tradeToDelete;
	}
	
}
