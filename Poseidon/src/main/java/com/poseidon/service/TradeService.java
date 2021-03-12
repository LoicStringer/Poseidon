package com.poseidon.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dao.TradeDao;
import com.poseidon.dto.TradeDto;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;

@Service
@Transactional(rollbackOn = Exception.class)
public class TradeService implements IGenericService<TradeDto,Integer>{

	@Autowired
	private TradeDao tradeDao;
	
	@Override
	public List<TradeDto> getDtoList() {
		return tradeDao.getAll();
	}

	@Override
	public TradeDto create(TradeDto dtoToCreate) throws DuplicatedResourceException {
		return tradeDao.create(dtoToCreate);
	}

	@Override
	public TradeDto read(Integer dtoId) throws ResourceNotFoundException {
		return tradeDao.read(dtoId);
	}

	@Override
	public TradeDto update(Integer dtoId, TradeDto dtoToUpdate) throws ResourceNotFoundException {
		return tradeDao.update(dtoId, dtoToUpdate);
	}

	@Override
	public TradeDto delete(Integer dtoId, TradeDto dtoToDelete) throws ResourceNotFoundException {
		return tradeDao.delete(dtoId, dtoToDelete);
	}

	/*
	@Autowired
	private TradeMapper tradeMapper ;
	
	@Autowired
	private TradeRepository tradeRepository;
	
	@Autowired
	private ResourceIdChecker<Trade, Integer> resourceIdChecker ;
	
	public List<TradeDto> getAllTrades(){
		List<Trade> trades = tradeRepository.findAll();
		List<TradeDto> tradesDto = trades.stream()
				.map(t->tradeMapper.tradeToTradeDto(t))
				.collect(Collectors.toList());
		return tradesDto;
	}
	
	public TradeDto create(TradeDto tradeToCreate) throws DuplicatedResourceException {
		resourceIdChecker.checkIfResourceExistsBeforeCreate(tradeToCreate.getTradeId());
		tradeRepository.save(tradeMapper.tradeDtoToTrade(tradeToCreate));
		return tradeToCreate;
	}
	
	public TradeDto read(Integer id) throws ResourceNotFoundException {
		resourceIdChecker.checkIfResourceExistsBeforeRead(Trade.class, id);
		return tradeMapper.tradeToTradeDto(tradeRepository.findById(id).get());
	}
	
	public TradeDto update(Integer resourceId,TradeDto tradeToUpdate) throws ResourceNotFoundException {
		Integer tradeToUpdateId = tradeToUpdate.getTradeId();
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(Trade.class, resourceId, tradeToUpdateId);
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(Trade.class, resourceId, tradeToUpdateId);
		tradeRepository.save(tradeMapper.tradeDtoToTrade(tradeToUpdate));
		return tradeToUpdate;
	}
	
	public TradeDto delete(Integer resourceId, TradeDto tradeToDelete) throws ResourceNotFoundException {
		Integer tradeToDeleteId = tradeToDelete.getTradeId();
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(Trade.class, resourceId, tradeToDeleteId);
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(Trade.class, resourceId, tradeToDeleteId);
		tradeRepository.delete(tradeMapper.tradeDtoToTrade(tradeToDelete));
		return tradeToDelete;
	}
	*/
	
}
