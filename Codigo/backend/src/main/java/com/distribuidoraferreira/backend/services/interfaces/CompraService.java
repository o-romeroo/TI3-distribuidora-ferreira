package com.distribuidoraferreira.backend.services.interfaces;

import com.distribuidoraferreira.backend.dtos.CompraRequest;
import com.distribuidoraferreira.backend.dtos.CompraResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;

import java.util.List;

public interface CompraService {

    GenericResponse<String> addCompra(CompraRequest compraRequest);

    GenericResponse<List<CompraResponse>> getCompras();

    GenericResponse<CompraResponse> getCompraById(Long id);
}
