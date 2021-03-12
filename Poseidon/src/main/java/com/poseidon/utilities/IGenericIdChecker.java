package com.poseidon.utilities;


public interface IGenericIdChecker<E, ID> {

	void checkIfResourceExistsBeforeCreate(ID id) throws Exception;
	void checkIfResourceExistsBeforeRead(String objectType, ID id) throws Exception;
	void checkIfResourceExistsBeforeUpdateOrDelete(String objectType, ID id, ID comparedId) throws Exception;
	void checkIdCoherenceBeforeUpdateOrDelete(String objectType, ID id, ID comparedId) throws Exception;
}
