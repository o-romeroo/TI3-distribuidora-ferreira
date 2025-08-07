package com.distribuidoraferreira.backend.repositories;

import com.distribuidoraferreira.backend.models.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {
    List<MovimentacaoEstoque> findAllByProdutoId(Long produtoId);
    List<MovimentacaoEstoque> findAllByCompraId(Long compraId);
}
