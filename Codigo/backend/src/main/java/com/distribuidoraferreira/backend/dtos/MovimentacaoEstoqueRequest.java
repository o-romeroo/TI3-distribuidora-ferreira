package com.distribuidoraferreira.backend.dtos;

import com.distribuidoraferreira.backend.models.Compra;
import com.distribuidoraferreira.backend.models.Venda;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovimentacaoEstoqueRequest {
    @JsonProperty("produto")
    private Long produtoId;

    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("quantidade")
    private Integer quantidade;

    @JsonProperty("preco_unitario")
    private Double precoUnitario;

    @JsonProperty("observacao")
    private String observacao;

    private Compra compra;

    private Venda venda;
}
