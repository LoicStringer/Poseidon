package com.poseidon.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.poseidon.entity.User;
import com.poseidon.exception.DuplicatedUserException;
import com.poseidon.exception.UserNotFoundException;
import com.poseidon.repository.UserRepository;

@Component
public class UserIdChecker implements IGenericIdChecker<User, Integer>{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void checkIfResourceExistsBeforeCreate(Integer id) throws DuplicatedUserException {
		if (userRepository.existsById(id))
			throw new DuplicatedUserException("Not allowed to set an id to resource.");
	}
	
	@Override
	public void checkIfResourceExistsBeforeRead(String objectType, Integer id) throws UserNotFoundException {
		if (!userRepository.existsById(id))
			throw new UserNotFoundException(objectType+" not found");
	}
	
	@Override
	public void checkIfResourceExistsBeforeUpdateOrDelete(String objectType, Integer id, Integer comparedId) throws UserNotFoundException {
		if(!userRepository.existsById(id) && id.equals(comparedId))
			throw new UserNotFoundException(objectType+" not found");
	}
	
	@Override
	public void checkIdCoherenceBeforeUpdateOrDelete(String objectType, Integer id, Integer comparedId) throws UserNotFoundException {
		if(userRepository.existsById(id) && !id.equals(comparedId))
			throw new UserNotFoundException("Id request is different from "+objectType+ " resource id to treat.");
	}
	

}
