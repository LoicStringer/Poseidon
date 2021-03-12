package com.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<E,ID> extends JpaRepository<E, ID>{

	
	
}
