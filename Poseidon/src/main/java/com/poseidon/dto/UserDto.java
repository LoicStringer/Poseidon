package com.poseidon.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

@Component
public class UserDto {

	private Integer userId;
	
	@NotBlank(message = "Username is mandatory")
    private String userName;
    
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
    message = "Password must contains at least 8 characters, an upper-case letter, a digit and a symbol.")
    @NotBlank(message = "Password is mandatory")
    private String userPassword;

    @NotBlank(message = "FullName is mandatory")
    private String userFullname;
    
    @NotBlank(message = "Role is mandatory")
    private String userRole;
    
	public UserDto() {
	}

	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserFullname() {
		return userFullname;
	}

	public void setUserFullname(String userFullname) {
		this.userFullname = userFullname;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole.toUpperCase();
	}


}
