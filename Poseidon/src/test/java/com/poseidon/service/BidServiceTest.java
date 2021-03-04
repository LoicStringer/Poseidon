package com.poseidon.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dto.BidDto;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.repository.BidRepository;

@ExtendWith(MockitoExtension.class)
class BidServiceTest {

	@Mock
	private BidRepository bidRepository;
	
	@InjectMocks
	private BidService bidService;
	
	private static BidDto testedBidDto;
	
	@BeforeAll
	static void setUp() {
		testedBidDto = new BidDto();
		testedBidDto.setBidId(1);
	}
	
	@Test
	void isExpectedExceptionThrownWhenTryingToFindUnexistingBidTest() {
		when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, ()-> bidService.read(1));
	}
	
	@Test
	void isExpectedExceptionThrownWhenDeletingUnexistingBidTest() {
		when(bidRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, ()-> bidService.delete(testedBidDto));
	}

	@Test
	void isExpectedExceptionThrownWhenUpdatingUnexistingBidTest() {
		when(bidRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(ResourceNotFoundException.class, ()-> bidService.update(testedBidDto));
	}
}
