package com.distribuidoraferreira.backend.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.distribuidoraferreira.backend.dtos.ProdutoRequest;
import com.distribuidoraferreira.backend.dtos.ProdutoResponse;
import com.distribuidoraferreira.backend.models.Produto;

@Mapper(componentModel = "spring", uses = { CategoriaMapper.class})
public interface ProdutoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codBarras", source = "request.codBarras")
    @Mapping(target = "nome", source = "request.nome")
    @Mapping(target = "preco", source = "request.preco")
    @Mapping(target = "precoConsumo", source = "request.precoConsumo")
    @Mapping(target = "img", source = "request.img")
    @Mapping(target = "imgID", source = "request.imgID")
    @Mapping(target = "quantidadeEstoque", source = "request.quantidadeEstoque")
    @Mapping(target = "quantidadeMinimaEstoque", ignore = true)
    @Mapping(target = "categoria", source = "request.categoriaRequest")
    @Mapping(target = "movimentacoesEstoque", ignore = true)
    public Produto produtoRequestToProduto(ProdutoRequest request);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "codBarras", source = "entity.codBarras")
    @Mapping(target = "nome", source = "entity.nome")
    @Mapping(target = "preco", source = "entity.preco")
    @Mapping(target = "precoConsumo", source = "entity.precoConsumo")
    @Mapping(target = "img", source = "entity.img")
    @Mapping(target = "imgID", source = "entity.imgID")
    @Mapping(target = "quantidadeEstoque", source = "entity.quantidadeEstoque")
    @Mapping(target = "quantidadeMinimaEstoque", source = "entity.quantidadeMinimaEstoque")
    @Mapping(target = "categoriaResponse", source = "entity.categoria")
    public ProdutoResponse produtoToProdutoResponse(Produto entity);

    public List<ProdutoResponse> produtosToProdutoResponses(List<Produto> entities);
}