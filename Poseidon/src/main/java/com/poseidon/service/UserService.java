package com.poseidon.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.poseidon.dto.UserDto;
import com.poseidon.entity.User;
import com.poseidon.exception.DuplicatedUserException;
import com.poseidon.exception.UserNotFoundException;
import com.poseidon.mapper.UserMapper;
import com.poseidon.repository.UserRepository;

@Service
@Transactional(rollbackOn = Exception.class)
public class UserService {
	
	@Autowired
	private UserMapper userMapper ;

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
	
	public UserDto create(UserDto userToCreate) throws DuplicatedUserException {
		if(userRepository.findByUserName(userToCreate.getUserName()) != null)
			throw new DuplicatedUserException("A user with user name " + userToCreate.getUserName() + " is alreday registered");
		userToCreate.setUserPassword(bCryptPasswordEncoder.encode(userToCreate.getUserPassword()));
		userRepository.save(userMapper.userDtoToUser(userToCreate));
		return userToCreate ;
	}
	
	public UserDto read(Integer id) throws UserNotFoundException {
		return userMapper.userToUserDto(userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found")));
	}
	
	public UserDto update(UserDto userToUpdate) throws UserNotFoundException {
		if(!userRepository.existsById(userToUpdate.getUserId()))
			throw new UserNotFoundException("User not found");
		userToUpdate.setUserPassword(bCryptPasswordEncoder.encode(userToUpdate.getUserPassword()));
		userRepository.save(userMapper.userDtoToUser(userToUpdate));
		return userToUpdate ;
	}
	
	public UserDto delete(UserDto userToDelete) throws UserNotFoundException {
		if(!userRepository.existsById(userToDelete.getUserId()))
			throw new UserNotFoundException("User not found");
		userRepository.delete(userMapper.userDtoToUser(userToDelete));
		return userToDelete;
	}
	
}
