package com.poseidon.mapper;

import org.mapstruct.Mapper;

import com.poseidon.entity.User;
import com.poseidon.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto userToUserDto (User user);
	User userDtoToUser (UserDto userDto);
}
