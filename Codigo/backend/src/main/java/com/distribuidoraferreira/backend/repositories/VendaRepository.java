package com.distribuidoraferreira.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.distribuidoraferreira.backend.enums.StatusVenda;
import com.distribuidoraferreira.backend.models.Venda;

import jakarta.transaction.Transactional;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findVendasByStatus(StatusVenda status);

    @Query("SELECT v FROM Venda v order by v.id desc")
    List<Venda> findVendasByIdDesc();

    Boolean existsByComandaId(Long idComanda);

    // @Query(value = "UPDATE vendas SET status_venda = :status WHERE
    // conta_cliente_id = :idCliente AND venda_id = :idVenda", nativeQuery = true)
    // Boolean updateStatusVenda(@Param("idCliente") Long idCliente,
    // @Param("idVenda") Long idVenda, @Param("status") String status);

    @Query(value = "SELECT * FROM vendas WHERE comanda_id = :idComanda", nativeQuery = true)
    List<Venda> getVendaByIdComanda(Long idComanda);

    @Query(value = "SELECT * FROM vendas WHERE conta_cliente_id = :idCliente AND venda_id = :idVenda", nativeQuery = true)
    List<Venda> verificaExisteVenda(@Param("idCliente") Long idCliente, @Param("idVenda") Long idVenda);

    @Modifying
    @Transactional
    @Query(value = "UPDATE vendas SET status_venda = :status WHERE conta_cliente_id = :idCliente AND venda_id = :idVenda", nativeQuery = true)
    void updateStatusVenda(@Param("idCliente") Long idCliente, @Param("idVenda") Long idVenda,
            @Param("status") String status);

    default void verificaExistenciaVenda(Long idCliente, Long idVenda, String status) {
        List<Venda> vendas = verificaExisteVenda(idCliente, idVenda);
        if (!vendas.isEmpty()) {
            updateStatusVenda(idCliente, idVenda, status);
        }
    }

    @Query(value = "SELECT * FROM vendas WHERE conta_cliente_id = :idContaCliente ORDER BY conta_cliente_id DESC", nativeQuery = true)
    List<Venda> findVendasPendentesPorCliente(@Param("idContaCliente") Long idContaCliente);

    List<Venda> findAllByOrderByDataHoraDesc();
}