package com.distribuidoraferreira.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CaixaRequest(
        @JsonProperty("valor_inicial") Double valorInicial
) {
    
}
