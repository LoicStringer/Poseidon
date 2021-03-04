package com.poseidon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.poseidon.dto.BidDto;
import com.poseidon.entity.Bid;

@Mapper(componentModel = "spring")
public interface BidMapper {

	//BidMapper bidMapper = Mappers.getMapper(BidMapper.class);
	
	BidDto bidToBidDto (Bid bid);
	Bid bidDtoToBid (BidDto bidDto);
}
