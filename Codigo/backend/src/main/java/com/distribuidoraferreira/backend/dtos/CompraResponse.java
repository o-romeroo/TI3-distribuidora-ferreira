package com.distribuidoraferreira.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompraResponse {
    @JsonProperty("compra_id")
    private Long id;

    @JsonProperty("data_hora")
    private Date dataHora;

    @JsonProperty("total_compra")
    private double totalCompra;

    @JsonProperty("metodo_pagamento")
    private String metodoPagamento;

    @JsonProperty("quantidade_itens")
    private Integer quantidadeItens;

    @JsonProperty("movimentacoes_estoque")
    private List<MovimentacaoEstoqueResponse> movimentacaoEstoqueResponses;
}
