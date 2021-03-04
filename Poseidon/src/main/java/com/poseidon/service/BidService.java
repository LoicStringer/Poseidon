package com.poseidon.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dto.BidDto;
import com.poseidon.entity.Bid;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.BidMapper;
import com.poseidon.repository.BidRepository;

@Service
@Transactional(rollbackOn = Exception.class)
public class BidService {

	@Autowired
	private BidMapper bidMapper; 
	
	@Autowired
	private BidRepository bidRepository;
	
	public List<BidDto> getAllBids(){
		List<Bid> bidsList = bidRepository.findAll();
		List<BidDto> bidsDto = bidsList.stream()
				.map(b-> bidMapper.bidToBidDto(b))
				.collect(Collectors.toList());
		return bidsDto;
	}
	
	public BidDto create(BidDto bidToCreate) {
		bidRepository.save(bidMapper.bidDtoToBid(bidToCreate));
		return bidToCreate;
	}
	
	public BidDto read(Integer id) throws ResourceNotFoundException {
		return bidMapper.bidToBidDto(bidRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Bid not found")));
	}
	
	public BidDto update(BidDto bidToUpdate) throws ResourceNotFoundException {
		if(!bidRepository.existsById(bidToUpdate.getBidId()))
			throw new ResourceNotFoundException("Bid not found");
		bidRepository.save(bidMapper.bidDtoToBid(bidToUpdate));
		return bidToUpdate;
	}
		
	public BidDto delete(BidDto bidToDelete) throws ResourceNotFoundException {
		if(!bidRepository.existsById(bidToDelete.getBidId()))
			throw new ResourceNotFoundException("Bid not found");
		bidRepository.delete(bidMapper.bidDtoToBid(bidToDelete));
		return bidToDelete;
	}
	
}
