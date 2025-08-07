package com.distribuidoraferreira.backend.repositories;

import com.distribuidoraferreira.backend.models.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {
    List<Comanda> findComandaByStatus(String status);

    List<Comanda> findComandaByIdCaixa(Long idCaixa);

    List<Comanda> findComandaByIdCliente(Long idCliente);
}