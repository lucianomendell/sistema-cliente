package com.br.projeto.repository;

import com.br.projeto.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
}
