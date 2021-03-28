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
		return tradeDao.getAllList();
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

}
