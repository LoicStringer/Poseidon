package com.poseidon.utilities;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.exception.DuplicatedUserException;
import com.poseidon.exception.UserNotFoundException;
import com.poseidon.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserIdCheckerTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserIdChecker checker;
	
	@Test
	void isExpectedExceptionThrownWhenCheckingResourceIdBeforeCreateReturnsTrueTest() {
		when(userRepository.existsById(any(Integer.class))).thenReturn(true);
		assertThrows(DuplicatedUserException.class,()->checker.checkIfResourceExistsBeforeCreate(any(Integer.class)));
	}

	@Test
	void isExpectedExceptionThrownWhenCheckingResourceIdBeforeReadReturnsFalseTest() {
		when(userRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(UserNotFoundException.class,()->checker.checkIfResourceExistsBeforeRead(any(String.class),1));
	}
	
	@Test
	void isExpectedExceptionThrownWhenCheckingResourceIdCoherenceReturnsFalse() {
		when(userRepository.existsById(any(Integer.class))).thenReturn(true);
		assertThrows(UserNotFoundException.class,()->checker.checkIdCoherenceBeforeUpdateOrDelete(any(String.class), 1, 2));
	}
	
	@Test
	void isExpectedExceptionThrownWhenCheckingResourceIdExistenceBeforeUpdateOrDeleteReturnsFalse() {
		when(userRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(UserNotFoundException.class,()->checker.checkIfResourceExistsBeforeUpdateOrDelete(any(String.class), 1, 1));
	}
	
	

}
