package com.distribuidoraferreira.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.distribuidoraferreira.backend.models.Categoria;

import jakarta.transaction.Transactional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findCategoriaByNome(String nome);

    @Transactional
    int deleteCategoriaById(Long id);
}
