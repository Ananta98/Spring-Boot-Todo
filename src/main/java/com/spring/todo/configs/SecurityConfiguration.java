package com.spring.todo.configs;

import java.util.List;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.spring.todo.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final CustomUserDetailsService userDetailService;
	
	public SecurityConfiguration(
			JwtAuthenticationFilter jwtAuthFilter, 
			CustomUserDetailsService userDetailService) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailService = userDetailService;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll()
					.anyRequest().authenticated())
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowedOrigins(List.of("Authorization", "Content-Type"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}
	
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
