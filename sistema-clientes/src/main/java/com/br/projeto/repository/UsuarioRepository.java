package com.br.projeto.repository;

import com.br.projeto.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer > {

    Optional<Usuario> findByUserName (String userName);

    boolean existsByUserName(String userName);
}
