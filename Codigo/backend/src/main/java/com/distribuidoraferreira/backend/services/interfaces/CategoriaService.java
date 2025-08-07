package com.distribuidoraferreira.backend.services.interfaces;

import java.util.List;

import com.distribuidoraferreira.backend.dtos.CategoriaRequest;
import com.distribuidoraferreira.backend.dtos.CategoriaResponse;
import com.distribuidoraferreira.backend.dtos.GenericResponse;

public interface CategoriaService {
    void addCategoria(CategoriaRequest categoriaRequest);

    void deleteCategoriaById(Long id);

    GenericResponse<CategoriaResponse> getCategoriaById(Long id);

    GenericResponse<List<CategoriaResponse>> getCategorias();

    GenericResponse<CategoriaResponse> getCategoriaByNome(String nome);

}
