package com.poseidon.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController{
	
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
		return "Welcome, user";
	}
	
	@RequestMapping("poseidon/login")
	public String login() {
		return "Login page";
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
