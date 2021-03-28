package com.poseidon.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dao.RuleDao;
import com.poseidon.dto.RuleDto;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;

@Service
@Transactional(rollbackOn = Exception.class)
public class RuleService implements IGenericService<RuleDto,Integer>{

	@Autowired
	private RuleDao ruleDao;
	
	@Override
	public List<RuleDto> getDtoList() {
		return ruleDao.getAllList();
	}

	@Override
	public RuleDto create(RuleDto dtoToCreate) throws DuplicatedResourceException {
		return ruleDao.create(dtoToCreate);
	}

	@Override
	public RuleDto read(Integer dtoId) throws ResourceNotFoundException {
		return ruleDao.read(dtoId);
	}

	@Override
	public RuleDto update(Integer dtoId, RuleDto dtoToUpdate) throws ResourceNotFoundException {
		return ruleDao.update(dtoId, dtoToUpdate);
	}

	@Override
	public RuleDto delete(Integer dtoId, RuleDto dtoToDelete) throws ResourceNotFoundException {
		return ruleDao.delete(dtoId, dtoToDelete);
	}

}
