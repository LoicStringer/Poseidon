package com.poseidon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@SequenceGenerator(name="seq")
@Entity
@Table(name = "user")
public class User {
	
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "seq")
    @Column(name="user_id",length = 4)
    private Integer userId;
    
    @NotBlank(message = "Username is mandatory")
    @Column(name="user_name",length = 125)
    private String userName;
    
  //@Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|\\]).{8,32}$",
  //message = "Password must contains at least 8 characters, an upper-case letter, a digit and a symbol.")
    @NotBlank(message = "Password is mandatory")
    @Column(name="user_password",length = 125)
    private String userPassword;
    
    @NotBlank(message = "FullName is mandatory")
    @Column(name="user_fullname",length = 125)
    private String userFullname;
    
    @NotBlank(message = "Role is mandatory")
    @Column(name="user_role",length = 125)
    private String userRole;
    
    public User() {
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
		this.userRole = userRole;
	}

	

	
}
