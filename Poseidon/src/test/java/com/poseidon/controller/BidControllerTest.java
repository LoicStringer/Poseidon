package com.poseidon.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.poseidon.dto.BidDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.BidService;

@DisplayName("BidController CRUD operations tests")
@ExtendWith(MockitoExtension.class)
public class BidControllerTest {

	@Mock
	private BidService bidService;

	@InjectMocks
	private BidController bidController;

	private static BidDto bidDto;
	private static List<BidDto> bidDtosList;

	@BeforeAll
	static void setUp() {
		bidDto = new BidDto();
		bidDto.setBidId(1);
		bidDto.setAccount("GamblingAccount");
		bidDto.setType("Gambling");
		bidDtosList = new ArrayList<BidDto>();
		bidDtosList.add(bidDto);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getBidsListTest() {
			when(bidService.getDtoList()).thenReturn(bidDtosList);
			assertEquals(ResponseEntity.ok(bidDtosList), bidController.getBidsList());
		}

		@Test
		void getOneBidTest() throws ResourceNotFoundException {
			when(bidService.read(any(Integer.class))).thenReturn(bidDto);
			assertEquals(ResponseEntity.ok(bidDto), bidController.getOneBid(1));
		}

		@Test
		void addBidTest() throws NotAllowedIdSettingException {
			when(bidService.create(any(BidDto.class))).thenReturn(bidDto);
			assertEquals(ResponseEntity.ok(bidDto), bidController.addBid(bidDto));
		}

		@Test
		void updateBidTest() throws ResourceNotFoundException {
			when(bidService.update(any(Integer.class), any(BidDto.class))).thenReturn(bidDto);
			assertEquals(ResponseEntity.ok(bidDto), bidController.updateBid(1, bidDto));
		}

		@Test
		void deletebidTest() throws ResourceNotFoundException {
			when(bidService.delete(any(Integer.class), any(BidDto.class))).thenReturn(bidDto);
			assertEquals(ResponseEntity.ok(bidDto), bidController.deleteBid(1, bidDto));
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeAddingResourceTest() throws NotAllowedIdSettingException {
			when(bidService.create(any(BidDto.class))).thenThrow(NotAllowedIdSettingException.class);
			assertThrows(NotAllowedIdSettingException.class,()->bidController.addBid(bidDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToGetUnexistingResourceTest() throws ResourceNotFoundException {
			when(bidService.read(any(Integer.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->bidController.getOneBid(2));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToUpdategResourceNotFoundTest() throws ResourceNotFoundException {
			when(bidService.update(any(Integer.class),any(BidDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->bidController.updateBid(2,bidDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToDeleteResourceNotFoundTest() throws ResourceNotFoundException {
			when(bidService.delete(any(Integer.class),any(BidDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class,()->bidController.deleteBid(2,bidDto));
		}
		
	}
}
