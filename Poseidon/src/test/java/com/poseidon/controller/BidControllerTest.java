package com.poseidon.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.poseidon.dto.BidDto;
import com.poseidon.service.BidService;

@ExtendWith(MockitoExtension.class)
public class BidControllerTest {

	@Mock
	private BidService bidService;
	
	@InjectMocks
	private BidController bidController;
	
	@Test
	void getAllTest() {
		BidDto bidDto = new BidDto();
		bidDto.setAccount("GamblingAccount");
		bidDto.setType("Gambling");
		List<BidDto> bidDtosList = new ArrayList<BidDto>();
		when(bidService.getDtoList()).thenReturn(bidDtosList);
		assertEquals(ResponseEntity.ok(bidDtosList),bidController.getBidsList());
	}
	
}
