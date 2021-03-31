package com.poseidon.controller;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poseidon.dto.BidDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.BidService;

@RestController
@RequestMapping("/poseidon/api/bids")
public class BidController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
   
	@Autowired
	private BidService bidService;
	
	@GetMapping("")
	public ResponseEntity<List<BidDto>> getBidsList(){
		log.info("User has entered \"/bids\" endpoint to get the bids list");
		return ResponseEntity.ok(bidService.getDtoList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BidDto> getOneBid(@PathVariable Integer id) throws ResourceNotFoundException{
		log.info("User has entered \"/bids\" endpoint to get one bid identified by " + id);
		return ResponseEntity.ok(bidService.read(id));
	}
	
	@PostMapping("")
	public ResponseEntity<BidDto> addBid(@RequestBody @Valid BidDto bidToAdd) throws NotAllowedIdSettingException {
		log.info("User has entered \"/bids\" endpoint to add a bid");
		return ResponseEntity.ok(bidService.create(bidToAdd));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<BidDto> updateBid(@PathVariable Integer id , @RequestBody @Valid BidDto bidToUpdate) throws ResourceNotFoundException{
		log.info("User has entered \"/bids\" endpoint to update a bid identified by " + bidToUpdate.getBidId() );
		return ResponseEntity.ok(bidService.update(id,bidToUpdate));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<BidDto> deleteBid(@PathVariable Integer id, @RequestBody @Valid BidDto bidToDelete) throws ResourceNotFoundException{
		log.info("User has entered \"/bids\" endpoint to delete a bid identified by " + bidToDelete.getBidId());
		return ResponseEntity.ok(bidService.delete(id,bidToDelete));
	}
	
	
}
