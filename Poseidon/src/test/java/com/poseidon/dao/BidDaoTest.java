package com.poseidon.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dto.BidDto;
import com.poseidon.entity.Bid;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.mapper.BidMapper;
import com.poseidon.repository.BidRepository;

@DisplayName("BidDao CRUD operations tests")
@ExtendWith(MockitoExtension.class)
public class BidDaoTest {

	@Mock
	private BidRepository bidRepository;

	@Mock
	private BidMapper bidMapper;

	@InjectMocks
	private BidDao bidDao;

	private static Bid testedBid;
	private static List<Bid> bidsList;
	private static BidDto testedBidDto;
	private static List<BidDto> bidDtosList;

	@BeforeAll
	static void setUp() {
		testedBid = new Bid();
		testedBid.setBidId(1);
		bidsList = new ArrayList<Bid>();
		bidsList.add(testedBid);
		testedBidDto = new BidDto();
		testedBidDto.setBidId(1);
		bidDtosList = new ArrayList<BidDto>();
		bidDtosList.add(testedBidDto);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getAllTest() {
			when(bidMapper.bidToBidDto(testedBid)).thenReturn(testedBidDto);
			when(bidRepository.findAll()).thenReturn(bidsList);
			assertEquals(bidDtosList, bidDao.getAllList());
		}

		@Test
		void createTest() throws DuplicatedResourceException {
			when(bidMapper.bidDtoToBid(testedBidDto)).thenReturn(testedBid);
			when(bidRepository.save(any(Bid.class))).thenReturn(testedBid);
			assertEquals(testedBidDto, bidDao.create(testedBidDto));
		}

		@Test
		void readTest() throws ResourceNotFoundException {
			when(bidMapper.bidToBidDto(testedBid)).thenReturn(testedBidDto);
			when(bidRepository.findById(any(Integer.class))).thenReturn(Optional.of(testedBid));
			assertEquals(testedBidDto, bidDao.read(1));
		}

		@Test
		void updateTest() throws ResourceNotFoundException {
			when(bidMapper.bidDtoToBid(testedBidDto)).thenReturn(testedBid);
			when(bidRepository.existsById(any(Integer.class))).thenReturn(true);
			when(bidRepository.save(any(Bid.class))).thenReturn(testedBid);
			assertEquals(testedBidDto, bidDao.update(1, testedBidDto));
		}

		@Test
		void deleteTest() throws ResourceNotFoundException {
			when(bidRepository.existsById(any(Integer.class))).thenReturn(true);
			assertEquals(testedBidDto, bidDao.delete(1, testedBidDto));
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {
		
		@Test
		void isExpectedExceptionThrownWhenBidIsNotFound() {
			assertThrows(ResourceNotFoundException.class, ()->bidDao.read(2));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeCreate() {
			when(bidRepository.existsById(any(Integer.class))).thenReturn(true);
			assertThrows(DuplicatedResourceException.class,()->bidDao.create(testedBidDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenRequestIdIsDifferentFromResourceId() {
			assertThrows(ResourceNotFoundException.class,()->bidDao.update(2, testedBidDto));
		}
	}
	
}
