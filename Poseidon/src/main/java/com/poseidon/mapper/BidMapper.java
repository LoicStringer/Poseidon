package com.poseidon.mapper;

import org.mapstruct.Mapper;

import com.poseidon.dto.BidDto;
import com.poseidon.entity.Bid;
import com.poseidon.service.BidService;

@Mapper(componentModel = "spring")
public interface BidMapper {

	
	BidDto bidToBidDto (Bid bid);
	Bid bidDtoToBid (BidDto bidDto);
}
