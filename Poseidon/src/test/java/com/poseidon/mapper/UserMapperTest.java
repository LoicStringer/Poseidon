package com.poseidon.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.poseidon.dto.UserDto;
import com.poseidon.entity.User;

public class UserMapperTest {

	private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
	
	@Test
	void userDtoToUserTest() {
		UserDto userDto = new UserDto();
		userDto.setUserName("Serpico");
		User user = userMapper.userDtoToUser(userDto);
		assertEquals("Serpico",user.getUserName());
	}
	
	@Test
	void userToUserDtoTest() {
		User user = new User();
		user.setUserName("Serpico");
		UserDto userDto = userMapper.userToUserDto(user);
		assertEquals("Serpico",userDto.getUserName());
	}
	
}
