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
public class EstoqueMinimoRequest {

    @JsonProperty("idProduto")
    private Long idProduto;

    @JsonProperty("estoqueMinimo")
    private int estoqueMinimo;
}
