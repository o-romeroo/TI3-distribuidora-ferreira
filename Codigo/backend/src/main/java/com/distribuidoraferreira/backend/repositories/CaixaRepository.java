package com.distribuidoraferreira.backend.repositories;

import com.distribuidoraferreira.backend.models.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CaixaRepository extends JpaRepository<Caixa, Long> {
    @Query("SELECT c FROM Caixa c WHERE c.status = 'ABERTO'")
    Optional<Caixa> findCaixaByStatusAberto();

    @Query("SELECT c FROM Caixa c WHERE c.status = 'FECHADO'")
    Optional<Caixa> findCaixaByStatusFechado();

    @Query("SELECT c FROM Caixa c order by c.id desc")
    Optional<List<Caixa>> getAllCaixasDesc();
}