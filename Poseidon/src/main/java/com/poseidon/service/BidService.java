package com.poseidon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dto.BidDto;
import com.poseidon.entity.Bid;
import com.poseidon.mappers.BidMapper;
import com.poseidon.repository.BidRepository;

@Service
public class BidService {

	private BidMapper bidMapper = Mappers.getMapper(BidMapper.class); 
	
	@Autowired
	private BidRepository bidRepository;
	
	public List<BidDto> getAllBids(){
		List<Bid> bids = bidRepository.findAll();
		List<BidDto> bidsDto = bids.stream()
				.map(b-> bidMapper.bidToBidDto(b))
				.collect(Collectors.toList());
		return bidsDto;
	}
	
	public BidDto create(BidDto bidToCreate) {
		bidRepository.save(bidMapper.bidDtoToBid(bidToCreate));
		return bidToCreate;
	}
	
	public BidDto read(Integer id) {
		return bidMapper.bidToBidDto(bidRepository.findById(id).orElse(null));
	}
	
	public BidDto update(BidDto bidToUpdate) {
		bidRepository.save(bidMapper.bidDtoToBid(bidToUpdate));
		return bidToUpdate;
	}
		
	public BidDto delete(BidDto bidToDelete) {
		bidRepository.delete(bidMapper.bidDtoToBid(bidToDelete));
		return bidToDelete;
	}
	
}
