package com.poseidon.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.poseidon.dto.BidDto;
import com.poseidon.entity.Bid;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.BidMapper;
import com.poseidon.utilities.ResourceIdChecker;

@Repository
public class BidDao extends GenericDao<Bid,Integer> implements IGenericDao<BidDto,Integer>{

	@Autowired
	private ResourceIdChecker<Bid,Integer> resourceIdChecker;
	
	@Autowired
	private BidMapper bidMapper;
	
	@Value("Bid")
	private String resourceName;

	@Override
	public List<BidDto> getAll() {
		List<Bid> bidEntitiesList = getAllEntity();
		List<BidDto> bidDtosList = bidEntitiesList.stream()
				.map(b->bidMapper.bidToBidDto(b))
				.collect(Collectors.toList());
		return bidDtosList;
	}

	@Override
	public BidDto create(BidDto entityToCreateAsDto) throws DuplicatedResourceException {
		resourceIdChecker.checkIfResourceExistsBeforeCreate(entityToCreateAsDto.getBidId());
		createEntity(bidMapper.bidDtoToBid(entityToCreateAsDto));
		return entityToCreateAsDto;
	}

	@Override
	public BidDto read(Integer entityId) throws ResourceNotFoundException {
		resourceIdChecker.checkIfResourceExistsBeforeRead(resourceName, entityId);
		return bidMapper.bidToBidDto(readEntity(entityId));
	}

	@Override
	public BidDto update(Integer entityId, BidDto entityToUpdateAsDto) throws ResourceNotFoundException {
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(resourceName, entityId, entityToUpdateAsDto.getBidId());
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(resourceName, entityId,  entityToUpdateAsDto.getBidId());
		updateEntity(entityId, bidMapper.bidDtoToBid(entityToUpdateAsDto));
		return entityToUpdateAsDto;
	}

	@Override
	public BidDto delete(Integer entityId, BidDto entityToDeleteAsDto) throws ResourceNotFoundException {
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete(resourceName, entityId, entityToDeleteAsDto.getBidId());
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(resourceName, entityId,  entityToDeleteAsDto.getBidId());
		deleteEntity(entityId, bidMapper.bidDtoToBid(entityToDeleteAsDto));
		return entityToDeleteAsDto;
	}

	
	
}
