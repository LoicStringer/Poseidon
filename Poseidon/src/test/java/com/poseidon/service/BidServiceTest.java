package com.poseidon.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.entity.Bid;
import com.poseidon.repository.BidRepository;

@ExtendWith(MockitoExtension.class)
public class BidServiceTest {

	@Mock
	private BidRepository bidRepository;

	@InjectMocks
	private BidService bidService;

	private List<Bid> bidsList;

	@BeforeEach
	void setUp() {
		bidsList = new ArrayList<Bid>();
		Bid bid = new Bid();
		bid.setBidId(1);
		bid.setBid(10.00);
		Bid bid2 = new Bid();
		bid.setBidId(2);
		bid.setBid(20.00);
		bidsList.add(bid2);
	}

	
	
}
