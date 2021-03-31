package com.poseidon.dao;

import java.util.List;

public interface IGenericCrudDao <DTO,ID> {

	List<DTO> getAllList();
	DTO create (DTO entityToCreateAsDto) throws Exception;
	DTO read (ID entityId) throws Exception;
	DTO update(ID entityId, DTO entityUpdatedAsDto) throws Exception;
	DTO delete(ID entityId, DTO entityToDeleteAsDto) throws Exception;

}
