package com.poseidon.service;

import java.util.List;

public interface IGenericService<DTO,ID> {

	List<DTO> getDtoList();
	DTO create (DTO dtoToCreate) throws Exception;
	DTO read (ID dtoId) throws Exception;
	DTO update(ID dtoId, DTO dtoToUpdate) throws Exception;
	DTO delete(ID dtoId, DTO dtoToDelete) throws Exception;
	
}
