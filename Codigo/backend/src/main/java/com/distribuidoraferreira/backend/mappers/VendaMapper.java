package com.distribuidoraferreira.backend.mappers;

import com.distribuidoraferreira.backend.dtos.VendaPendenteResponse;
import com.distribuidoraferreira.backend.dtos.VendaRequest;
import com.distribuidoraferreira.backend.dtos.VendaResponse;
import com.distribuidoraferreira.backend.models.Venda;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { MovimentacaoEstoqueMapper.class })
public interface VendaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataHora", ignore = true)
    @Mapping(target = "metodoPagamento", source = "request.metodoPagamento")
    @Mapping(target = "totalVenda", ignore = true)
    @Mapping(target = "totalPago", ignore = true)
    @Mapping(target = "caixa", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "movimentacoesEstoque", source = "request.movimentacaoEstoque")
    @Mapping(target = "comanda", ignore = true)
    @Mapping(target = "contaCliente", ignore = true)
    public Venda vendaRequestToVenda(VendaRequest request);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "dataHora", source = "entity.dataHora")
    @Mapping(target = "metodoPagamento", source = "entity.metodoPagamento")
    @Mapping(target = "totalVenda", source = "entity.totalVenda")
    @Mapping(target = "caixa", source = "entity.caixa", ignore = true)
    @Mapping(target = "movimentacaoEstoqueResponses", source = "entity.movimentacoesEstoque")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "contaCliente", source = "entity.contaCliente", ignore = true)
    public VendaResponse vendaToVendaResponse(Venda entity);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "dataHora", source = "entity.dataHora")
    @Mapping(target = "metodoPagamento", source = "entity.metodoPagamento")
    @Mapping(target = "totalVenda", source = "entity.totalVenda")
    @Mapping(target = "caixa", source = "entity.caixa", ignore = true)
    @Mapping(target = "movimentacaoEstoqueResponses", source = "entity.movimentacoesEstoque")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "contaCliente", source = "entity.contaCliente.nomeCliente")
    public VendaPendenteResponse vendaToVendaPendenteResponse(Venda entity);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "dataHora", source = "entity.dataHora")
    @Mapping(target = "metodoPagamento", source = "entity.metodoPagamento")
    @Mapping(target = "totalVenda", source = "entity.totalVenda")
    @Mapping(target = "totalPago", source = "entity.totalPago", ignore = true)
    @Mapping(target = "caixa", source = "entity.caixa", ignore = true)
    @Mapping(target = "movimentacaoEstoqueResponses", source = "entity.movimentacoesEstoque")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "contaCliente", source = "entity.contaCliente.nomeCliente")
    public List<VendaPendenteResponse> vendasToVendaPendenteResponses(List<Venda> entity);

    public List<VendaResponse> vendasToVendaResponses(List<Venda> entities);

}
