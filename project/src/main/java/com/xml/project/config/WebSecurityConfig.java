package com.xml.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.xml.project.service.JwtUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	private JwtAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
			.authorizeRequests().antMatchers("/auth/**").permitAll().antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
			.antMatchers(HttpMethod.POST, "/api/user/login", "/api/user/register").permitAll()
			.antMatchers(HttpMethod.GET, "/api/db/init").permitAll()
			.antMatchers(HttpMethod.GET, "/api/response/**").permitAll()
			.antMatchers(HttpMethod.GET, "/api/request/**").permitAll()
			.anyRequest().authenticated().and()
			.cors().and();
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.POST, "/api/user/login");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/user/register");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/response/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/request/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/db/init");
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js","/v2/api-docs");
	}
}
