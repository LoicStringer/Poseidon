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
import com.poseidon.service.RuleService;

@RestController
@RequestMapping("/rules")
public class RuleController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	   
	@Autowired
	private RuleService ruleService;
	
	@GetMapping("")
	public ResponseEntity<List<RuleDto>> getRulesList(){
		log.info("User has entered \"/rules\" endpoint to get the rules list");
		return ResponseEntity.ok(ruleService.getAllRules());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RuleDto> getOneRule(@PathVariable Integer id){
		log.info("User has entered \"/rules\" endpoint to get one rule identified by " + id);
		return ResponseEntity.ok(ruleService.read(id));
	}
	
	@PostMapping("")
	public ResponseEntity<RuleDto> addRule(@RequestBody @Valid RuleDto ruleToAdd){
		log.info("User has entered \"/rules\" endpoint to add a rule");
		return ResponseEntity.ok(ruleService.create(ruleToAdd));
	}
	
	@PutMapping("")
	public ResponseEntity<RuleDto> updateRule(@RequestBody @Valid RuleDto ruleToUpdate){
		log.info("User has entered \"/rules\" endpoint to update a rule identified by " + ruleToUpdate.getRuleId());
		return ResponseEntity.ok(ruleService.update(ruleToUpdate));
	}
	
	@DeleteMapping("")
	public ResponseEntity<RuleDto> deleteRule(@RequestBody @Valid RuleDto ruleToDelete){
		log.info("User has entered \"/rules\" endpoint to delete a rule identified by " + ruleToDelete.getRuleId());
		return ResponseEntity.ok(ruleService.delete(ruleToDelete));
	}
	
	/*
    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        // TODO: find all RuleName, add to model
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        return "redirect:/ruleName/list";
    }
	 */
	
}
