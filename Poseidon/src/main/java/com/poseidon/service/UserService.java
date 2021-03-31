package com.poseidon.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.dao.UserDao;
import com.poseidon.dto.UserDto;
import com.poseidon.exception.DuplicatedUserException;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.UserNotFoundException;

@Service
@Transactional(rollbackOn = Exception.class)
public class UserService implements IGenericService<UserDto,Integer>{
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<UserDto> getDtoList() {
		return userDao.getAllList();
	}

	@Override
	public UserDto create(UserDto dtoToCreate) throws DuplicatedUserException, NotAllowedIdSettingException {
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
	
}
