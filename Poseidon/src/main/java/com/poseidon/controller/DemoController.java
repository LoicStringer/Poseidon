package com.poseidon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController{
	
	@RequestMapping("/poseidon")
	public String getHome() {
		return "Welcome to Poseidon Home page !";
	}
	
	@RequestMapping("/poseidon/admin")
	public String greetAdmin() {
		return "Welcome, admin";
	}
	
	@RequestMapping("/poseidon/users")
	public String greetUser() {
		return "Welcome, admin in users management";
	}
	
	@RequestMapping("/poseidon/login")
	public String accessLogin() {
		return "Login page";
	}
	
	@RequestMapping("/poseidon/api")
	public String accessApiResources() {
		return "Welcome to Poseidon resources api.";
	}
	
	
	/*
	@RequestMapping("/")
	public String home(Model model)
	{
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}
	*/
	
}
