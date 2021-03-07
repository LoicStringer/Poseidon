package com.poseidon.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.poseidon.dto.BidDto;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.BidService;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class BidServiceTestIT {

	@Autowired
	private BidService bidService;
	
	@Test
	void test() throws ResourceNotFoundException, DuplicatedResourceException {
		BidDto bidDto = new BidDto();
		bidDto.setBidId(10);
		bidDto.setAccount("GamblingAccount");
		bidDto.setType("Gambling");
		bidService.create(bidDto);
		bidDto.setBidId(11);
		Exception ex = assertThrows(ResourceNotFoundException.class,()->bidService.delete(11, bidDto));
		assertEquals("Bid not found", ex.getMessage());
	}

}
