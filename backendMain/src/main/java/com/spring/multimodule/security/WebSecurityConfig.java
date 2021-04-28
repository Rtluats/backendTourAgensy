package com.spring.multimodule.security;

import com.spring.multimodule.security.jwt.AuthEntryPointJwt;
import com.spring.multimodule.security.jwt.AuthTokenFilter;
import com.spring.multimodule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService service;

	@Autowired
	private AuthEntryPointJwt unAuthHandler;

	public WebSecurityConfig() {
	}

	@Bean
	public AuthTokenFilter authJwtTokenFilter(){
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http //.authorizeRequests().antMatchers("/api/**").permitAll().anyRequest();
					.cors()
				.and()
					.csrf()
					.disable()
					.exceptionHandling()
					.authenticationEntryPoint(unAuthHandler)
				.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
					.authorizeRequests()
					.antMatchers("/api/v1/auth/")
					.permitAll()
					.antMatchers("/api/v1/**")
					.permitAll()
					.anyRequest()
					.authenticated();

		http.addFilterBefore(authJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}


}
