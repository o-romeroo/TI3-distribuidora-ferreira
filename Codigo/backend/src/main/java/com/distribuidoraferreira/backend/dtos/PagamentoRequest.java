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
public class PagamentoRequest {
    @JsonProperty("idCliente")
    private Long idCliente;
    @JsonProperty("idVenda")
    private Long idVenda;
    @JsonProperty("valorPago")
    private Double valorPago;
    @JsonProperty("metodoPagamento")
    private String metodoPagamento;

}
