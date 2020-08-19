package br.com.lemos.lemosfood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		//Configura usuario em memoria
		
		auth.inMemoryAuthentication()
			.withUser("rodrigo")
				.password(passwordEncoder().encode("123456"))
				.roles("ADMIN")
			.and()
			.withUser("joao")
				.password(passwordEncoder().encode("123"))
				.roles("ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.httpBasic()
		
			.and()
			.authorizeRequests()
				.antMatchers("/v1/cozinhas/**").permitAll() //Sempre antes do anyRequest para nao dar erro
				.anyRequest().authenticated() //Autoriza qualquer requisicao autenticada
				
			.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Define que a API é stateless
		
			.and().csrf().disable(); //Desabilita 'csrf': O csrf é uma protecao contra ataque de falsficação de requisicao, quando cookies sao 
									 //utilizados. Como a sessao é stateless, nao ha problema em desabilitar o csrf
	}
	
	//Bean registrado para que a configuracao de usuario em memoria funcione. Caso nao seja registrado ocorre uma exception apontando id null
	@Bean
	public PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}
}