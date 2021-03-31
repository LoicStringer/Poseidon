package com.poseidon.mapper;

import org.mapstruct.Mapper;

import com.poseidon.dto.UserDto;
import com.poseidon.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto userToUserDto (User user);
	User userDtoToUser (UserDto userDto);
}
