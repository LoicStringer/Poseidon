package com.poseidon.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dao.BidDao;
import com.poseidon.dto.BidDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;

@Service
@Transactional(rollbackOn = Exception.class)
public class BidService implements IGenericService<BidDto,Integer>{
	
	@Autowired
	private BidDao bidDao;

	@Override
	public List<BidDto> getDtoList() {
		return bidDao.getAllList();
	}

	@Override
	public BidDto create(BidDto dtoToCreate) throws NotAllowedIdSettingException {
		return bidDao.create(dtoToCreate);
	}

	@Override
	public BidDto read(Integer dtoId) throws ResourceNotFoundException {
		return bidDao.read(dtoId);
	}

	@Override
	public BidDto update(Integer dtoId, BidDto dtoToUpdate) throws ResourceNotFoundException {
		return bidDao.update(dtoId, dtoToUpdate);
	}

	@Override
	public BidDto delete(Integer dtoId, BidDto dtoToDelete) throws ResourceNotFoundException {
		return bidDao.delete(dtoId, dtoToDelete);
	}

}
