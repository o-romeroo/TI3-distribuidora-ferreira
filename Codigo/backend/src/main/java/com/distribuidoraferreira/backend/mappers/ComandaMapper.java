package com.distribuidoraferreira.backend.mappers;

import com.distribuidoraferreira.backend.dtos.ComandaRequest;
import com.distribuidoraferreira.backend.dtos.ComandaResponse;
import com.distribuidoraferreira.backend.models.Comanda;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import org.mapstruct.Named;

import com.distribuidoraferreira.backend.dtos.VendaResponse;
import com.distribuidoraferreira.backend.models.Venda;

@Mapper(componentModel = "spring", uses = { VendaMapper.class })
public interface ComandaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataHora", ignore = true)
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "idCliente", source = "request.idCliente")
    @Mapping(target = "venda", source = "request.vendaRequest", ignore = true)
    @Mapping(target = "idCaixa", ignore = true)
    public Comanda comandaRequestToComanda(ComandaRequest request);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "dataHora", source = "entity.dataHora")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "idCliente", source = "entity.idCliente")
    @Mapping(target = "idCaixa", source = "entity.idCaixa")
    @Mapping(target = "vendaResponse", source = "entity.venda", ignore = true)
    public ComandaResponse comandaToComandaResponse(Comanda entity);

    public List<ComandaResponse> comandasToComandaResponses(List<Comanda> entities);

    @Named("mapVendaToVendaResponse")
    VendaResponse mapVendaToVendaResponse(Venda venda);

    VendaResponse[] map(List<Venda> value);
}
