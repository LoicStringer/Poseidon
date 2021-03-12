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

import com.poseidon.dto.TradeDto;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.TradeService;

@RestController
@RequestMapping("/trades")
public class TradeController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	   
	@Autowired
	private TradeService tradeService;
	
	@GetMapping("")
	public ResponseEntity<List<TradeDto>> getTradesList(){
		log.info("User has entered \"/trades\" endpoint to get the trades list");
		return ResponseEntity.ok(tradeService.getDtoList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TradeDto> getOneTrade(@PathVariable Integer id) throws ResourceNotFoundException{
		log.info("User has entered \"/trades\" endpoint to get one trade identified by " + id);
		return ResponseEntity.ok(tradeService.read(id));
	}
	
	@PostMapping("")
	public ResponseEntity<TradeDto> addBid(@RequestBody @Valid TradeDto tradeToAdd) throws DuplicatedResourceException{
		log.info("User has entered \"/trades\" endpoint to add a trade");
		return ResponseEntity.ok(tradeService.create(tradeToAdd));
	}
	
	@PutMapping("")
	public ResponseEntity<TradeDto> updateTrade(@PathVariable Integer id, @RequestBody @Valid TradeDto tradeToUpdate) throws ResourceNotFoundException{
		log.info("User has entered \"/trades\" endpoint to update a trade identified by " + tradeToUpdate.getTradeId());
		return ResponseEntity.ok(tradeService.update(id,tradeToUpdate));
	}
	
	@DeleteMapping("")
	public ResponseEntity<TradeDto> deleteTrade(@PathVariable Integer id, @RequestBody @Valid TradeDto tradeToDelete) throws ResourceNotFoundException{
		log.info("User has entered \"/trades\" endpoint to delete a trade identified by " + tradeToDelete.getTradeId());
		return ResponseEntity.ok(tradeService.delete(id,tradeToDelete));
	}
	
	/*
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        // TODO: find all Trade, add to model
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Trade list
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        return "redirect:/trade/list";
    }
    
    */
}
