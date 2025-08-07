package com.distribuidoraferreira.backend.services.interfaces;

import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.dtos.MovimentacaoEstoqueRequest;
import com.distribuidoraferreira.backend.dtos.MovimentacaoEstoqueResponse;

import java.util.List;

public interface MovimentacaoEstoqueService {
    GenericResponse<String> addMovimentacaoEstoque(MovimentacaoEstoqueRequest movimentacaoEstoqueRequest);

    GenericResponse<List<MovimentacaoEstoqueResponse>> getMovimentacoesEstoque();

    GenericResponse<MovimentacaoEstoqueResponse> getMovimentacaoEstoqueById(Long id);

    GenericResponse<List<MovimentacaoEstoqueResponse>> getMovimentacoesEstoqueByProdutoId(Long produtoId);
}
