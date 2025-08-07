package com.distribuidoraferreira.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemComandaRequest(
        @JsonProperty("id_produto") Long idProduto,
        @JsonProperty("quantidade") Integer quantidade ) {
}
