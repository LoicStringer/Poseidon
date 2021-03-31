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

import com.poseidon.dto.UserDto;
import com.poseidon.exception.DuplicatedUserException;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.UserNotFoundException;
import com.poseidon.service.UserService;

@DisplayName("UserController CRUD operations tests")
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	private static UserDto userDto;
	private static List<UserDto> userDtosList;

	@BeforeAll
	static void setUp() {
		userDto = new UserDto();
		userDto.setUserId(1);
		userDto.setUserName("Tony");
		userDtosList = new ArrayList<UserDto>();
		userDtosList.add(userDto);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getUsersListTest() {
			when(userService.getDtoList()).thenReturn(userDtosList);
			assertEquals(ResponseEntity.ok(userDtosList), userController.getUsersList());
		}

		@Test
		void getOneUserTest() throws UserNotFoundException {
			when(userService.read(any(Integer.class))).thenReturn(userDto);
			assertEquals(ResponseEntity.ok(userDto), userController.getOneUser(1));
		}

		@Test
		void addUserTest() throws DuplicatedUserException, NotAllowedIdSettingException {
			when(userService.create(any(UserDto.class))).thenReturn(userDto);
			assertEquals(ResponseEntity.ok(userDto), userController.addUser(userDto));
		}

		@Test
		void updateUserTest() throws UserNotFoundException {
			when(userService.update(any(Integer.class), any(UserDto.class))).thenReturn(userDto);
			assertEquals(ResponseEntity.ok(userDto), userController.updateUser(1, userDto));
		}

		@Test
		void deleteuserTest() throws UserNotFoundException {
			when(userService.delete(any(Integer.class), any(UserDto.class))).thenReturn(userDto);
			assertEquals(ResponseEntity.ok(userDto), userController.deleteUser(1, userDto));
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeAddingUserTest() throws DuplicatedUserException, NotAllowedIdSettingException {
			when(userService.create(any(UserDto.class))).thenThrow(NotAllowedIdSettingException.class);
			assertThrows(NotAllowedIdSettingException.class,()->userController.addUser(userDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToCreateAlreadyRegisterdUserTest() throws DuplicatedUserException, NotAllowedIdSettingException {
			when(userService.create(any(UserDto.class))).thenThrow(DuplicatedUserException.class);
			assertThrows(DuplicatedUserException.class,()->userController.addUser(userDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToGetUnexistingUserTest() throws UserNotFoundException {
			when(userService.read(any(Integer.class))).thenThrow(UserNotFoundException.class);
			assertThrows(UserNotFoundException.class,()->userController.getOneUser(2));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToUpdategUserNotFoundTest() throws UserNotFoundException {
			when(userService.update(any(Integer.class),any(UserDto.class))).thenThrow(UserNotFoundException.class);
			assertThrows(UserNotFoundException.class,()->userController.updateUser(2,userDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToDeleteUserNotFoundTest() throws UserNotFoundException {
			when(userService.delete(any(Integer.class),any(UserDto.class))).thenThrow(UserNotFoundException.class);
			assertThrows(UserNotFoundException.class,()->userController.deleteUser(2,userDto));
		}
		
	}
	
}
