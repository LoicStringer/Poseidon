package com.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poseidon.entity.Bid;


public interface BidRepository extends JpaRepository<Bid,Integer> {

}
