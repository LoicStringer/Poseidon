package com.poseidon.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poseidon.dto.TradeDto;
import com.poseidon.entity.Trade;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.TradeMapper;
import com.poseidon.repository.TradeRepository;

@Repository
public class TradeDao implements IGenericCrudDao<TradeDto,Integer>  {

	@Autowired
	private TradeMapper tradeMapper;
	
	@Autowired
	private TradeRepository tradeRepository;
	
	public List<TradeDto> getAllList() {
		List<Trade> tradeEntitiesList = tradeRepository.findAll();
		List<TradeDto> tradeDtosList = tradeEntitiesList.stream()
				.map(t->tradeMapper.tradeToTradeDto(t))
				.collect(Collectors.toList());
		return tradeDtosList;
	}

	@Override
	public TradeDto create(TradeDto tradeToCreate) throws DuplicatedResourceException {
		preventResourceIdBreach(tradeToCreate.getTradeId());
		tradeRepository.save(tradeMapper.tradeDtoToTrade(tradeToCreate));
		return tradeToCreate;
	}

	@Override
	public TradeDto read(Integer tradeId) throws ResourceNotFoundException {
		Trade tradeToRead = tradeRepository.findById(tradeId).orElseThrow(()->new ResourceNotFoundException("Trade with id "+tradeId+ " not found"));
		return tradeMapper.tradeToTradeDto(tradeToRead);
	}

	@Override
	public TradeDto update(Integer tradeId, TradeDto updatedTrade) throws ResourceNotFoundException {
		checkResourceExistence(tradeId);
		checkResourceIdCoherence(tradeId, updatedTrade.getTradeId());
		tradeRepository.save(tradeMapper.tradeDtoToTrade(updatedTrade));
		return updatedTrade;
	}

	@Override
	public TradeDto delete(Integer tradeId, TradeDto tradeToDelete) throws ResourceNotFoundException {
		checkResourceExistence(tradeId);
		checkResourceIdCoherence(tradeId, tradeToDelete.getTradeId());
		tradeRepository.delete(tradeMapper.tradeDtoToTrade(tradeToDelete));
		return tradeToDelete;
	}

	private void preventResourceIdBreach(Integer tradeId) throws DuplicatedResourceException {
		if(tradeRepository.existsById(tradeId))
			throw new DuplicatedResourceException("Not allowed to set an id to resources.");
	}

	private void checkResourceExistence(Integer tradeId) throws ResourceNotFoundException {
		if(!tradeRepository.existsById(tradeId))
			throw new ResourceNotFoundException("The trade with "+tradeId+ " id number is not registered.");
	}
	
	private void checkResourceIdCoherence(Integer targetTradeId, Integer treatedTradedId) throws ResourceNotFoundException {
		if(!targetTradeId.equals(treatedTradedId))
			throw new ResourceNotFoundException("The requested trade's id "+targetTradeId+ " is different from the currently handled trade's id.");
	}
	
}
