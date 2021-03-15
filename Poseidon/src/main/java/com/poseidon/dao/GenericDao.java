package com.poseidon.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class GenericDao<E,ID> {

	@Autowired(required = false)
	private JpaRepository<E, ID> genericRepository;
	
	public List<E> getAllEntity(){
		return genericRepository.findAll();
	}
	
	public E createEntity (E entityToCreate) {
		return genericRepository.save(entityToCreate);
	}
	
	public E readEntity (ID entityId) {
		return genericRepository.findById(entityId).get();
	}
	
	public E updateEntity (ID entityId, E entityToUpdate) {
		return genericRepository.save(entityToUpdate);
	}
	
	public E deleteEntity (ID entityId, E entityToDelete) {
		genericRepository.delete(entityToDelete);
		return entityToDelete;
	}
}
