package com.distribuidoraferreira.backend.dtos;

import com.distribuidoraferreira.backend.enums.StatusVenda;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record VendaRequest(
                @JsonProperty("metodo_pagamento") String metodoPagamento,
                @JsonProperty("movimentacoes_estoque") List<MovimentacaoEstoqueRequest> movimentacaoEstoque,
                @JsonInclude(JsonInclude.Include.NON_NULL) @JsonProperty("conta_cliente") ContaClienteVendaRequest contaCliente,
                @JsonProperty("status") StatusVenda statusVenda) {
}