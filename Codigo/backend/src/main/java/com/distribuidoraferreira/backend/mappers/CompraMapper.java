package com.distribuidoraferreira.backend.mappers;

import com.distribuidoraferreira.backend.dtos.CompraRequest;
import com.distribuidoraferreira.backend.dtos.CompraResponse;
import com.distribuidoraferreira.backend.models.Compra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MovimentacaoEstoqueMapper.class})
public interface CompraMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataHora", ignore = true)
    @Mapping(target = "totalCompra", ignore = true)
    @Mapping(target = "metodoPagamento", source = "request.metodoPagamento")
    @Mapping(target = "movimentacoesEstoque", source = "request.movimentacaoEstoqueRequests")
    Compra compraRequestToCompra(CompraRequest request);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "dataHora", source = "entity.dataHora")
    @Mapping(target = "totalCompra", source = "entity.totalCompra")
    @Mapping(target = "metodoPagamento", source = "entity.metodoPagamento")
    @Mapping(target = "quantidadeItens", expression = "java(entity.getMovimentacoesEstoque().size())")
    @Mapping(target = "movimentacaoEstoqueResponses", source = "entity.movimentacoesEstoque")
    CompraResponse compraToCompraResponse(Compra entity);

    List<CompraResponse> comprasToCompraResponses(List<Compra> compras);
}
