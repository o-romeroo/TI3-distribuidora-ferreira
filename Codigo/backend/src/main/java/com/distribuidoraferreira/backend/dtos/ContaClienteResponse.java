package com.distribuidoraferreira.backend.dtos;

import java.util.List;

import com.distribuidoraferreira.backend.enums.StatusCliente;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ContaClienteResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("nome_cliente") String nomeCliente,
        @JsonProperty("telefone") String telefone,
        @JsonProperty("status") StatusCliente statusCliente,
        @JsonProperty("saldo_devedor") Double saldoDevedor,
        @JsonProperty("vendas") List<VendaResponse> vendaResponseList,
        @JsonProperty("total_pago") Double totalPago) {
}