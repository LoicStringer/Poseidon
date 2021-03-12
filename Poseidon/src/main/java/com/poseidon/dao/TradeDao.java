package com.poseidon.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.poseidon.dto.TradeDto;
import com.poseidon.entity.Trade;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.TradeMapper;
import com.poseidon.utilities.ResourceIdChecker;

@Repository
public class TradeDao extends GenericDao<Trade, Integer> implements IGenericDao<TradeDto,Integer>{

	@Autowired
	private ResourceIdChecker<Trade,Integer> resourceIdChecker;
	
	@Autowired
	private TradeMapper tradeMapper;
	
	@Value("Trade")
	private String resourceName;

	@Override
	public List<TradeDto> getAll() {
		List<Trade> tradeEntitiesList = getAllEntity();
		List<TradeDto> tradeDtosList = tradeEntitiesList.stream()
				.map(t->tradeMapper.tradeToTradeDto(t))
				.collect(Collectors.toList());
		return tradeDtosList;
	}

	@Override
	public TradeDto create(TradeDto entityToCreateAsDto) throws DuplicatedResourceException {
		resourceIdChecker.checkIfResourceExistsBeforeCreate(entityToCreateAsDto.getTradeId());
		createEntity(tradeMapper.tradeDtoToTrade(entityToCreateAsDto));
		return entityToCreateAsDto;
	}

	@Override
	public TradeDto read(Integer entityId) throws ResourceNotFoundException {
		resourceIdChecker.checkIfResourceExistsBeforeRead(resourceName, entityId);
		return tradeMapper.tradeToTradeDto(readEntity(entityId));
	}

	@Override
	public TradeDto update(Integer entityId, TradeDto entityToUpdateAsDto) throws ResourceNotFoundException {
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(resourceName, entityId, entityToUpdateAsDto.getTradeId());
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(resourceName, entityId,  entityToUpdateAsDto.getTradeId());
		updateEntity(entityId, tradeMapper.tradeDtoToTrade(entityToUpdateAsDto));
		return entityToUpdateAsDto;
	}

	@Override
	public TradeDto delete(Integer entityId, TradeDto entityToDeleteAsDto) throws ResourceNotFoundException {
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(resourceName, entityId, entityToDeleteAsDto.getTradeId());
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(resourceName, entityId,  entityToDeleteAsDto.getTradeId());
		deleteEntity(entityId, tradeMapper.tradeDtoToTrade(entityToDeleteAsDto));
		return entityToDeleteAsDto;
	}

	
	
}
