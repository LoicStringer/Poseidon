package com.poseidon.utilities;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.repository.GenericRepository;

@ExtendWith(MockitoExtension.class)
class ResourceIdCheckerTest {

	@Mock
	private GenericRepository<Object,Object> repo;
	
	@InjectMocks
	private ResourceIdChecker<Object,Object> checker;
	
	@Test
	void isExpectedExceptionThrownWhenCheckingResourceIdBeforeCreateReturnsTrueTest() {
		when(repo.existsById(any(Integer.class))).thenReturn(true);
		assertThrows(DuplicatedResourceException.class,()->checker.checkIfResourceExistsBeforeCreate(any(Integer.class)));
	}

	@Test
	void isExpectedExceptionThrownWhenCheckingResourceIdBeforeReadReturnsFalseTest() {
		when(repo.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(ResourceNotFoundException.class,()->checker.checkIfResourceExistsBeforeRead(any(String.class),1));
	}
	
	@Test
	void isExpectedExceptionThrownWhenCheckingResourceIdCoherenceReturnsFalse() {
		when(repo.existsById(any(Integer.class))).thenReturn(true);
		assertThrows(ResourceNotFoundException.class,()->checker.checkIdCoherenceBeforeUpdateOrDelete(any(String.class), 1, 2));
	}
	
	@Test
	void isExpectedExceptionThrownWhenCheckingResourceIdExistenceBeforeUpdateOrDeleteReturnsFalse() {
		when(repo.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(ResourceNotFoundException.class,()->checker.checkIfResourceExistsBeforeUpdateOrDelete(any(String.class), 1, 1));
	}
	
	
}
