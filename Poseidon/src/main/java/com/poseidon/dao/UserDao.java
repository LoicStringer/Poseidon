package com.poseidon.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.poseidon.dto.UserDto;
import com.poseidon.entity.User;
import com.poseidon.exception.DuplicatedUserException;
import com.poseidon.exception.UserNotFoundException;
import com.poseidon.mapper.UserMapper;
import com.poseidon.utilities.UserIdChecker;

@Repository
public class UserDao extends GenericDao<User,Integer> implements IGenericDao<UserDto, Integer>{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserIdChecker userIdChecker;
	
	@Autowired 
	private UserMapper userMapper;
	
	@Value("User")
	private String resourceName;

	
	public List<UserDto> getAll(){
		List<User> userEntityList = getAllEntity();
		List<UserDto> dtoList = userEntityList.stream()
				.map(u-> userMapper.userToUserDto(u))
				.collect(Collectors.toList());
		return dtoList;
	}
	
	public UserDto create(UserDto userToCreate) throws DuplicatedUserException  {
		userIdChecker.checkIfResourceExistsBeforeCreate(userToCreate.getUserId());
		userToCreate.setUserPassword(bCryptPasswordEncoder.encode(userToCreate.getUserPassword()));
		createEntity(userMapper.userDtoToUser(userToCreate));
		return userToCreate;
	}
	
	public UserDto read (Integer userId) throws UserNotFoundException {
		userIdChecker.checkIfResourceExistsBeforeRead(resourceName, userId);
		return userMapper.userToUserDto(readEntity(userId));
	}
	
	public UserDto update (Integer userId, UserDto userToUpdate) throws UserNotFoundException  {
		userIdChecker.checkIdCoherenceBeforeUpdateOrDelete(resourceName, userId, userToUpdate.getUserId());
		userIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(resourceName, userId, userToUpdate.getUserId());
		userToUpdate.setUserPassword(bCryptPasswordEncoder.encode(userToUpdate.getUserPassword()));
		updateEntity(userId, userMapper.userDtoToUser(userToUpdate));
		return userToUpdate;
	}
	
	public UserDto delete (Integer id, UserDto userToDelete) throws UserNotFoundException {
		userIdChecker.checkIdCoherenceBeforeUpdateOrDelete(resourceName, id, userToDelete.getUserId());
		userIdChecker.checkIfResourceExistsBeforeUpdateOrDelete(resourceName, id, userToDelete.getUserId());
		deleteEntity(id,userMapper.userDtoToUser(userToDelete));
		return userToDelete;
	}
}
