package com.distribuidoraferreira.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProdutoRequest {
    @JsonProperty("codBarras")
    private String codBarras;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("preco")
    private Double preco;

    @JsonProperty("precoConsumo")
    private Double precoConsumo;

    @JsonProperty("estoque")
    private Integer quantidadeEstoque;

    @JsonProperty("img")
    private String img;

    @JsonProperty("imgID")
    private String imgID;

    @JsonProperty("categoria")
    private CategoriaRequest categoriaRequest;
}
