package com.distribuidoraferreira.backend.dtos;

import java.util.Date;

import com.distribuidoraferreira.backend.enums.StatusCaixa;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CaixaResponse(
                @JsonProperty("caixa_id") Long id,
                @JsonProperty("data_hora") Date dataHora,
                @JsonProperty("status") StatusCaixa status,
                @JsonProperty("valor_inicial") Double valorInicial,
                @JsonProperty("faturamento_dia") Double faturamentoDia,
                @JsonProperty("valor_total") Double valorTotal) {
}
