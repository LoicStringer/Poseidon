package com.poseidon.dao;

import java.util.List;

public interface IGenericDao<DTO,ID> {

	List<DTO> getAll();
	DTO create (DTO entityToCreateAsDto) throws Exception;
	DTO read (ID entityId) throws Exception;
	DTO update (ID entityID, DTO entityToUpdateAsDto) throws Exception;
	DTO delete (ID entityId, DTO entityToDeleteAsDto) throws Exception;
}
