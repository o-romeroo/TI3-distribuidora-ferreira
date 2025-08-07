
package com.distribuidoraferreira.backend.services.interfaces;

import com.distribuidoraferreira.backend.dtos.ComandaRequest;
import com.distribuidoraferreira.backend.dtos.ComandaResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;
import com.distribuidoraferreira.backend.dtos.ItemComandaRequest;
import com.distribuidoraferreira.backend.dtos.PagamentoComandaRequest;

import java.util.List;

public interface ComandaService {
    GenericResponse<String> addComanda(ComandaRequest comandaRequest);

    GenericResponse<String> fecharComandaById(Long idComanda);

    GenericResponse<String> concluirVendasComanda(PagamentoComandaRequest comanda);

    GenericResponse<String> updateComandaById(Long id, ComandaRequest comandaRequest);

    GenericResponse<List<ComandaResponse>> getComandas();

    GenericResponse<List<ComandaResponse>> getComandasByStatus(String status);

    GenericResponse<ComandaResponse> getComandaById(Long id);

    GenericResponse<List<ComandaResponse>> getComandasByIdCliente(Long idCliente);

    GenericResponse<String> addItemComanda(Long id, List<ItemComandaRequest> itemComandaRequest);
}