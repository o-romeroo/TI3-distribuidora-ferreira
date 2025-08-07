package com.distribuidoraferreira.backend.mappers;

import com.distribuidoraferreira.backend.dtos.MovimentacaoEstoqueRequest;
import com.distribuidoraferreira.backend.dtos.MovimentacaoEstoqueResponse;
import com.distribuidoraferreira.backend.models.MovimentacaoEstoque;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ProdutoMapper.class })
public interface MovimentacaoEstoqueMapper {

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "produto.id", source = "request.produtoId")
        @Mapping(target = "quantidade", source = "request.quantidade")
        @Mapping(target = "horarioRegistro", ignore = true)
        @Mapping(target = "tipo", source = "request.tipo")
        @Mapping(target = "precoUnitario", source = "request.precoUnitario")
        @Mapping(target = "observacao", source = "request.observacao")
        @Mapping(target = "compra", ignore = true)
        @Mapping(target = "venda", ignore = true)
        @Mapping(target = "valor", expression = "java(entity.calcularValorTotalMovimentacao())", ignore = true)
        public MovimentacaoEstoque movimentacaoEstoqueRequestToMovimentacaoEstoque(MovimentacaoEstoqueRequest request);

        @Mapping(target = "id", source = "entity.id")
        @Mapping(target = "produtoId", source = "entity.produto.id")
        @Mapping(target = "nomeProduto", source = "entity.produto.nome")
        @Mapping(target = "img", source = "entity.produto.img")
        @Mapping(target = "quantidade", source = "entity.quantidade")
        @Mapping(target = "valor", expression = "java(entity.calcularValorTotalMovimentacao())")
        @Mapping(target = "precoUnitario", source = "entity.precoUnitario")
        @Mapping(target = "horarioRegistro", source = "entity.horarioRegistro")
        @Mapping(target = "tipo", source = "entity.tipo")
        @Mapping(target = "observacao", source = "entity.observacao")
        public MovimentacaoEstoqueResponse movimentacaoEstoqueToMovimentacaoEstoqueResponse(MovimentacaoEstoque entity);

        public List<MovimentacaoEstoqueResponse> movimentacoesEstoqueToMovimentacaoEstoqueResponses(
                        List<MovimentacaoEstoque> entities);

        public List<MovimentacaoEstoque> movimentacoesEstoqueRequestToMovimentacoesEstoque(
                        List<MovimentacaoEstoqueRequest> requests);
}
