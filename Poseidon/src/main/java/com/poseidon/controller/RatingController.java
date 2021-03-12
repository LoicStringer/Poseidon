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
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.RatingService;

@RestController
@RequestMapping("/ratings")
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
	public ResponseEntity<RatingDto> addRating(@RequestBody @Valid RatingDto ratingToAdd) throws DuplicatedResourceException{
		log.info("User has entered \"/ratings\" endpoint to add a rating");
		return ResponseEntity.ok(ratingService.create(ratingToAdd));
	}
	
	@PutMapping("")
	public ResponseEntity<RatingDto> updateRating(@PathVariable Integer id, @RequestBody @Valid RatingDto ratingToUpdate) throws ResourceNotFoundException{
		log.info("User has entered \"/ratings\" endpoint to update a rating identified by " + ratingToUpdate.getRatingId());
		return ResponseEntity.ok(ratingService.update(id,ratingToUpdate));
	}
	
	@DeleteMapping("")
	public ResponseEntity<RatingDto> deleteRating(@PathVariable Integer id, @RequestBody @Valid RatingDto ratingToDelete) throws ResourceNotFoundException{
		log.info("User has entered \"/ratings\" endpoint to delete a Rating identified by " + ratingToDelete.getRatingId());
		return ResponseEntity.ok(ratingService.delete(id,ratingToDelete));
	}
	
	/*
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        // TODO: find all Rating, add to model
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        return "redirect:/rating/list";
    }
    */
}
