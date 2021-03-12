package com.poseidon.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dao.BidDao;
import com.poseidon.dto.BidDto;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;

@Service
@Transactional(rollbackOn = Exception.class)
public class BidService implements IGenericService<BidDto,Integer>{
	
	@Autowired
	private BidDao bidDao;

	@Override
	public List<BidDto> getDtoList() {
		return bidDao.getAll();
	}

	@Override
	public BidDto create(BidDto dtoToCreate) throws DuplicatedResourceException {
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

/*
	@Autowired
	private BidMapper bidMapper; 
	
	@Autowired
	private BidDao bidRepository;
	
	@Autowired
	private ResourceIdChecker<Bid, Integer> resourceIdChecker ;
	
	public List<BidDto> getAllBids(){
		
		List<Bid> bidsList = bidRepository.findAll();
		List<BidDto> bidsDto = bidsList.stream()
				.map(b-> bidMapper.bidToBidDto(b))
				.collect(Collectors.toList());
		
		return bidRepository.getBidsList();
	}
	
	public BidDto createBid (BidDto bidToCreate) throws DuplicatedResourceException {
		
		resourceIdChecker.checkIfResourceExistsBeforeCreate(bidToCreate.getBidId());
		
		bidRepository.save(bidMapper.bidDtoToBid(bidToCreate));
		return bidToCreate;
		
		return bidRepository.createBid(bidToCreate);
	}
	
	public BidDto read(Integer id) throws ResourceNotFoundException {
		
		resourceIdChecker.checkIfResourceExistsBeforeRead("Bid", id);
		return bidMapper.bidToBidDto(bidRepository.findById(id).get());
		
		return bidRepository.readBid(id);
	}
	
	public BidDto update(Integer resourceId, BidDto bidToUpdate) throws ResourceNotFoundException {
		/*
		Integer bidToUpdateId = bidToUpdate.getBidId();
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete("Bid", resourceId, bidToUpdateId);
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete("Bid", resourceId, bidToUpdateId);
		bidRepository.save(bidMapper.bidDtoToBid(bidToUpdate));
		return bidToUpdate;
		
		return bidRepository.updateBid(resourceId, bidToUpdate);
	}
		
	public BidDto delete(Integer resourceId, BidDto bidToDelete) throws ResourceNotFoundException {
		
		Integer bidToDeleteId = bidToDelete.getBidId();
		resourceIdChecker.checkIfResourceExistsBeforeUpdateOrDelete("Bid", resourceId, bidToDeleteId);
		resourceIdChecker.checkIdCoherenceBeforeUpdateOrDelete("Bid", resourceId, bidToDeleteId);
		bidRepository.delete(bidMapper.bidDtoToBid(bidToDelete));
		return bidToDelete;
		
		return bidRepository.deleteBid(resourceId, bidToDelete);
	}
*/

}
