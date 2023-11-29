package com.company.books.backend.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfigSecurity {

	@Bean
	public UserDetailsManager userDetailsManager(DataSource datasource) {
		return new JdbcUserDetailsManager(datasource);
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration 
			authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests( configure ->{
			configure
				.requestMatchers(HttpMethod.GET, "/v1/libros").hasRole("Empleado")
				.requestMatchers(HttpMethod.GET, "/v1/libros/**").hasRole("Empleado")
				.requestMatchers(HttpMethod.POST, "/v1/libros").hasRole("Jefe")
				.requestMatchers(HttpMethod.PUT, "/v1/libros/**").hasRole("Jefe")
				.requestMatchers(HttpMethod.DELETE, "/v1/libros/**").hasRole("Jefe")
				//.requestMatchers(HttpMethod.GET, "/v1/categorias").hasRole("Empleado")
				//.requestMatchers(HttpMethod.GET, "/v1/categorias/**").hasRole("Empleado")
				.requestMatchers(HttpMethod.POST, "/v1/categorias").hasRole("Jefe")
				.requestMatchers("/v1/authenticate", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
		});
		
		http.httpBasic(Customizer.withDefaults());
		
		http.csrf(csrf -> csrf.disable());
		
		return http.build();
	}
	
	/*@Bean
	public InMemoryUserDetailsManager userDetailsManager() {
		
		UserDetails jaime = User.builder()
				.username("jaime")
				.password("{noop}jaime123")
				.roles("Empleado")
				.build();
		
		UserDetails federico = User.builder()
				.username("federico")
				.password("{noop}federico123")
				.roles("Empleado", "Jefe")
				.build();
		
		UserDetails tomas = User.builder()
				.username("tomas")
				.password("{noop}tomas123")
				.roles("Empleado", "Jefe")
				.build();
		
		return new InMemoryUserDetailsManager(jaime,federico,tomas);
	}*/
}
