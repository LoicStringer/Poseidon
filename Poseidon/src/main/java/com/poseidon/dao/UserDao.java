package com.poseidon.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.poseidon.dto.UserDto;
import com.poseidon.entity.User;
import com.poseidon.exception.DuplicatedUserException;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.UserNotFoundException;
import com.poseidon.mapper.UserMapper;
import com.poseidon.repository.UserRepository;

@Repository
public class UserDao  implements IGenericCrudDao<UserDto,Integer>{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private UserMapper userMapper;

	@Override
	public List<UserDto> getAllList(){
		List<User> userEntityList = userRepository.findAll();
		List<UserDto> dtoList = userEntityList.stream()
				.map(u-> userMapper.userToUserDto(u))
				.collect(Collectors.toList());
		return dtoList;
	}
	
	@Override
	public UserDto create(UserDto userToCreate) throws DuplicatedUserException, NotAllowedIdSettingException  {
		preventUserIdBreach(userToCreate);
		checkUserDuplication(userToCreate.getUserName());
		userToCreate.setUserPassword(bCryptPasswordEncoder.encode(userToCreate.getUserPassword()));
		userRepository.save(userMapper.userDtoToUser(userToCreate));
		return userToCreate;
	}
	
	@Override
	public UserDto read (Integer userId) throws UserNotFoundException {
		User userToRead = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User with id "+userId+" not found."));
		return userMapper.userToUserDto(userToRead);
	}
	
	@Override
	public UserDto update (Integer userId, UserDto userToUpdate) throws UserNotFoundException  {
		checkUserExistence(userId);
		checkUserIdCoherence(userId, userToUpdate.getUserId());
		userToUpdate.setUserPassword(bCryptPasswordEncoder.encode(userToUpdate.getUserPassword()));
		userRepository.save(userMapper.userDtoToUser(userToUpdate));
		return userToUpdate;
	}
	
	@Override
	public UserDto delete (Integer userId, UserDto userToDelete) throws UserNotFoundException {
		checkUserExistence(userId);
		checkUserIdCoherence(userId, userToDelete.getUserId());
		userRepository.delete(userMapper.userDtoToUser(userToDelete));
		return userToDelete;
	}
	
	private void checkUserDuplication(String userName) throws DuplicatedUserException {
		if(userRepository.findByUserName(userName).isPresent()) {
			log.error("A user with the same user name "+userName+" is present in database.");
			throw new DuplicatedUserException("User with the same user name "+userName+" is already registered.");
		}
	}
	
	private void preventUserIdBreach(UserDto userToCreate) throws NotAllowedIdSettingException {
		if(userToCreate.getUserId()!=null) {
			log.error("Id has been set to this bid before insert.");
			throw new NotAllowedIdSettingException("Not allowed to set an id to users.");
		}
	}

	private void checkUserExistence(Integer userId) throws UserNotFoundException {
		if(!userRepository.existsById(userId)) {
			log.error("The user's id number "+userId+ "doesn't match any registered user's id.");
			throw new UserNotFoundException("The user with "+userId+ " id number is not registered.");
		}
	}
	
	private void checkUserIdCoherence(Integer targetUserId, Integer treatedUserId) throws UserNotFoundException {
		if(!targetUserId.equals(treatedUserId)) {
			log.error("The uri's id is different from the user's id currently handled");
			throw new UserNotFoundException("The requested user's id "+targetUserId+ " is different from the currently handled user's id.");
		}
	}
}
