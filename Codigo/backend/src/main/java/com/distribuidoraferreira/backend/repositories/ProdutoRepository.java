package com.distribuidoraferreira.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.distribuidoraferreira.backend.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    Optional<Produto> findByNome(String nome);
    
    Optional<Produto> findByCodBarras(String codBarras);
}
