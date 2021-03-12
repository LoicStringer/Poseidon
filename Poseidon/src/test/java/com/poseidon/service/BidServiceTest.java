package com.poseidon.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dao.BidDao;
import com.poseidon.dto.BidDto;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;

@DisplayName("BidService CRUD operations tests")
@ExtendWith(MockitoExtension.class)
class BidServiceTest {

	@Mock
	private BidDao bidDao;

	@InjectMocks
	private BidService bidService;

	private static BidDto testedBidDto;

	@BeforeAll
	static void setUp() {
		testedBidDto = new BidDto();
		testedBidDto.setBidId(1);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void createTest() throws DuplicatedResourceException {
			when(bidDao.create(any(BidDto.class))).thenReturn(testedBidDto);
			assertEquals(testedBidDto, bidService.create(testedBidDto));
		}

		@Test
		void readTest() throws ResourceNotFoundException {
			when(bidDao.read(any(Integer.class))).thenReturn(testedBidDto);
			assertEquals(testedBidDto, bidService.read(1));
		}

		@Test
		void updateTest() throws ResourceNotFoundException {
			when(bidDao.update(any(Integer.class),any(BidDto.class))).thenReturn(testedBidDto);
			assertEquals(testedBidDto, bidService.update(1,testedBidDto));
		}

		@Test
		void deleteTest() throws ResourceNotFoundException {
			when(bidDao.delete(any(Integer.class),any(BidDto.class))).thenReturn(testedBidDto);
			assertEquals(testedBidDto, bidService.delete(1,testedBidDto));
		}

	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenCreatingAlreadyExistingBidTest() throws DuplicatedResourceException {
			when(bidDao.create(testedBidDto)).thenThrow(DuplicatedResourceException.class);
			assertThrows(DuplicatedResourceException.class, () -> bidService.create(testedBidDto));
		}

		@Test
		void isExpectedExceptionThrownWhenTryingToFindUnexistingBidTest() throws ResourceNotFoundException {
			when(bidDao.read(any(Integer.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> bidService.read(1));
		}

		@Test
		void isExpectedExceptionThrownWhenDeletingUnexistingBidTest() throws ResourceNotFoundException {
			when(bidDao.delete(any(Integer.class), any(BidDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> bidService.delete(1, testedBidDto));
		}

		@Test
		void isExpectedExceptionThrownWhenDeletingBidWithIncoherentIdTest() throws ResourceNotFoundException {
			when(bidDao.delete(any(Integer.class), any(BidDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> bidService.delete(2, testedBidDto));
		}

		@Test
		void isExpectedExceptionThrownWhenUpdatingUnexistingBidTest() throws ResourceNotFoundException {
			when(bidDao.update(any(Integer.class), any(BidDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> bidService.update(1, testedBidDto));
		}

		@Test
		void isExpectedExceptionThrownWhenUpdatingBidWithIncoherentIdTest() throws ResourceNotFoundException {
			when(bidDao.update(any(Integer.class), any(BidDto.class))).thenThrow(ResourceNotFoundException.class);
			assertThrows(ResourceNotFoundException.class, () -> bidService.update(2, testedBidDto));
		}
	}
}
