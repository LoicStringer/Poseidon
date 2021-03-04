package com.poseidon.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.poseidon.dto.UserDto;
import com.poseidon.entity.User;
import com.poseidon.exception.DuplicateUserException;
import com.poseidon.exception.UserNotFoundException;
import com.poseidon.mapper.UserMapper;
import com.poseidon.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Spy
	private UserMapper userMapper;
	
	@InjectMocks
	private UserService userService;
	
	private static UserDto testedUserDto;
	
	@BeforeAll
	static void setup() {
		testedUserDto = new UserDto();
		testedUserDto.setUserName("Tony");
		testedUserDto.setUserId(1);
	}
	
	@Test
	void isExpectedExceptionThrownWhenDuplicatingUserTest() {
		User user = new User();
		when(userRepository.findByUserName(any(String.class))).thenReturn(Optional.of(user));
		assertThrows(DuplicateUserException.class, ()-> userService.create(testedUserDto));
	}

	@Test
	void isExpectedExceptionThrownWhenUpdatingUnexistingUserTest() {
		when(userRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(UserNotFoundException.class, ()-> userService.update(testedUserDto));
	}
	
	@Test
	void isExpectedExceptionThrownWhenDeletingUnexistingUserTest() {
		when(userRepository.existsById(any(Integer.class))).thenReturn(false);
		assertThrows(UserNotFoundException.class, ()-> userService.delete(testedUserDto));
	}
	
}
