package com.poseidon.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.repository.GenericRepository;

@Component
public class ResourceIdChecker<E,ID> implements IGenericIdChecker<E, ID>{
	
	@Autowired(required=false)
	private GenericRepository<E,ID> resourceRepository;
	
	public void checkIfResourceExistsBeforeCreate(ID id) throws DuplicatedResourceException {
		if (resourceRepository.existsById(id))
			throw new DuplicatedResourceException("Not allowed to set an id to resource.");
	}
	
	public void checkIfResourceExistsBeforeRead(String objectType, ID id) throws ResourceNotFoundException {
		if (!resourceRepository.existsById(id))
			throw new ResourceNotFoundException(objectType+" not found");
	}
	
	public void checkIfResourceExistsBeforeUpdateOrDelete(String objectType, ID id, ID comparedId) throws ResourceNotFoundException {
		if(!resourceRepository.existsById(id) && id.equals(comparedId))
			throw new ResourceNotFoundException(objectType+" not found");
	}
	
	public void checkIdCoherenceBeforeUpdateOrDelete(String objectType, ID id, ID comparedId) throws ResourceNotFoundException {
		if(resourceRepository.existsById(id) && !id.equals(comparedId))
			throw new ResourceNotFoundException("Id request is different from "+objectType+ " resource id to treat.");
	}
	
	
}
