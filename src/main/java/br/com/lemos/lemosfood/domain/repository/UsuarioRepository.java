package br.com.lemos.lemosfood.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.lemos.lemosfood.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

	Optional <Usuario> findByEmail(String email);
}
