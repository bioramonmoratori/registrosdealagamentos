package com.thmestatistica.alagamentos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.thmestatistica.alagamentos.repository.UsuarioRepository;
import com.thmestatistica.alagamentos.service.AutenticacaoService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	final AutenticacaoService autenticacaoService;
	
	public SecurityConfigurations(AutenticacaoService autenticacaoService) {
		this.autenticacaoService = autenticacaoService;
	}
	
	//Configuracoes de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	//Configuracoes de autenticacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			
		
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/css/**", "/images/**").permitAll()
		.antMatchers(HttpMethod.GET, "/registro/**").permitAll()
		.antMatchers(HttpMethod.POST, "/novo/**").permitAll()
		.antMatchers(HttpMethod.GET, "/**").hasRole("USUARIO")
		.anyRequest().authenticated()
		.and().formLogin(form -> form.loginPage("/login")
		.defaultSuccessUrl("/", true).permitAll())
		.logout(logout -> logout.logoutUrl("/logout"));
		
	}
	
	//Configuracoes de recursos estaticos (js, css, imagens etc)
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
	
}
