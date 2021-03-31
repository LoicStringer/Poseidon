package com.poseidon.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.poseidon.dto.UserDto;
import com.poseidon.entity.User;
import com.poseidon.exception.DuplicatedUserException;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.UserNotFoundException;
import com.poseidon.mapper.UserMapper;
import com.poseidon.repository.UserRepository;

@DisplayName("UserDao CRUD operations tests")
@ExtendWith(MockitoExtension.class)
public class UserDaoTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserMapper userMapper;
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@InjectMocks
	private UserDao userDao;

	private static User testedUser;
	private static List<User> usersList;
	private static UserDto testedUserDto;
	private static List<UserDto> userDtosList;

	@BeforeAll
	static void setUp() {
		testedUser = new User();
		testedUser.setUserId(1);
		testedUser.setUserName("Serpico");
		testedUser.setUserPassword("corruption");
		usersList = new ArrayList<User>();
		usersList.add(testedUser);
		testedUserDto = new UserDto();
		testedUserDto.setUserId(1);
		testedUserDto.setUserName("Serpico");
		testedUserDto.setUserPassword("corruption");
		userDtosList = new ArrayList<UserDto>();
		userDtosList.add(testedUserDto);
	}

	@Nested
	@Tag("NominalCasesTests")
	@DisplayName("Nominal cases checking")
	class NominalCasesTests {

		@Test
		void getAllTest() {
			when(userMapper.userToUserDto(testedUser)).thenReturn(testedUserDto);
			when(userRepository.findAll()).thenReturn(usersList);
			assertEquals(userDtosList, userDao.getAllList());
		}

		@Test
		void createTest() throws DuplicatedUserException, NotAllowedIdSettingException {
			User userToCreate = new User();
			userToCreate.setUserName("Tony");
			userToCreate.setUserPassword("narcotics");
			UserDto userDtoToCreate = new UserDto(); 
			userDtoToCreate.setUserName("Tony");
			userDtoToCreate.setUserPassword("narcotics");
			when(userRepository.findByUserName(any(String.class))).thenReturn(Optional.empty());
			when(bCryptPasswordEncoder.encode(any(CharSequence.class))).thenReturn(userToCreate.getUserPassword());
			when(userMapper.userDtoToUser(userDtoToCreate)).thenReturn(userToCreate);
			when(userRepository.save(any(User.class))).thenReturn(userToCreate);
			assertEquals(userDtoToCreate, userDao.create(userDtoToCreate));
		}

		@Test
		void readTest() throws UserNotFoundException {
			when(userMapper.userToUserDto(testedUser)).thenReturn(testedUserDto);
			when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(testedUser));
			assertEquals(testedUserDto, userDao.read(1));
		}

		@Test
		void updateTest() throws UserNotFoundException {
			when(userMapper.userDtoToUser(testedUserDto)).thenReturn(testedUser);
			when(userRepository.existsById(any(Integer.class))).thenReturn(true);
			when(bCryptPasswordEncoder.encode(any(CharSequence.class))).thenReturn(testedUser.getUserPassword());
			when(userRepository.save(any(User.class))).thenReturn(testedUser);
			assertEquals(testedUserDto, userDao.update(1, testedUserDto));
		}

		@Test
		void deleteTest() throws UserNotFoundException {
			when(userRepository.existsById(any(Integer.class))).thenReturn(true);
			assertEquals(testedUserDto, userDao.delete(1, testedUserDto));
		}
	}

	@Nested
	@Tag("ExceptionsTests")
	@DisplayName("Exceptions Checking")
	class ExceptionsTests {
		
		@Test
		void isExpectedExceptionThrownWhenUserIsNotFound() {
			assertThrows(UserNotFoundException.class, ()->userDao.read(2));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToSetAnIdBeforeCreate() {
			assertThrows(NotAllowedIdSettingException.class,()->userDao.create(testedUserDto));
		}
		
		@Test
		void isExpectedExceptionThrownWhenTryingToCreateAlreadyRegisteredUserTest() {
			User userToCreate = new User();
			userToCreate.setUserName("Tony");
			UserDto userDtoToCreate = new UserDto(); 
			userDtoToCreate.setUserName("Tony");
			when(userRepository.findByUserName(any(String.class))).thenReturn(Optional.of(userToCreate));
			assertThrows(DuplicatedUserException.class,()->userDao.create(userDtoToCreate));
		}
		
		@Test
		void isExpectedExceptionThrownWhenRequestIdIsDifferentFromUserId() {
			assertThrows(UserNotFoundException.class,()->userDao.update(2, testedUserDto));
		}
	}
	
}
