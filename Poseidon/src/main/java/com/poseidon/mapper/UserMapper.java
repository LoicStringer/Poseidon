package com.poseidon.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.poseidon.entity.User;
import com.poseidon.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserMapper userMapper = Mappers.getMapper(UserMapper.class);
	
	UserDto userToUserDto (User user);
	User userDtoToUser (UserDto userDto);
}
