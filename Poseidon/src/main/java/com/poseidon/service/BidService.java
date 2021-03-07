package com.poseidon.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dto.BidDto;
import com.poseidon.entity.Bid;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.BidMapper;
import com.poseidon.repository.BidRepository;
import com.poseidon.utilities.ResourceIdChecker;

@Service
@Transactional(rollbackOn = Exception.class)
public class BidService {

	@Autowired
	private BidMapper bidMapper; 
	
	@Autowired
	private BidRepository bidRepository;
	
	@Autowired
	private ResourceIdChecker<Bid, Integer> checker ;
	
	public List<BidDto> getAllBids(){
		List<Bid> bidsList = bidRepository.findAll();
		List<BidDto> bidsDto = bidsList.stream()
				.map(b-> bidMapper.bidToBidDto(b))
				.collect(Collectors.toList());
		return bidsDto;
	}
	
	public BidDto create(BidDto bidToCreate) throws DuplicatedResourceException {
		checker.checkIfResourceExistsBeforeCreate(bidToCreate.getBidId());
		bidRepository.save(bidMapper.bidDtoToBid(bidToCreate));
		return bidToCreate;
	}
	
	public BidDto read(Integer id) throws ResourceNotFoundException {
		return bidMapper.bidToBidDto(bidRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Bid not found")));
	}
	
	public BidDto update(Integer resourceId, BidDto bidToUpdate) throws ResourceNotFoundException {
		Integer bidDtoId = bidToUpdate.getBidId();
		checker.checkIfResourceExistsBeforeTreatment(Bid.class, resourceId, bidDtoId);
		checker.checkIdEqualityBeforeTreatment(Bid.class, resourceId, bidDtoId);
		bidRepository.save(bidMapper.bidDtoToBid(bidToUpdate));
		return bidToUpdate;
	}
		
	public BidDto delete(Integer resourceId, BidDto bidToDelete) throws ResourceNotFoundException {
		Integer bidDtoId = bidToDelete.getBidId();
		checker.checkIfResourceExistsBeforeTreatment(Bid.class, resourceId, bidDtoId);
		checker.checkIdEqualityBeforeTreatment(Bid.class, resourceId, bidDtoId);
		bidRepository.delete(bidMapper.bidDtoToBid(bidToDelete));
		return bidToDelete;
	}
	
}
