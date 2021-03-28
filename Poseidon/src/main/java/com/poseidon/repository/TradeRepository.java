package com.poseidon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poseidon.entity.Trade;


public interface TradeRepository extends JpaRepository<Trade,Integer>{

}
