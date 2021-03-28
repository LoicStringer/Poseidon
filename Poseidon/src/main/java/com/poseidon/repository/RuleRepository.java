package com.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poseidon.entity.Rule;


public interface RuleRepository extends JpaRepository<Rule,Integer> {

}
