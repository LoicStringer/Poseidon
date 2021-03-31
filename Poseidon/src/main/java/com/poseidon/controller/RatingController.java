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

import com.poseidon.dto.RatingDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.RatingService;

@RestController
@RequestMapping("/poseidon/api/ratings")
public class RatingController {
    
	private Logger log = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
	private RatingService ratingService;
	
	@GetMapping("")
	public ResponseEntity<List<RatingDto>> getRatingsList(){
		log.info("User has entered \"/ratings\" endpoint to get the ratings list");
		return ResponseEntity.ok(ratingService.getDtoList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RatingDto> getOneRating (@PathVariable Integer id) throws ResourceNotFoundException{
		log.info("User has entered \"/ratings\" endpoint to get one rating identified by " + id);
		return ResponseEntity.ok(ratingService.read(id));
	}
	
	@PostMapping("")
	public ResponseEntity<RatingDto> addRating(@RequestBody @Valid RatingDto ratingToAdd) throws NotAllowedIdSettingException{
		log.info("User has entered \"/ratings\" endpoint to add a rating");
		return ResponseEntity.ok(ratingService.create(ratingToAdd));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RatingDto> updateRating(@PathVariable Integer id, @RequestBody @Valid RatingDto ratingToUpdate) throws ResourceNotFoundException{
		log.info("User has entered \"/ratings\" endpoint to update a rating identified by " + ratingToUpdate.getRatingId());
		return ResponseEntity.ok(ratingService.update(id,ratingToUpdate));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<RatingDto> deleteRating(@PathVariable Integer id, @RequestBody @Valid RatingDto ratingToDelete) throws ResourceNotFoundException{
		log.info("User has entered \"/ratings\" endpoint to delete a Rating identified by " + ratingToDelete.getRatingId());
		return ResponseEntity.ok(ratingService.delete(id,ratingToDelete));
	}
	
}
