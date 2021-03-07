package com.poseidon.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;

@Component
public class ResourceIdChecker<T,U>{
	
	@Autowired
	private GenericRepositoryImpl<T,U> checkerRepo;
	
	public void checkIfResourceExistsBeforeCreate(U id) throws DuplicatedResourceException {
		if (checkerRepo.existsById(id))
			throw new DuplicatedResourceException("Not allowed to set an id to resource.");
	}
	
	public void checkIfResourceExistsBeforeTreatment(Class<T> objectType, U id, U comparedId) throws ResourceNotFoundException {
		if(!checkerRepo.existsById(id) && id.equals(comparedId))
			throw new ResourceNotFoundException(objectType.getSimpleName()+" not found");
	}
	
	public void checkIdEqualityBeforeTreatment(Class<T> objectType, U id, U comparedId) throws ResourceNotFoundException {
		if(checkerRepo.existsById(id) && !id.equals(comparedId))
			throw new ResourceNotFoundException("Id request is different from "+objectType.getSimpleName()+ " resource id to treat.");
	}
	
	
}
