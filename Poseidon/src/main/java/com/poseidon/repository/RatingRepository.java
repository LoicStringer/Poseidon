package com.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poseidon.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
