package com.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poseidon.entity.CurvePoint;

public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
