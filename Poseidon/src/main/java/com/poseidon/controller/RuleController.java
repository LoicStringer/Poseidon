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

import com.poseidon.dto.RuleDto;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.RuleService;

@RestController
@RequestMapping("/poseidon/api/rules")
public class RuleController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	   
	@Autowired
	private RuleService ruleService;
	
	@GetMapping("")
	public ResponseEntity<List<RuleDto>> getRulesList(){
		log.info("User has entered \"/rules\" endpoint to get the rules list");
		return ResponseEntity.ok(ruleService.getDtoList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RuleDto> getOneRule(@PathVariable Integer id) throws ResourceNotFoundException{
		log.info("User has entered \"/rules\" endpoint to get one rule identified by " + id);
		return ResponseEntity.ok(ruleService.read(id));
	}
	
	@PostMapping("")
	public ResponseEntity<RuleDto> addRule(@RequestBody @Valid RuleDto ruleToAdd) throws NotAllowedIdSettingException{
		log.info("User has entered \"/rules\" endpoint to add a rule");
		return ResponseEntity.ok(ruleService.create(ruleToAdd));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RuleDto> updateRule(@PathVariable Integer id, @RequestBody @Valid RuleDto ruleToUpdate) throws ResourceNotFoundException{
		log.info("User has entered \"/rules\" endpoint to update a rule identified by " + ruleToUpdate.getRuleId());
		return ResponseEntity.ok(ruleService.update(id,ruleToUpdate));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<RuleDto> deleteRule(@PathVariable Integer id,@RequestBody @Valid RuleDto ruleToDelete) throws ResourceNotFoundException{
		log.info("User has entered \"/rules\" endpoint to delete a rule identified by " + ruleToDelete.getRuleId());
		return ResponseEntity.ok(ruleService.delete(id,ruleToDelete));
	}
	
}
