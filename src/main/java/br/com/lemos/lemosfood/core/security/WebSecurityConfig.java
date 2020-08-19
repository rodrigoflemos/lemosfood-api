package br.com.lemos.lemosfood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
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
}