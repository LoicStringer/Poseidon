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
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.ResourceNotFoundException;
import com.poseidon.service.CurvePointService;

@RestController
@RequestMapping("/poseidon/api/curvePoints")
public class CurvePointController {
    
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CurvePointService curvePointService;
	
	@GetMapping("")
	public ResponseEntity<List<CurvePointDto>> getCurvePointsList(){
		log.info("User has entered \"/curvePoints\" endpoint to get the curve points list");
		return ResponseEntity.ok(curvePointService.getDtoList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CurvePointDto> getOneCurvePoint(@PathVariable Integer id) throws ResourceNotFoundException{
		log.info("User has entered \"/curvePoints\" endpoint to get one curve point identified by " + id);
		return ResponseEntity.ok(curvePointService.read(id));
	}
	
	@PostMapping("")
	public ResponseEntity<CurvePointDto> addCurvePoint(@RequestBody @Valid CurvePointDto curvePointToAdd) throws NotAllowedIdSettingException{
		log.info("User has entered \"/curvePoints\" endpoint to add a curve point");
		return ResponseEntity.ok(curvePointService.create(curvePointToAdd));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CurvePointDto> updateCurvePoint(@PathVariable Integer id, @RequestBody @Valid CurvePointDto curvePointToUpdate) throws ResourceNotFoundException{
		log.info("User has entered \"/curvePoints\" endpoint to update a curve point identified by " + curvePointToUpdate.getCurveId());
		return ResponseEntity.ok(curvePointService.update(id,curvePointToUpdate));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CurvePointDto> deleteCurvePoint(@PathVariable Integer id, @RequestBody @Valid CurvePointDto curvePointToDelete) throws ResourceNotFoundException{
		log.info("User has entered \"/curvePoints\" endpoint to delete a curve point identified by " + curvePointToDelete.getCurveId());
		return ResponseEntity.ok(curvePointService.delete(id,curvePointToDelete));
	}
	
}
