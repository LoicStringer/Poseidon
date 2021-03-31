package com.poseidon.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poseidon.dto.BidDto;
import com.poseidon.entity.Bid;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.BidMapper;
import com.poseidon.repository.BidRepository;

@Repository
public class BidDao implements IGenericCrudDao<BidDto,Integer>  {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BidMapper bidMapper;
	
	@Autowired
	private BidRepository bidRepository;
	
	@Override
	public List<BidDto> getAllList() {
		List<Bid> bidEntitiesList = bidRepository.findAll();
		List<BidDto> bidDtosList = bidEntitiesList.stream()
				.map(b->bidMapper.bidToBidDto(b))
				.collect(Collectors.toList());
		return bidDtosList;
	}

	@Override
	public BidDto create(BidDto bidToCreate) throws NotAllowedIdSettingException  {
		preventResourceIdBreach(bidToCreate);
		bidRepository.save(bidMapper.bidDtoToBid(bidToCreate));
		return bidToCreate;
	}

	@Override
	public BidDto read(Integer bidId) throws ResourceNotFoundException {
		Bid bidToRead = bidRepository.findById(bidId).orElseThrow(()-> new ResourceNotFoundException("Bid with id "+bidId+ " not found"));
		return bidMapper.bidToBidDto(bidToRead);
	}

	@Override
	public BidDto update(Integer bidId, BidDto updatedBid) throws ResourceNotFoundException  {
		checkResourceExistence(bidId);
		checkResourceIdCoherence(bidId, updatedBid.getBidId());
		bidRepository.save(bidMapper.bidDtoToBid(updatedBid));
		return updatedBid;
	}

	@Override
	public BidDto delete(Integer bidId, BidDto bidToDelete) throws ResourceNotFoundException {
		checkResourceExistence(bidId);
		checkResourceIdCoherence(bidId, bidToDelete.getBidId());
		bidRepository.deleteById(bidId);
		return bidToDelete;
	}
	
	private void preventResourceIdBreach(BidDto bidToCreate) throws NotAllowedIdSettingException {
		if(bidToCreate.getBidId()!=null)		
			log.error("Id has been set to this bid before insert.");
			throw new NotAllowedIdSettingException("Not allowed to set an id to resources.");
	}

	private void checkResourceExistence(Integer bidId) throws ResourceNotFoundException {
		if(!bidRepository.existsById(bidId))
			log.error("The bid's id number "+bidId+ "doesn't match any registered bid's id.");
			throw new ResourceNotFoundException("The bid with "+bidId+ " id number is not registered.");
	}
	
	private void checkResourceIdCoherence(Integer targetBidId, Integer treatedBidId) throws ResourceNotFoundException {
		if(!targetBidId.equals(treatedBidId))
			log.error("The uri's id is different from the bid's id currently handled");
			throw new ResourceNotFoundException("The requested bid's id "+targetBidId+ " is different from the currently handled bid's id.");
	}
}
