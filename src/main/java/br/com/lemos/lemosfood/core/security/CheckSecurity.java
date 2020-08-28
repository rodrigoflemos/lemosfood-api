package br.com.lemos.lemosfood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {
	
	public @interface Cozinhas {
		
		@PreAuthorize("hasAutority('SCOPE_READ') and isAuthenticated()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar { }
		
		@PreAuthorize("hasAutority('SCOPE_WRITE') and hasAutority('EDITAR_COZINHAS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar { }
	}
	
	public @interface Restaurantes {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }

	    @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
}