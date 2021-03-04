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
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.BidService;

@RestController
@RequestMapping("/bids")
public class BidController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
   
	@Autowired
	private BidService bidService;
	
	@GetMapping("")
	public ResponseEntity<List<BidDto>> getBidsList(){
		log.info("User has entered \"/bids\" endpoint to get the bids list");
		return ResponseEntity.ok(bidService.getAllBids());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BidDto> getOneBid(@PathVariable Integer id) throws ResourceNotFoundException{
		log.info("User has entered \"/bids\" endpoint to get one bid identified by " + id);
		return ResponseEntity.ok(bidService.read(id));
	}
	
	@PostMapping("")
	public ResponseEntity<BidDto> addBid(@RequestBody @Valid BidDto bidToAdd) {
		log.info("User has entered \"/bids\" endpoint to add a bid");
		return ResponseEntity.ok(bidService.create(bidToAdd));
	}
	
	@PutMapping("")
	public ResponseEntity<BidDto> updateBid(@RequestBody @Valid BidDto bidToUpdate) throws ResourceNotFoundException{
		log.info("User has entered \"/bids\" endpoint to update a bid identified by " + bidToUpdate.getBidId() );
		return ResponseEntity.ok(bidService.update(bidToUpdate));
	}
	
	@DeleteMapping("")
	public ResponseEntity<BidDto> deleteBid(@RequestBody @Valid BidDto bidToDelete) throws ResourceNotFoundException{
		log.info("User has entered \"/bids\" endpoint to delete a bid identified by " + bidToDelete.getBidId());
		return ResponseEntity.ok(bidService.delete(bidToDelete));
	}
	
	
	/*
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // TODO: call service find all bids to show to the view
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        return "redirect:/bidList/list";
    }
    */
    
    
    
}
