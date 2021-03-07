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
import com.poseidon.exception.UserNotFoundException;
import com.poseidon.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private UserService userService;

	@GetMapping("")
	public ResponseEntity<List<UserDto>> getUsersList(){
		log.info("User has entered \"/users\" endpoint to get the users list");
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getOneUser(@PathVariable Integer id) throws UserNotFoundException{
		log.info("User has entered \"/users\" endpoint to get one user identified by " + id);
		return ResponseEntity.ok(userService.read(id));
	}
	
	@PostMapping("")
	public ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userToAdd) throws DuplicatedUserException{
		log.info("User has entered \"/users\" endpoint to add a user");
		return ResponseEntity.ok(userService.create(userToAdd));
	}
	
	@PutMapping("")
	public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userToUpdate) throws UserNotFoundException{
		log.info("User has entered \"/users\" endpoint to update a user identified by " + userToUpdate.getUserId());
		return ResponseEntity.ok(userService.update(userToUpdate));
	}
	
	@DeleteMapping("")
	public ResponseEntity<UserDto> deleteUser(@RequestBody @Valid UserDto userToDelete) throws UserNotFoundException{
		log.info("User has entered \"/users\" endpoint to delete a user identified by " + userToDelete.getUserId());
		return ResponseEntity.ok(userService.delete(userToDelete));
	}
	
	/*
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            model.addAttribute("users", userRepository.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
    */
}
