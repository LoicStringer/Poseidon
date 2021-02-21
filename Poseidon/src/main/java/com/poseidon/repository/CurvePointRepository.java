package com.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poseidon.entity.CurvePoint;


public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
