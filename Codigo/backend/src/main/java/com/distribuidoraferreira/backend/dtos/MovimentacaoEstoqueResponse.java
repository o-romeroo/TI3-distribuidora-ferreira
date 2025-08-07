package com.distribuidoraferreira.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovimentacaoEstoqueResponse {

    @JsonProperty("id_movimentacao")
    private Long id;

    @JsonProperty("produto_id")
    private Long produtoId;

    @JsonProperty("nome_produto")
    private String nomeProduto;

    @JsonProperty("img")
    private String img;

    @JsonProperty("quantidade")
    private Integer quantidade;

    @JsonProperty("horario_registro")
    private Date horarioRegistro;

    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("valor")
    private Double valor;

    @JsonProperty("preco_unitario")
    private Double precoUnitario;

    @JsonProperty("observacao")
    private String observacao;
}
