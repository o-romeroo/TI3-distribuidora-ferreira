package com.distribuidoraferreira.backend.dtos;

import com.distribuidoraferreira.backend.enums.StatusCliente;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ContaClienteRequest(
        @JsonProperty("nome_cliente") String nomeCliente,
        @JsonProperty("telefone") String telefone,
        @JsonProperty("status") StatusCliente statusCliente
) {
        public ContaClienteRequest(String nomeCliente, String telefone) {
                this(nomeCliente, telefone, StatusCliente.ADIMPLENTE);
        }
}