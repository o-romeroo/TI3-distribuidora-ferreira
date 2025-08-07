package com.distribuidoraferreira.backend.dtos;

import com.distribuidoraferreira.backend.enums.StatusComanda;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ComandaRequest(
        @JsonProperty("status") StatusComanda status,
        @JsonProperty("idCliente") Long idCliente,
        @JsonProperty("venda") VendaRequest vendaRequest) {

}