package com.distribuidoraferreira.backend.services.interfaces;

import java.util.Date;

import com.distribuidoraferreira.backend.dtos.CaixaDetailsResponse;
import com.distribuidoraferreira.backend.dtos.CaixaRequest;
import com.distribuidoraferreira.backend.dtos.CaixaResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;

public interface CaixaService {
    GenericResponse<String> abrirCaixa(CaixaRequest caixaRequest);

    GenericResponse<String> fecharCaixa(Long id);

    GenericResponse<CaixaResponse> getCaixaById(Long id);

    GenericResponse<java.util.List<CaixaResponse>> getCaixas();

    GenericResponse<CaixaResponse> getCaixa();

    GenericResponse<CaixaDetailsResponse> getDetalhesCaixa(Long id);

    GenericResponse<String> updateCaixa(Long id, CaixaRequest CaixaRequest);

    GenericResponse<CaixaResponse> getCaixaByDataHora(Date dataHora);

    GenericResponse<Boolean> isCaixaAberto();

    GenericResponse<Long> getCaixaByID();
}
