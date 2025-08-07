package com.distribuidoraferreira.backend.dtos;

import java.util.Date;

import com.distribuidoraferreira.backend.enums.StatusComanda;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ComandaResponse(
        Long id,
        @JsonProperty("data_hora") Date dataHora,
        @JsonProperty("status") StatusComanda status,
        @JsonProperty("idCliente") Integer idCliente,
        @JsonProperty("idCaixa") Long idCaixa,
        @JsonProperty("venda") VendaResponse[] vendaResponse) {
}
