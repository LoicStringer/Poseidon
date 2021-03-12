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

import com.poseidon.dto.CurvePointDto;
import com.poseidon.exception.DuplicatedResourceException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.CurvePointService;



@RestController
@RequestMapping("/curvePoints")
public class CurvePointController {
    
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurvePointService curvePointService;
	
	@GetMapping("")
	public ResponseEntity<List<CurvePointDto>> getCurvePointsList(){
		log.info("User has entered \"/curvePoints\" endpoint to get the curve points list");
		return ResponseEntity.ok(curvePointService.getDtoList());
	}
	
	@GetMapping("/id}")
	public ResponseEntity<CurvePointDto> getOneCurvePoint(@PathVariable Integer id) throws ResourceNotFoundException{
		log.info("User has entered \"/curvePoints\" endpoint to get one curve point identified by " + id);
		return ResponseEntity.ok(curvePointService.read(id));
	}
	@PostMapping("")
	public ResponseEntity<CurvePointDto> addCurvePoint(@RequestBody @Valid CurvePointDto curvePointToAdd) throws DuplicatedResourceException{
		log.info("User has entered \"/curvePoints\" endpoint to add a curve point");
		return ResponseEntity.ok(curvePointService.create(curvePointToAdd));
	}
	
	@PutMapping("")
	public ResponseEntity<CurvePointDto> updateCurvePoint(@PathVariable Integer id, @RequestBody @Valid CurvePointDto curvePointToUpdate) throws ResourceNotFoundException{
		log.info("User has entered \"/curvePoints\" endpoint to update a curve point identified by " + curvePointToUpdate.getCurveId());
		return ResponseEntity.ok(curvePointService.update(id,curvePointToUpdate));
	}
	
	@DeleteMapping("")
	public ResponseEntity<CurvePointDto> deleteCurvePoint(@PathVariable Integer id, @RequestBody @Valid CurvePointDto curvePointToDelete) throws ResourceNotFoundException{
		log.info("User has entered \"/curvePoints\" endpoint to delete a curve point identified by " + curvePointToDelete.getCurveId());
		return ResponseEntity.ok(curvePointService.delete(id,curvePointToDelete));
	}
	
	/*
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        // TODO: find all Curve Point, add to model
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        return "redirect:/curvePoint/list";
    }
    
    */
}
