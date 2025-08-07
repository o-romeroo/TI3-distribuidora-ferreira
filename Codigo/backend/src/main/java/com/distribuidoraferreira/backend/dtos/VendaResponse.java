package com.distribuidoraferreira.backend.dtos;

import java.util.Date;
import java.util.List;

import com.distribuidoraferreira.backend.enums.StatusVenda;
import com.distribuidoraferreira.backend.models.ContaCliente;
import com.fasterxml.jackson.annotation.JsonProperty;

public record VendaResponse(
                Long id,
                @JsonProperty("data_hora") Date dataHora,
                @JsonProperty("metodo_pagamento") String metodoPagamento,
                @JsonProperty("total_venda") Double totalVenda,
                @JsonProperty("total_pago") Double totalPago,
                @JsonProperty("caixa") CaixaResponse caixa,
                @JsonProperty("movimentacoes_estoque") List<MovimentacaoEstoqueResponse> movimentacaoEstoqueResponses,
                @JsonProperty("status") StatusVenda status,
                @JsonProperty("conta_cliente") ContaCliente contaCliente) {
}
