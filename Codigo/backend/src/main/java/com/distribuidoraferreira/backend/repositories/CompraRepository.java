package com.distribuidoraferreira.backend.repositories;

import com.distribuidoraferreira.backend.models.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}
