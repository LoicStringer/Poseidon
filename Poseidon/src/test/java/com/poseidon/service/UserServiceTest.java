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

import com.poseidon.dao.UserDao;
import com.poseidon.dto.UserDto;
import com.poseidon.exception.DuplicatedUserException;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.UserNotFoundException;

@DisplayName("UserService CRUD operations tests")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserDao userDao;
	
	@InjectMocks
	private UserService userService;
	
	private static UserDto testedUserDto;
	
	@BeforeAll
	static void setup() {
		testedUserDto = new UserDto();
		testedUserDto.setUserName("Tony");
		testedUserDto.setUserId(1);
	}
	
	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void createTest() throws DuplicatedUserException, NotAllowedIdSettingException {
			when(userDao.create(any(UserDto.class))).thenReturn(testedUserDto);
			assertEquals(testedUserDto, userService.create(testedUserDto));
		}

		@Test
		void readTest() throws UserNotFoundException {
			when(userDao.read(any(Integer.class))).thenReturn(testedUserDto);
			assertEquals(testedUserDto, userService.read(1));
		}

		@Test
		void updateTest() throws UserNotFoundException {
			when(userDao.update(any(Integer.class),any(UserDto.class))).thenReturn(testedUserDto);
			assertEquals(testedUserDto, userService.update(1,testedUserDto));
		}

		@Test
		void deleteTest() throws UserNotFoundException {
			when(userDao.delete(any(Integer.class),any(UserDto.class))).thenReturn(testedUserDto);
			assertEquals(testedUserDto, userService.delete(1,testedUserDto));
		}

	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {

		@Test
		void isExpectedExceptionThrownWhenCreatingAlreadyExistingUserTest() throws DuplicatedUserException, NotAllowedIdSettingException {
			when(userDao.create(testedUserDto)).thenThrow(DuplicatedUserException.class);
			assertThrows(DuplicatedUserException.class, () -> userService.create(testedUserDto));
		}

		@Test
		void isExpectedExceptionThrownWhenTryingToFindUnexistingUserTest() throws UserNotFoundException {
			when(userDao.read(any(Integer.class))).thenThrow(UserNotFoundException.class);
			assertThrows(UserNotFoundException.class, () -> userService.read(1));
		}

		@Test
		void isExpectedExceptionThrownWhenDeletingUnexistingUserTest() throws UserNotFoundException {
			when(userDao.delete(any(Integer.class), any(UserDto.class))).thenThrow(UserNotFoundException.class);
			assertThrows(UserNotFoundException.class, () -> userService.delete(1, testedUserDto));
		}

		@Test
		void isExpectedExceptionThrownWhenDeletingUserWithIncoherentIdTest() throws UserNotFoundException {
			when(userDao.delete(any(Integer.class), any(UserDto.class))).thenThrow(UserNotFoundException.class);
			assertThrows(UserNotFoundException.class, () -> userService.delete(2, testedUserDto));
		}

		@Test
		void isExpectedExceptionThrownWhenUpdatingUnexistingUserTest() throws UserNotFoundException {
			when(userDao.update(any(Integer.class), any(UserDto.class))).thenThrow(UserNotFoundException.class);
			assertThrows(UserNotFoundException.class, () -> userService.update(1, testedUserDto));
		}

		@Test
		void isExpectedExceptionThrownWhenUpdatingUserWithIncoherentIdTest() throws UserNotFoundException {
			when(userDao.update(any(Integer.class), any(UserDto.class))).thenThrow(UserNotFoundException.class);
			assertThrows(UserNotFoundException.class, () -> userService.update(2, testedUserDto));
		}
	}
}
