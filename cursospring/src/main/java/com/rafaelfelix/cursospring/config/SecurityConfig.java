package com.rafaelfelix.cursospring.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rafaelfelix.cursospring.security.JWTAuthenticationFilter;
import com.rafaelfelix.cursospring.security.JWTAuthorizationFilter;
import com.rafaelfelix.cursospring.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	public static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**",
	};
	
	public static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
			"/estados/**",
			"/cidades/**",
			"/endereco/**"
	};
	
	public static final String[] PUBLIC_MATCHERS_POST = {
			"/clientes",
			"/auth/forgot/**"
	};
	
	@Override
	protected void configure (HttpSecurity httpSec) throws Exception {
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			httpSec.headers().frameOptions().disable();
		}
		
		httpSec.csrf().disable();
		httpSec.authorizeRequests()
			   .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			   .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
			   .antMatchers(PUBLIC_MATCHERS).permitAll()
			   .anyRequest().authenticated();
		httpSec.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		httpSec.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		httpSec.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}
