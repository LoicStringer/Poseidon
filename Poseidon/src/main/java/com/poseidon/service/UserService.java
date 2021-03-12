package com.poseidon.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dao.UserDao;
import com.poseidon.dto.UserDto;
import com.poseidon.exception.DuplicatedUserException;
import com.poseidon.exception.UserNotFoundException;

@Service
@Transactional(rollbackOn = Exception.class)
public class UserService implements IGenericService<UserDto,Integer>{
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<UserDto> getDtoList() {
		return userDao.getAll();
	}

	@Override
	public UserDto create(UserDto dtoToCreate) throws DuplicatedUserException {
		return userDao.create(dtoToCreate);
	}

	@Override
	public UserDto read(Integer dtoId) throws UserNotFoundException {
		return userDao.read(dtoId);
	}

	@Override
	public UserDto update(Integer dtoId, UserDto dtoToUpdate) throws UserNotFoundException {
		return userDao.update(dtoId, dtoToUpdate);
	}

	@Override
	public UserDto delete(Integer dtoId, UserDto dtoToDelete) throws UserNotFoundException {
		return userDao.delete(dtoId, dtoToDelete);
	}
	
	/*
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
	
	public UserDto update(Integer resourceId, UserDto userToUpdate) throws UserNotFoundException {
		if(!userRepository.existsById(userToUpdate.getUserId()))
			throw new UserNotFoundException("User not found");
		userToUpdate.setUserPassword(bCryptPasswordEncoder.encode(userToUpdate.getUserPassword()));
		userRepository.save(userMapper.userDtoToUser(userToUpdate));
		return userToUpdate ;
	}
	
	public UserDto delete(Integer resourceId, UserDto userToDelete) throws UserNotFoundException {
		if(!userRepository.existsById(userToDelete.getUserId()))
			throw new UserNotFoundException("User not found");
		userRepository.delete(userMapper.userDtoToUser(userToDelete));
		return userToDelete;
	}
	*/
}
