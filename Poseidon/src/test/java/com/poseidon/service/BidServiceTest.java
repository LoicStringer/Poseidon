package com.poseidon.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dto.BidDto;
import com.poseidon.entity.Bid;
import com.poseidon.mapper.BidMapper;
import com.poseidon.repository.BidRepository;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {

	@Mock
	private BidRepository bidRepository;
	
	@Spy
	private BidMapper bidMapper;

	@InjectMocks
	private BidService bidService;

	private List<Bid> bidsList;
	private Bid bid;
	private Bid bid2;

	@BeforeEach
	void setUp() {
		bidsList = new ArrayList<Bid>();
		bid = new Bid();
		bid.setBidId(1);
		bid.setBid(10.00);
		bid2 = new Bid();
		bid.setBidId(2);
		bid.setBid(20.00);
		bidsList.add(bid);
		bidsList.add(bid2);
	}

	@Test
	void getAllBidsTest() {
		
		when(bidRepository.findAll()).thenReturn(bidsList);
		
		assertEquals(2, bidService.getAllBids().size());
		//assertEquals(10.00, bidService.getAllBids().get(0).getBid());
		
	}

}
