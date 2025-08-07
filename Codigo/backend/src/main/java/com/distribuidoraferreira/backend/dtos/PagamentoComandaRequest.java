package com.distribuidoraferreira.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagamentoComandaRequest {
    @JsonProperty("idComanda")
    private Long idComanda;
    @JsonProperty("metodoPagamento")
    private String metodoPagamento;
}
