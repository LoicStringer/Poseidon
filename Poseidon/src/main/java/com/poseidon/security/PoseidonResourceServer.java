package com.poseidon.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@SuppressWarnings("deprecation")
@Configuration
@EnableResourceServer
public class PoseidonResourceServer extends ResourceServerConfigurerAdapter {
	
	@Value("${resource.id}")
	private String resourceId;
	
	@Value("${stateless.enabled}")
	private boolean isStateless;
	
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceId).stateless(isStateless);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
       
    	http
        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	.and()
            .authorizeRequests()
            	.antMatchers("/poseidon/users/**","/poseidon/admin/**","poseidon/api/**").authenticated()
                .antMatchers("/poseidon/users/**").hasAuthority("ADMIN")
                .antMatchers("/poseidon/admin/**").hasAuthority("ADMIN")
                .antMatchers("/poseidon/api/**").hasAnyAuthority("USER","ADMIN")
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
        
    }


}
