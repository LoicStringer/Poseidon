package com.poseidon.mappers;

import org.mapstruct.Mapper;

import com.poseidon.dto.BidDto;
import com.poseidon.entity.Bid;

@Mapper(componentModel = "spring")
public interface BidMapper {

	BidDto bidToBidDto (Bid bid);
	Bid bidDtoToBid (BidDto bidDto);
}
