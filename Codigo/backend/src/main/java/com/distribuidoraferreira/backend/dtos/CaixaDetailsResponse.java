package com.distribuidoraferreira.backend.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CaixaDetailsResponse(
        @JsonProperty("dinheiro") Double dinheiro,
        @JsonProperty("pix") Double pix,
        @JsonProperty("credito") Double credito,
        @JsonProperty("debito") Double debito,
        @JsonProperty("contaCliente") Double contaCliente) {
}
