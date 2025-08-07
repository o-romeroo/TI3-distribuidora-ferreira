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
public class ProdutoResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("codBarras")
    private String codBarras;

    @JsonProperty("preco")
    private Double preco;

    @JsonProperty("precoConsumo")
    private Double precoConsumo;

    @JsonProperty("estoque")
    private Integer quantidadeEstoque;

    @JsonProperty("estoqueMinimo")
    private Integer quantidadeMinimaEstoque;

    @JsonProperty("img")
    private String img;

    @JsonProperty("imgID")
    private String imgID;

    @JsonProperty("categoria")
    private CategoriaResponse categoriaResponse;
}
