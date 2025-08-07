package com.distribuidoraferreira.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompraRequest {

    @JsonProperty("metodo_pagamento")
    private String metodoPagamento;

    @JsonProperty("movimentacoes_estoque")
    private List<MovimentacaoEstoqueRequest> movimentacaoEstoqueRequests;
}
