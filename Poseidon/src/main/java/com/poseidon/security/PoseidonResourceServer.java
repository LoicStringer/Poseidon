package com.poseidon.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


@Configuration
@EnableResourceServer
public class PoseidonResourceServer extends ResourceServerConfigurerAdapter {

	 @Override
	    public void configure(HttpSecurity http) throws Exception {
	        http
	            .headers()
	                .frameOptions()
	                .disable()
	                .and()
	            .authorizeRequests()
	                .antMatchers("/","/home","/register","/login").permitAll()
	                .antMatchers("/admin","/user").authenticated();
	        
	        //hasRole() instead of authenticated();
	    }
	

}
