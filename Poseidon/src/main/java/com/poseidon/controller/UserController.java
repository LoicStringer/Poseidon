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

import com.poseidon.dto.UserDto;
import com.poseidon.exception.DuplicatedUserException;
import com.poseidon.exception.NotAllowedIdSettingException;
import com.poseidon.exception.UserNotFoundException;
import com.poseidon.service.UserService;

@RestController
@RequestMapping("/poseidon/users")
public class UserController {
    
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private UserService userService;

	@GetMapping("")
	public ResponseEntity<List<UserDto>> getUsersList(){
		log.info("User has entered \"/users\" endpoint to get the users list");
		return ResponseEntity.ok(userService.getDtoList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getOneUser(@PathVariable Integer id) throws UserNotFoundException{
		log.info("User has entered \"/users\" endpoint to get one user identified by " + id);
		return ResponseEntity.ok(userService.read(id));
	}
	
	@PostMapping("")
	public ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userToAdd) throws DuplicatedUserException, NotAllowedIdSettingException{
		log.info("User has entered \"/users\" endpoint to add a user");
		return ResponseEntity.ok(userService.create(userToAdd));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody @Valid UserDto userToUpdate) throws UserNotFoundException{
		log.info("User has entered \"/users\" endpoint to update a user identified by " + userToUpdate.getUserId());
		return ResponseEntity.ok(userService.update(id,userToUpdate));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UserDto> deleteUser(@PathVariable Integer id, @RequestBody @Valid UserDto userToDelete) throws UserNotFoundException{
		log.info("User has entered \"/users\" endpoint to delete a user identified by " + userToDelete.getUserId());
		return ResponseEntity.ok(userService.delete(id,userToDelete));
	}
	
}
