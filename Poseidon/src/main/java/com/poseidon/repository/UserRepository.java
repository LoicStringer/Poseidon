package com.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.poseidon.entity.User;


public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	User findByUserName(String userName);
	
}
