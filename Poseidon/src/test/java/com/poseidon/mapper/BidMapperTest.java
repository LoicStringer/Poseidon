package com.poseidon.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.poseidon.dto.BidDto;
import com.poseidon.entity.Bid;

public class BidMapperTest {

	private BidMapper bidMapper = Mappers.getMapper(BidMapper.class);
	
	@Test
	void bidDtoToBidTest() {
		BidDto bidDto = new BidDto();
		bidDto.setAccount("GamblingAccount");
		Bid bid = bidMapper.bidDtoToBid(bidDto);
		assertEquals("GamblingAccount",bid.getAccount());
	}
	
	@Test
	void bidToBidDto() {
		Bid bid = new Bid();
		bid.setAccount("GamblingAccount");
		BidDto bidDto = bidMapper.bidToBidDto(bid);
		assertEquals("GamblingAccount",bidDto.getAccount());
	}
}
