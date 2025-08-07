package com.distribuidoraferreira.backend.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.distribuidoraferreira.backend.dtos.CaixaResponse;
import com.distribuidoraferreira.backend.dtos.CaixaDetailsResponse;
import com.distribuidoraferreira.backend.dtos.CaixaRequest;
import com.distribuidoraferreira.backend.models.Caixa;
import com.distribuidoraferreira.backend.models.DetalhesCaixa;
import com.fasterxml.jackson.annotation.JsonProperty;

@Mapper(componentModel = "spring")
public interface CaixaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataHora", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "valorInicial", source = "request.valorInicial")
    @Mapping(target = "faturamentoDia", ignore = true)
    @Mapping(target = "valorTotal", ignore = true)
    public Caixa caixaRequestToCaixa(CaixaRequest request);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "dataHora", source = "entity.dataHora")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "valorInicial", source = "entity.valorInicial")
    @Mapping(target = "faturamentoDia", source = "entity.faturamentoDia")
    @Mapping(target = "valorTotal", source = "entity.valorTotal")
    public CaixaResponse caixaToCaixaResponse(Caixa entity);

    @Mapping(target = "dinheiro", source = "entity.dinheiro")
    @Mapping(target = "pix", source = "entity.pix")
    @Mapping(target = "credito", source = "entity.credito")
    @Mapping(target = "debito", source = "entity.debito")
    @Mapping(target = "contaCliente", source = "entity.contaCliente")
    public CaixaDetailsResponse caixaToCaixaDetailsResponse(DetalhesCaixa entity);

    public List<CaixaResponse> caixasToCaixasResponses(List<Caixa> entities);

}
