package com.distribuidoraferreira.backend.services.interfaces;

import java.util.List;

import com.distribuidoraferreira.backend.dtos.VendaRequest;
import com.distribuidoraferreira.backend.dtos.VendaResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.dtos.VendaPendenteResponse;
import com.distribuidoraferreira.backend.enums.StatusVenda;

public interface VendaService {
    GenericResponse<String> addVenda(VendaRequest vendaRequest);

    GenericResponse<String> deleteVendaById(Long id);

    GenericResponse<VendaResponse> getVendaById(Long id);

    GenericResponse<List<VendaResponse>> getVendaByIdComanda(Long idComanda);

    GenericResponse<List<VendaResponse>> getVendas();

    GenericResponse<List<VendaResponse>> getVendasByDataHora();

    GenericResponse<List<VendaPendenteResponse>> getVendasPendentes();

    GenericResponse<List<VendaResponse>> getVendasByStatus(StatusVenda status);

    GenericResponse<List<VendaPendenteResponse>> getVendasPendentesPorCliente(Long id);
}