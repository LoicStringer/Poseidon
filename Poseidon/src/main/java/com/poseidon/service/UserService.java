package com.poseidon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.poseidon.dto.UserDto;
import com.poseidon.entity.User;
import com.poseidon.mapper.UserMapper;
import com.poseidon.repository.UserRepository;

@Service
public class UserService {
	
	private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public List<UserDto> getAllUsers(){
		List<User> users = userRepository.findAll();
		List<UserDto> usersDto = users.stream()
				.map(u->userMapper.userToUserDto(u))
				.collect(Collectors.toList());
		return usersDto;
	}
	
	public UserDto create(UserDto userToCreate) {
		userToCreate.setUserPassword(bCryptPasswordEncoder.encode(userToCreate.getUserPassword()));
		userRepository.save(userMapper.userDtoToUser(userToCreate));
		return userToCreate ;
	}
	
	public UserDto read(Integer id) {
		return userMapper.userToUserDto(userRepository.findById(id).orElse(null));
	}
	
	public UserDto update(UserDto userToUpdate) {
		userToUpdate.setUserPassword(bCryptPasswordEncoder.encode(userToUpdate.getUserPassword()));
		userRepository.save(userMapper.userDtoToUser(userToUpdate));
		return userToUpdate ;
	}
	
	public UserDto delete(UserDto userToDelete) {
		userRepository.delete(userMapper.userDtoToUser(userToDelete));
		return userToDelete;
	}
	
}
